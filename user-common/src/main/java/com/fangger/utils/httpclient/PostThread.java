package com.fangger.utils.httpclient;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by p0po on 15/1/7.
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
