package com.github.jitwxs.commons.web.http;

import com.github.jitwxs.commons.core.constant.HttpConstant;
import com.github.jitwxs.commons.core.constant.SymbolConstant;
import com.github.jitwxs.commons.core.util.JacksonUtils;
import com.github.jitwxs.commons.core.util.thread.ThreadUtils;
import com.google.common.net.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.Buffer;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.logging.Logger;

public class HttpService {
    private static final Logger logger = Logger.getLogger(HttpService.class.getName());

    private final OkHttpClient client;

    public HttpService(OkHttpClient client) {
        this.client = client;
    }

    public HttpResult get(HttpParams params) {
        Request request = new Request.Builder().url(buildQueryStringUrl(params)).build();
        params.setRequest(request);
        params.setMethod(HttpMethod.GET.name());
        return send(params);
    }

    public HttpResult post(HttpParams params) {
        Request request = new Request.Builder()
                .url(buildQueryStringUrl(params))
                .post(buildRequestBody(params.getBodyParams(), params.isFormBody()))
                .build();
        params.setRequest(request);
        params.setMethod(HttpMethod.POST.name());
        return send(params);
    }

    public HttpResult delete(HttpParams params) {
        Request request = new Request.Builder()
                .url(buildQueryStringUrl(params))
                .delete(buildRequestBody(params.getBodyParams(), params.isFormBody()))
                .build();
        params.setRequest(request);
        params.setMethod(HttpMethod.DELETE.name());
        return send(params);
    }

    private HttpResult send(HttpParams params) {
        HttpResult result = new HttpResult();
        try {
            Request request = params.getRequest();
            Request.Builder requestBuilder = request.newBuilder();
            boolean setHeader = false;
            if (MapUtils.isNotEmpty(params.getHeaders())) {
                requestBuilder.headers(Headers.of(params.getHeaders()));
                setHeader = true;
            }
            if (MapUtils.isNotEmpty(params.getCookies()) || StringUtils.isNotEmpty(params.getCookiesString())) {
                String cookiesString = buildCookieString(params.getCookies());
                if (StringUtils.isEmpty(cookiesString) || StringUtils.isNotEmpty(params.getCookiesString())) {
                    cookiesString = params.getCookiesString();
                }
                requestBuilder.header(HttpHeaders.COOKIE, cookiesString);
                setHeader = true;
            }
            if (setHeader) {
                request = requestBuilder.build();
                params.setRequest(request);
            }
            boolean print = params.isPrint();
            if (print) {
                printRequestInfo(params);
            }
            Response response = this.client.newCall(request).execute();
            int status = response.code();
            String message = response.message();
            ResponseBody body = response.body();
            String bodyString = body == null ? null : body.string();
            if (print) {
                printResponseInfo(status, message, bodyString);
            }
            result.setStatus(status);
            result.setMessage(message);
            if (response.isSuccessful()) {
                result.setData(bodyString);
            }
        } catch (Exception e) {
            result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            result.setData(e.getMessage());
            result.setMessage(ThreadUtils.getStackTrace());
        }
        return result;
    }

    public String buildUrl(HttpParams params) {
        String endpoint = params.getEndpoint(), uri = params.getUri();

        if (endpoint.endsWith(SymbolConstant.SLASH) && uri.startsWith(SymbolConstant.SLASH)) {
            uri = StringUtils.removeStart(uri, SymbolConstant.SLASH);
        }
        if (!endpoint.endsWith(SymbolConstant.SLASH) && !uri.startsWith(SymbolConstant.SLASH)) {
            uri = SymbolConstant.SLASH + uri;
        }
        String url = endpoint + uri;
        params.setUrl(url);
        return url;
    }

    public String buildQueryStringUrl(HttpParams params) {
        String queryString = buildQueryString(params.getQueryStringParams());
        String url = buildUrl(params);
        params.setQueryString(queryString);
        return StringUtils.isNotEmpty(queryString) ? url + SymbolConstant.QUESTION + queryString : url;
    }

    public String buildQueryString(Map<String, Object> params) {
        String queryString = null;
        if (MapUtils.isNotEmpty(params)) {
            StringBuilder stringBuilder = new StringBuilder(512);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                stringBuilder.append(entry.getKey()).append(SymbolConstant.EQUAL).append(entry.getValue().toString())
                        .append(SymbolConstant.AND);
            }
            queryString = StringUtils.removeEnd(stringBuilder.toString(), SymbolConstant.AND);
        }
        return queryString;
    }

    private String buildCookieString(Map<String, String> cookiesMap) {
        String cookieString = null;
        if (MapUtils.isNotEmpty(cookiesMap)) {
            StringBuilder stringBuilder = new StringBuilder(512);
            cookiesMap.forEach((k, v) -> stringBuilder.append(k).append(SymbolConstant.EQUAL).append(v)
                    .append(SymbolConstant.SEMICOLON));
            cookieString = StringUtils.removeEnd(stringBuilder.toString(), SymbolConstant.SEMICOLON);
        }
        return cookieString;
    }

    private RequestBody buildRequestBody(Map<String, Object> params, boolean formBody) {
        RequestBody body;
        if (formBody) {
            FormBody.Builder builderFormBody = new FormBody.Builder();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                builderFormBody.add(entry.getKey(), entry.getValue().toString());
            }
            body = builderFormBody.build();
        } else {
            body = RequestBody.create(MediaType.parse("application/json;"), JacksonUtils.objectToJson(params));
        }
        return body;
    }

    private String buildBodyString(RequestBody body) throws IOException {
        String bodyString = null;
        if (body != null) {
            Buffer buffer = new Buffer();
            body.writeTo(buffer);
            bodyString = buffer.readString(Charset.forName(HttpConstant.Encoding.UTF8));
        }
        return bodyString;
    }

    private void printRequestInfo(HttpParams params) throws IOException {
        Request request = params.getRequest();
        String method = params.getMethod();
        String queryString = params.getQueryString();
        String body = buildBodyString(request.body());
        StringBuilder requestInfo = new StringBuilder(512);
        requestInfo.append("\n\n\tHttp Request: ");
        requestInfo.append("\n\t\t").append("Url: ").append(params.getUrl());
        requestInfo.append("\n\t\t").append("Method: ").append(method);
        Headers headers = request.headers();
        if (headers.size() > 0) {
            requestInfo.append("\n\t\t").append("Headers: ");
            for (String name : headers.names()) {
                requestInfo.append("\n\t\t\t").append(name).append(": ").append(headers.get(name));
            }
        }
        requestInfo.append("\n\t\t").append("Query-String: ").append(queryString);
        requestInfo.append("\n\t\t").append("Body: ").append(body).append("\n");
        logger.info(requestInfo.toString());
    }

    private void printResponseInfo(int status, String message, String body) {
        String responseInfo = "\n\n\t" + "Http Response: " +
                "\n\t\t" + "Status: " + status +
                "\n\t\t" + "Message: " + message +
                "\n\t\t" + "Body: " + body + "\n";
        logger.info(responseInfo);
    }
}