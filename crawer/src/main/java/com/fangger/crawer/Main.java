package com.fangger.crawer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fangger.utils.httpclient.GetClient;
import com.fangger.utils.httpclient.HttpClientBuilder;
import com.fangger.utils.httpclient.HttpResult;
import com.fangger.utils.httpclient.PostClient;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by p0po on 15-2-24.
 */
public class Main {
    //static volatile boolean toContinue =true;
    final static int ThreadNum = 100;
    private static final Logger logger = LoggerFactory.getLogger("success");
    public static final ExecutorService exec = Executors.newFixedThreadPool(ThreadNum);



    private static final String filePath = "/app/Dm_Mobile.txt";

    public static boolean resolveLine(String line){
        String[] lineArray = line.split("\\s{2,}");
        int index = Integer.valueOf(lineArray[0]); //序号
        String prefixNum = lineArray[1]; //号码段
        String area = lineArray[2];//地区
        String corperation = lineArray[3];//运营商
        //String areaCode = lineArray[4];//区号

    if (area.equals("北京") && corperation.contains("联通") && !corperation.contains("虚拟运营商")) {
        System.out.println("prefixNum = [" + prefixNum + "] area=[" + area + "] 运营商=[" + corperation + "]");

        //if(Integer.valueOf(prefixNum) >= 1343678){
            crow(prefixNum);
            //toContinue = false;
        //}
    }
        return true;
    }

    private static String url = "";



    public static void crow(String phonePre){
        List<String> phones = genPhoneNum(phonePre);
        for(String phone:phones){
                HttpClientBuilder httpClientBuilder = HttpClientBuilder.getClient(url+phone);
                AThread aThread = new AThread(httpClientBuilder);
                exec.execute(aThread);
        }
    }

    public static void main(String[] args) {

        //System.out.println("https://user.lufax.com/user/service/user/check-phone-is-used?newPhoneNum=13501011001".substring(73));
        File file = new File(filePath);

        try {
           List<String> dd = Files.readLines(file, Charset.forName("GBK"),
                    new LineProcessor<List<String>>() {
                        @Override
                        public boolean processLine(String line) throws IOException {
                            long start = System.currentTimeMillis();
                            while (((ThreadPoolExecutor)exec).getActiveCount() != 0){
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            System.out.println("耗时:"+(System.currentTimeMillis()-start));
                            return resolveLine(line);
                        }

                        @Override
                        public List<String> getResult() {
                            return null;
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("============================================================================");
            exec.shutdown();
        }
    }




    private static List<String> genPhoneNum(String head) {
        List<String> num = new ArrayList<>(10000);
        for (int i = 9999; i >= 0; i--) {
            if (i > 999) {
                num.add(head + i);
            } else if (i >= 100) {
                num.add(head + "0" + i);
            } else if (i >= 10) {
                num.add(head + "00" + i);
            } else {
                num.add(head + "000" + i);
            }
        }
        return num;
    }

}
