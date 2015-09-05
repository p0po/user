package com.fangger.utils.httpclient;

import java.util.Map;

/**
 * Created by p0po on 15-4-3.
 */
public class HttpResult {
    int statusCode;
    String body;
    Map<String,String> header;
    public static final int OK = 200;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }
}
