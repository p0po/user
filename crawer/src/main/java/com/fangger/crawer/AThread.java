package com.fangger.crawer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fangger.utils.httpclient.HttpClientBuilder;
import com.fangger.utils.httpclient.HttpResult;
import com.fangger.utils.httpclient.PostClient;
import org.apache.log4j.spi.LoggerFactory;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by p0po on 15-4-13.
 */
public class AThread implements Runnable {
    private static Logger successLogger = org.slf4j.LoggerFactory.getLogger("success");
    private static Logger failLogger = org.slf4j.LoggerFactory.getLogger("failure");

    HttpClientBuilder httpClientBuilder;

    public AThread(HttpClientBuilder httpClientBuilder){
        this.httpClientBuilder = httpClientBuilder;
    }
    @Override
    public void run() {
        //System.out.println(httpClientBuilder.getUrl());
        try {
           HttpResult httpResult = httpClientBuilder.get();
            if(httpResult.getStatusCode() == 200){
                if(httpResult.getBody().length()>20){
                    success(httpClientBuilder.getUrl().substring(73));
                }
                //成功处理
            }else {
                failure(httpClientBuilder.getUrl().substring(73));
                //失败处理
            }
        } catch (IOException e) {
            failure(httpClientBuilder.getUrl().substring(73));
            //失败处理
            //e.printStackTrace();
        }
    }

    public void success(String phone){
        successLogger.info("{}",phone);
    }

    public void failure(String phone){
        failLogger.info("{}",phone);
    }
}
