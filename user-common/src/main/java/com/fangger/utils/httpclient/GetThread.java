package com.fangger.utils.httpclient;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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
