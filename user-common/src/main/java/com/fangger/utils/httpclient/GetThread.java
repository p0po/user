package com.fangger.utils.httpclient;

import java.util.concurrent.Callable;

/**
 * Created by p0po on 2015/9/8 0008.
 */
public class GetThread implements Callable<HttpResult> {
    private HttpClientBuilder httpClientBuilder;

    public GetThread(HttpClientBuilder httpClientBuilder) {
        this.httpClientBuilder = httpClientBuilder;
    }

    @Override
    public HttpResult call() throws Exception {
        return GetClient.send(httpClientBuilder);
    }
}
