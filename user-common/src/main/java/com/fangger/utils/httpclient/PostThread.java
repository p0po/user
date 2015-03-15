package com.fangger.utils.httpclient;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * Created by p0po on 15/1/7.
 */
public class PostThread implements Callable<String> {
    private final CloseableHttpClient httpClient;
    private final HttpContext context;
    private final HttpPost httppost;

    public PostThread(CloseableHttpClient httpClient,HttpPost httpPost){
        this.httpClient = httpClient;
        this.context = HttpClientContext.create();
        this.httppost = httpPost;
    }

    @Override
    public String call() throws Exception {
        String result = "";
        try {
            CloseableHttpResponse response = httpClient.execute(
                    httppost, context);
            try {
                if(response != null){
                    result = EntityUtils.toString(response.getEntity());
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}