package com.fangger.crawer;

import com.fangger.utils.httpclient.HttpClientBuilder;
import com.fangger.utils.httpclient.HttpResult;
import com.google.common.io.Files;
import org.apache.http.HttpHost;

import java.io.File;
import java.nio.charset.Charset;

/**
 * Created by p0po on 15-6-27.
 */
public class Bthread implements Runnable {
    String line;

    public Bthread(String line){
        this.line = line;
    }
    @Override
    public void run() {
        String msg = line;
        try {
            String[] array = line.split(" ");
            HttpResult httpResult = HttpClientBuilder.getClient("http://www.renren.com")
                    .setProxy(new HttpHost(array[0], Integer.valueOf(array[1]), array[4]))
                    .get();
            if(httpResult.getStatusCode() == 200){
                msg = msg+" 成功";
                Files.append(line, new File("/app/proxy.txt"), Charset.defaultCharset());
                Files.append("\r\n", new File("/app/proxy.txt"), Charset.defaultCharset());
            }else {
                msg = msg+" 失败 "+httpResult.getStatusCode();
            }
        }catch (Exception e){
            msg = msg+" "+e.getMessage();
        }
        System.out.println(msg);
    }
}
