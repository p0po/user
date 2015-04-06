package com.fangger.utils.httpclient;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import static com.fangger.utils.httpclient.DefaultConst.*;

/**
 * Created by p0po on 15-2-24.
 */
public class PostClient {
    /**
     * 并发发起POST请求 默认header
     *
     * @param url
     * @param data
     * @return
     */
    public static String postWithPool(String url, Map<String, String> data,ExecutorService exec) {
        return postWithPool(url, data, null,exec);
    }

    /**
     * 并发发起POST请求
     *
     * @param url
     * @param data
     * @param header
     * @return
     */
    public static String postWithPool(String url, Map<String, String> data, Map<String, String> header,ExecutorService exec) {
        return postWithPoolHttp(url, data, header, false,exec);
    }


    /**
     * 并发发起POST请求 SSL
     *
     * @param url
     * @param data
     * @param header
     * @return
     */
    public static String postSSLWithPool(String url, Map<String, String> data, Map<String, String> header,ExecutorService exec) {
        return postWithPoolHttp(url, data, header, true,exec);
    }

    private static String postWithPoolHttp(String url, Map<String, String> data, Map<String, String> header, boolean ssl,ExecutorService exec) {

        CloseableHttpClient httpClient = HttpClients.createDefault();


        HttpPost httpPost = new HttpPost(url);

        if (header != null) {
            for (String key : header.keySet()) {
                httpPost.setHeader(key, header.get(key));
            }
        }

        if (data != null) {
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            for (String key : data.keySet()) {
                formParams.add(new BasicNameValuePair(key, data.get(key)));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
            httpPost.setEntity(entity);
        }

        String result = "";
        try {
            result = exec.submit(new PostThread(httpClient, httpPost)).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 发起POST请求 默认超时 默认header
     *
     * @param url
     * @param data
     * @return
     */
    public static String post(String url, Map<String, String> data) {
        return post(url, data, null);
    }

    /**
     * 发起POST请求 默认超时
     *
     * @param url
     * @param data
     * @param header
     * @return
     */
    public static String post(String url, Map<String, String> data, Map<String, String> header) {
        return post(url, data, header, DEFAULT_TIME_OUT);
    }

    /**
     * 发起POST请求
     *
     * @param url
     * @param data
     * @param header
     * @param connectionTimeOut
     * @return
     */
    public static String post(String url, Map<String, String> data, Map<String, String> header, int connectionTimeOut) {
        return doPost(url, data, header, connectionTimeOut, false);
    }


    public static String sslPost(String url, Map<String, String> data) {
        return post(url, data, null, DEFAULT_TIME_OUT);
    }

    public static String sslPost(String url, Map<String, String> data, Map<String, String> header) {
        return post(url, data, header, DEFAULT_TIME_OUT);
    }

    public static String sslPost(String url, Map<String, String> data, Map<String, String> header, int connectionTimeOut) {
        return doPost(url, data, header, connectionTimeOut, true);
    }

    private static String doPost(String url, Map<String, String> data, Map<String, String> header, int connectionTimeOut, boolean ssl) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        if (ssl) {
            httpClient = createSSLClientDefault();
        }

        connectionTimeOut = connectionTimeOut < 0 ? DEFAULT_TIME_OUT : connectionTimeOut;

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(connectionTimeOut)
                .setConnectTimeout(connectionTimeOut)
                .build();

        HttpPost httpPost = new HttpPost(url);

        httpPost.setConfig(requestConfig);

        if (header != null) {
            for (String key : header.keySet()) {
                httpPost.setHeader(key, header.get(key));
            }
        }

        if (data != null) {
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            for (String key : data.keySet()) {
                formParams.add(new BasicNameValuePair(key, data.get(key)));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
            httpPost.setEntity(entity);
        }

        String result = "";
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
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
}
