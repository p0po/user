package com.fangger.utils.httpclient;

import org.apache.http.HttpHost;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created by p0po on 15-6-25.
 */
public class HttpClientBuilder {
    String url;
    Map<String, String> header;
    Map<String, String> body;
    int connectionTimeOut;
    HttpHost proxy;
    Charset charset;

    public static HttpClientBuilder getClient(String url){
        return new HttpClientBuilder(url);
    }

    public HttpResult get() throws IOException {return GetClient.send(this);}
    public Future<HttpResult> getWithPool(ExecutorService executorService) throws IOException {return GetClient.sendWithPool(this, executorService);}

    public HttpResult post() throws IOException {return PostClient.send(this);}
    public Future<HttpResult> postWithPool(ExecutorService executorService) throws IOException {return PostClient.sendWithPool(this,executorService);}

    private HttpClientBuilder(String url){
        this.url = url;
    }

    public HttpHost getProxy() {
        return proxy;
    }

    public HttpClientBuilder setProxy(HttpHost proxy) {
        this.proxy = proxy;
        return this;
    }

    public int getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public HttpClientBuilder setConnectionTimeOut(int connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
        return this;
    }

    public Map<String, String> getBody() {
        return body;
    }

    public HttpClientBuilder setBody(Map<String, String> body) {
        this.body = body;
        return this;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public HttpClientBuilder setHeader(Map<String, String> header) {
        this.header = header;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Charset getCharset() {
        return charset;
    }

    public HttpClientBuilder setCharset(Charset charset) {
        this.charset = charset;
        return this;
    }
}
