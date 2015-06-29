package com.fangger.utils.httpclient;

import com.google.common.base.Charsets;
import org.apache.http.HttpHost;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by p0po on 15-6-26.
 */
public class Demo {
    public static void main(String[] args) {
        HttpHost httpHost = new HttpHost("111.161.65.83",80);
        try {
            HttpResult httpResult = HttpClientBuilder.getClient("http://1111.ip138.com/ic.asp")
                    .setCharset(Charset.forName("gb2312"))
                    .setProxy(httpHost)
                    .get();
            if(httpResult.getStatusCode() == 200){
                String result = httpResult.getBody();
                System.out.println(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
