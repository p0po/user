package com.fangger.utils.httpclient;

import java.util.concurrent.Callable;

/**
 * Created by p0po on 2015/9/8 0008.
 */
public class PostThread implements Callable<HttpResult> {
    HttpClientBuilder httpClientBuilder;


    public PostThread(HttpClientBuilder httpClientBuilder) {
        this.httpClientBuilder = httpClientBuilder;
    }

    @Override
    public HttpResult call() throws Exception {
        return PostClient.send(httpClientBuilder);
    }
}
