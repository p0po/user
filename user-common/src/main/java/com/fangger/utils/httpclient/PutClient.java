package com.fangger.utils.httpclient;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import org.apache.http.Consts;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.fangger.utils.httpclient.DefaultConst.*;

/**
 * Created by p0po on 15-2-24.
 */
public class PutClient {
    public static String doPut(HttpClientBuilder httpClientBuilder) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        int connectionTimeOut = httpClientBuilder.getConnectionTimeOut() <= 0?DEFAULT_TIME_OUT:httpClientBuilder.getConnectionTimeOut();
        HttpHost proxy = httpClientBuilder.getProxy();
        Map<String,String> header = httpClientBuilder.getHeader();
        Map<String,String> body = httpClientBuilder.getHeader();
        String url = httpClientBuilder.getUrl();
        Charset charset = httpClientBuilder.getCharset()==null? Consts.UTF_8:httpClientBuilder.getCharset();
        Preconditions.checkNotNull(url, "url can not be null");

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(connectionTimeOut)
                .setConnectTimeout(connectionTimeOut)
                .setProxy(proxy)
                .build();

        HttpPut httpPut = new HttpPut(url);

        httpPut.setConfig(requestConfig);

        if (header != null) {
            for (String key : header.keySet()) {
                httpPut.setHeader(key, header.get(key));
            }
        }

        if (body != null) {
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            for (String key : body.keySet()) {
                formParams.add(new BasicNameValuePair(key, body.get(key)));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
            httpPut.setEntity(entity);
        }

        String result = "";
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPut);
            if (response != null) {
                result = EntityUtils.toString(response.getEntity(),charset);
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
