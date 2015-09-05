package com.fangger.crawer.proxy;

import com.fangger.crawer.Bthread;
import com.fangger.utils.httpclient.HttpClientBuilder;
import com.fangger.utils.httpclient.HttpResult;
import com.google.common.io.Files;
import org.apache.http.HttpHost;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by p0po on 15-7-1.
 */
public class Main {
    public static void main(String[] args) {
        //saveFile();
        exFile();
    }

    public static void exFile(){
        File dir = new File("D:\\app\\data\\daili");
        if(dir.isDirectory()){
            File[] files = dir.listFiles();
            for(File file:files){
                try {
                    Document document = Jsoup.parse(file, Charset.defaultCharset().name());
                    Elements elements = document.getElementsByTag("tr");
                    int index = 0;
                    for(Element element:elements){
                        index++;
                        if(index == 1)continue;

                        Bthread bthread = new Bthread(element.text());
                        Thread thread = new Thread(bthread);
                        thread.start();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void saveFile(){
        for(int i=1;i<65;i++){
            try {
                HttpResult httpResult = HttpClientBuilder.getClient("http://www.xici.net.co/nt/" + i).get();
                if(httpResult.getStatusCode() == 200){
                    Files.write(httpResult.getBody().getBytes(),new File("/app/data/daili/nt-"+i));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
