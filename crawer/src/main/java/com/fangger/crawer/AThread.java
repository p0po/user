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
import java.util.Random;
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

    static Random random = new Random();

    @Override
    public void run() {
        try {
            Thread.sleep(Main.interval+random.nextInt(9));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //String phone = httpClientBuilder.getBody().get("cellphone");
        String phone = httpClientBuilder.getUrl();
        phone = phone.substring(phone.lastIndexOf("=")+1,phone.length());
        //System.out.println(httpClientBuilder.getUrl().substring());
        try {
           HttpResult httpResult = httpClientBuilder.get();
            if(httpResult.getStatusCode() == 200){
                if(httpResult.getBody().equals("{\"result\":false,\"tips\":\"此手机已是链家网注册账户\"}")){
                   success(phone);
               }
                /*if(httpResult.getBody().length()>20){
                    success(httpClientBuilder.getUrl().substring(73));
                }*/
                //成功处理
            }else {
                System.out.println(httpResult.getStatusCode()+" "+httpResult.getBody());
                failure(phone+"  "+httpResult.getStatusCode());
                //失败处理
            }
        } catch (IOException e) {
            failure(phone+" "+e.getMessage());
            //失败处理
            //e.printStackTrace();
        }
    }

    public void success(String phone){
        successLogger.info("{}",phone);
    }

    public void failure(String phone){
        Main.interval = 0;
        failLogger.info("{}",phone);
    }
}
