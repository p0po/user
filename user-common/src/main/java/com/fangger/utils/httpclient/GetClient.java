package com.fangger.utils.httpclient;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

import static com.fangger.utils.httpclient.DefaultConst.*;

/**
 * Created by p0po on 15-2-24.
 */
public class GetClient {
    /**
     * 发起GET请求，默认header，默认超时
     *
     * @param url
     * @return
     */
    public static String get(String url) {
        return get(url, null, DEFAULT_TIME_OUT);
    }

    /**
     * 发起GET请求，默认超时
     *
     * @param url
     * @param header
     * @return
     */
    public static String get(String url, Map<String, String> header) {
        return get(url, header, DEFAULT_TIME_OUT);
    }

    /**
     * 发起GET请求
     *
     * @param url
     * @param header
     * @param connectionTimeOut
     * @return
     */
    public static String get(String url, Map<String, String> header, int connectionTimeOut) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        connectionTimeOut = connectionTimeOut < 0 ? DEFAULT_TIME_OUT : connectionTimeOut;

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(connectionTimeOut)
                .setConnectTimeout(connectionTimeOut)
                .build();


        HttpGet httpget = new HttpGet(url);

        httpget.setConfig(requestConfig);

        if (header != null) {
            for (String key : header.keySet()) {
                httpget.setHeader(key, header.get(key));
            }
        }

        String result = "";
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpget);

            if (response != null) {
                result = EntityUtils.toString(response.getEntity());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 并发发起GET请求 默认header
     *
     * @param url
     * @return
     */
    public static String getWithPool(String url) {
        return getWithPool(url, null);
    }

    /**
     * 并发发起GET请求
     *
     * @param url
     * @param header
     * @return
     */
    public static String getWithPool(String url, Map<String, String> header) {
        HttpGet httpget = new HttpGet(url);

        if (header != null) {
            for (String key : header.keySet()) {
                httpget.setHeader(key, header.get(key));
            }
        }

        String result = "";
        try {
            result = exec.submit(new GetThread(httpClient, httpget)).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
