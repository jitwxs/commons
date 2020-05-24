package com.github.jitwxs.commons.web.http;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.junit.Test;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class HttpServiceTest {
    OkHttpClient client = new HttpClientConfig().client();
    public static final String ENDPOINT = "https://postman-echo.com";

    @Test
    public void get() {
        HttpParams params = new HttpParams();
        params.setEndpoint(ENDPOINT);
        params.setUri("/get");
        HashMap<String, Object> paramMap = new HashMap<String, Object>() {{
            put("test", 1234);
        }};
        params.setQueryStringParams(paramMap);

        HttpService httpService = new HttpService(client);
        HttpResult httpResult = httpService.get(params);
        System.out.println(httpResult);
    }

    @Test
    public void post() {
        HttpParams params = new HttpParams();
        params.setEndpoint(ENDPOINT);
        params.setUri("/post");
        HashMap<String, Object> bodyMap = new HashMap<String, Object>() {{
            put("strange", "boom");
        }};
        params.setBodyParams(bodyMap);
        params.setFormBody(true);

        HttpService httpService = new HttpService(client);
        HttpResult httpResult = httpService.post(params);
        System.out.println(httpResult);
    }

    class HttpClientConfig {
        private long connectTimeout = 20;
        private long readTimeout = 20;
        private long writeTimeout = 20;
        private int maxIdleConnections = 20;
        private long keepAliveDurationNs = 10;
        private boolean retryOnConnectionFailure = true;

        public OkHttpClient client() {
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            clientBuilder.connectTimeout(connectTimeout, TimeUnit.SECONDS);
            clientBuilder.readTimeout(readTimeout, TimeUnit.SECONDS);
            clientBuilder.writeTimeout(writeTimeout, TimeUnit.SECONDS);
            clientBuilder.retryOnConnectionFailure(retryOnConnectionFailure);
            ConnectionPool pool = new ConnectionPool(maxIdleConnections, keepAliveDurationNs, TimeUnit.SECONDS);
            clientBuilder.connectionPool(pool);
            return clientBuilder.build();
        }
    }
}