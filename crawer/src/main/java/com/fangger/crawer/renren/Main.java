package com.fangger.crawer.renren;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fangger.utils.httpclient.HttpClientBuilder;
import com.fangger.utils.httpclient.HttpResult;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by p0po on 2019/7/4.
 */
public class Main {
    static Map<String,String> header = new HashMap<>();

    static {
        header.put("Cookie","anonymid=jxo99nno-9urv2t; _r01_=1; ln_uact=w-y-p-x-l@163.com; ln_hurl=http://hdn.xnimg.cn/photos/hdn121/20120831/1320/h_main_TxSR_427400004c2a1376.jpg; jebe_key=ffd6f5b2-dacd-41cd-a412-c6a607584e21%7Cb0d21ff9588dc759f4abf20782b29263%7C1562219928198%7C1%7C1562219928039; __utmz=10481322.1562415543.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); depovince=GW; __utma=10481322.562789003.1562415543.1562415543.1562500043.2; _ga=GA1.2.562789003.1562415543; fenqi_user_city=36; _de=8A1DD4841FA220A4F7F8145B70242E336DEBB8C2103DE356; jebecookies=625a938d-b88b-4675-9479-b7bcc768ca04|||||; ick_login=9397d2bc-1207-4ac5-a5fb-25f8a4182f02; p=9118f8c82c235f5e6990571383f3242c6; first_login_flag=1; t=e07d0b49b2b3a7969bb48ef851ad6d956; societyguester=e07d0b49b2b3a7969bb48ef851ad6d956; id=88074396; xnsid=259b17d6; ver=7.0; loginfrom=null; JSESSIONID=abc6b6DNtKQEV76ZXzAVw; wp_fold=0; jebe_key=ffd6f5b2-dacd-41cd-a412-c6a607584e21%7Cb0d21ff9588dc759f4abf20782b29263%7C1562219928198%7C1%7C1562738357938");
        header.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
        header.put("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
    }

    static ExecutorService executorService = Executors.newFixedThreadPool(1);

    static Set<String> uidSet = new HashSet<>();
    static Set<String> gotUidSet = new HashSet<>();
    static Queue<String> uQueue = new ConcurrentLinkedQueue<>();
    static int index = 0;
    public static void main(String[] args) {



        /*final BloomFilter<String> bloom = BloomFilter.create(new Funnel<String>() {
            @Override
            public void funnel(String from, PrimitiveSink into) {
                // 自定义过滤条件 此处不做任何过滤
                into.putString((CharSequence) from, Charset.forName("UTF-8"));
            }
        }, size);*/



        try {

            Files.asCharSource(new File("d:", "renren-photo.txt"), Charset.defaultCharset())
                    .readLines(new LineProcessor<String>() {
                        public boolean processLine(String line) throws IOException {
                            JSONArray jsonArray = JSON.parseArray(line+"]");
                            if(jsonArray!=null&&jsonArray.size()>0){
                                String uid = ((JSONObject)jsonArray.get(0)).getString("ownerId");
                                uid=uid.trim();
                                gotUidSet.add(uid);
                            }
                            return true;//如果是false，则会中断读取操作
                        }

                        public String getResult() {
                            return null;//返回的结果。可以自定义
                        }
                    });

            System.out.println(gotUidSet.size());

            Files.asCharSource(new File("d:", "renren.txt"), Charset.defaultCharset())
                    .readLines(new LineProcessor<String>() {
                                   public boolean processLine(String line) throws IOException {
                                       String uid = "";
                                       if(line.startsWith("-")){
                                           uid = line.substring(12,line.length()-12);
                                       }else {
                                           uid = line.substring(0,line.indexOf('='));
                                       }

                                       uid=uid.trim();

                                       if(!gotUidSet.contains(uid)&&!uidSet.contains(uid)) {
                                           index++;
                                           uidSet.add(uid);
                                           uQueue.offer(uid);
                                           /*if(index>=1000000){
                                               index = 0;
                                               executorService.execute(new RenrenPicThread(uQueue));
                                               uQueue = new ConcurrentLinkedQueue<>();
                                           }*/
                                       }
                                       //bloom.put(uid);
                                       return true;//如果是false，则会中断读取操作
                                   }

                                   public String getResult() {
                                       return null;//返回的结果。可以自定义
                                   }
                               });
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(uidSet.size());

        executorService.execute(new RenrenPicThread(uQueue));


        //System.out.println(bloom.mightContain("88074396")+"---------"+uidSet.contains("88074396"));

      /*  JSONArray jsonArray = listMyFriend();
        Iterator iterator = jsonArray.iterator();
        while (iterator.hasNext()){
            JSONObject object = (JSONObject)iterator.next();
            System.out.println(object.getString("fid")+" = [" + object + "]");
            String uid = object.getString("fid");
            if(!uidSet.contains(uid)){
                uQueue.offer(uid);
                uidSet.add(uid);
            }
        }*/

        //uQueue.offer("88074396");
        //uidSet.add("88074396");

        //executorService.execute(new FrientListThread(uQueue, uidSet));

    }

    /*public static void main(String[] args) {
        listFriend("86530571");
    }*/


    private static JSONArray listMyFriend(){
        HttpResult httpResult = null;
        try {
            httpResult = HttpClientBuilder.getClient("http://friend.renren.com/groupsdata").setHeader(header).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String data = httpResult.getBody().toString().substring(118, httpResult.getBody().length() - 10);
        JSONObject jsonObject = JSON.parseObject(data);
        JSONArray jsonArray = jsonObject.getJSONArray("friends");

        return jsonArray;
    }


}
