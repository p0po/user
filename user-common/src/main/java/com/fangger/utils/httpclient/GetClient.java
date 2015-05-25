package com.fangger.utils.httpclient;

import com.google.common.base.Preconditions;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
    public static HttpResult get(String url) throws IOException {
        return get(url, null, DEFAULT_TIME_OUT);
    }

    /**
     * 发起GET请求，默认超时
     *
     * @param url
     * @param header
     * @return
     */
    public static HttpResult get(String url, Map<String, String> header) throws IOException {
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
    public static HttpResult get(String url, Map<String, String> header, int connectionTimeOut) throws IOException {
        //CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpResult httpResult = new HttpResult();
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

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpget, HttpClientContext.create());

            Preconditions.checkNotNull(response, "Get % result null", httpget.getURI().toString());

            //Preconditions.checkState(response.getStatusLine().getStatusCode() == 200,response.getStatusLine().getStatusCode());
            httpResult.setStatusCode(response.getStatusLine().getStatusCode());

            Header[] headers = response.getAllHeaders();
            int size = headers == null?1:(headers.length+1);
            Map<String,String> map = new HashMap<>(size);
            for (Header resHeader:headers){
                map.put(resHeader.getName(),resHeader.getValue());
            }
            httpResult.setHeader(map);

            String result = EntityUtils.toString(response.getEntity());
            httpResult.setBody(result);
        } finally {
            if (response != null) {
                try {
                    response.close();
                    httpget.releaseConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpResult;
    }

    /**
     * 并发发起GET请求 默认header
     *
     * @param url
     * @return
     */
    public static Future<HttpResult> getWithPool(String url,ExecutorService exec) {
        return getWithPool(url, null,DEFAULT_TIME_OUT,exec);
    }

    /**
     * 并发发起GET请求
     *
     * @param url
     * @param header
     * @return
     */
    public static Future<HttpResult> getWithPool(String url, Map<String, String> header,int connectionTimeOut,ExecutorService exec) {
            return exec.submit(new GetThread(url,header,connectionTimeOut));
    }

    public static void closeExecuter(){

    }
}
