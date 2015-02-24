package com.fangger.utils.httpclient;

import org.apache.http.Consts;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.fangger.utils.httpclient.DefaultConst.*;

/**
 * Created by p0po on 15-2-24.
 */
public class PutClient {
    public String put(String url,Map<String,String> data){
        return doPut(url,data,null,DEFAULT_TIME_OUT,false);
    }


    private static String doPut(String url,Map<String,String> data,Map<String,String> header,int connectionTimeOut,boolean ssl){
        CloseableHttpClient httpClient = HttpClients.createDefault();

        if(ssl){
            httpClient = createSSLClientDefault();
        }

        connectionTimeOut = connectionTimeOut<0?DEFAULT_TIME_OUT:connectionTimeOut;

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(connectionTimeOut)
                .setConnectTimeout(connectionTimeOut)
                .build();

        HttpPut httpPut = new HttpPut(url);

        httpPut.setConfig(requestConfig);

        if(header != null){
            for(String key:header.keySet()){
                httpPut.setHeader(key,header.get(key));
            }
        }

        if(data != null){
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            for(String key:data.keySet()){
                formParams.add(new BasicNameValuePair(key, data.get(key)));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
            httpPut.setEntity(entity);
        }

        String result = "";
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPut);
            if(response != null){
                result = EntityUtils.toString(response.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(response != null){
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
