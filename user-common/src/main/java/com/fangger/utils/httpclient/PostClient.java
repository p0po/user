package com.fangger.utils.httpclient;

import com.google.common.base.Preconditions;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.security.auth.callback.Callback;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

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
    public static Future<HttpResult> postWithPool(String url, Map<String, String> data,ExecutorService exec) {
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
    public static Future<HttpResult> postWithPool(String url, Map<String, String> data, Map<String, String> header, ExecutorService exec) {
        return postWithPoolHttp(url, data, header,exec);
    }


    /**
     * 并发发起POST请求 SSL
     *
     * @param url
     * @param data
     * @param header
     * @return
     */
    public static Future<HttpResult> postSSLWithPool(String url, Map<String, String> data, Map<String, String> header,ExecutorService exec) {
        return postWithPoolHttp(url, data, header,exec);
    }

    private static Future<HttpResult> postWithPoolHttp(String url, Map<String, String> data, Map<String, String> header, ExecutorService exec) {
        return exec.submit(new PostThread(url,data,header,DEFAULT_TIME_OUT));
    }


    /**
     * 发起POST请求 默认超时 默认header
     *
     * @param url
     * @param data
     * @return
     */
    public static HttpResult post(String url, Map<String, String> data) throws IOException {
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
    public static HttpResult post(String url, Map<String, String> data, Map<String, String> header) throws IOException {
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
    public static HttpResult post(String url, Map<String, String> data, Map<String, String> header, int connectionTimeOut) throws IOException {
            return doPost(url, data, header, connectionTimeOut);
    }

    public static HttpResult sslPost(String url, Map<String, String> data) throws IOException {
        return post(url, data, null, DEFAULT_TIME_OUT);
    }

    public static HttpResult sslPost(String url, Map<String, String> data, Map<String, String> header) throws IOException {
        return post(url, data, header, DEFAULT_TIME_OUT);
    }

    public static HttpResult sslPost(String url, Map<String, String> data, Map<String, String> header, int connectionTimeOut) throws IOException {
        return doPost(url, data, header, connectionTimeOut);
    }

    private static HttpResult doPost(String url, Map<String, String> data, Map<String, String> header, int connectionTimeOut) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

      /*  if (ssl) {
            httpClient = createSSLClientDefault();
        }*/

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

        HttpResult httpResult = new HttpResult();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            Preconditions.checkNotNull(response, "Get % result null", httpPost.getURI().toString());

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
                    httpPost.releaseConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpResult;
    }
}
