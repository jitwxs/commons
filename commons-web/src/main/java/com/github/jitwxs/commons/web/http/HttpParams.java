package com.github.jitwxs.commons.web.http;

import lombok.Getter;
import lombok.Setter;
import okhttp3.Request;

import java.util.Map;

@Setter
@Getter
public class HttpParams {

    private String endpoint;
    private String uri;
    private Map<String, String> headers;
    private Map<String, String> cookies;
    private String cookiesString;
    private Map<String, Object> queryStringParams;
    private Map<String, Object> bodyParams;
    private boolean formBody;

    /**
     * under usage: print log
     */
    private boolean print = false;
    private Request request;
    private String url;
    private String method;
    private String queryString;
}