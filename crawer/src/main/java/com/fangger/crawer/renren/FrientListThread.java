package com.fangger.crawer.renren;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fangger.utils.httpclient.HttpClientBuilder;
import com.fangger.utils.httpclient.HttpResult;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by p0po on 2019/7/5.
 */
public class FrientListThread implements Runnable {
    static Map<String,String> header = new HashMap<>();

    static {
        header.put("Cookie","anonymid=jxo99nno-9urv2t; depovince=GW; _r01_=1; ick_login=b8abbc0b-4524-4094-9486-2c4ccfe67968; jebecookies=bd7de43c-7897-4f9c-a02e-521c9420e1e6|||||; _de=8A1DD4841FA220A4F7F8145B70242E336DEBB8C2103DE356; p=f912e6a75701bcc479dd6213bccf94ab6; first_login_flag=1; ln_uact=w-y-p-x-l@163.com; ln_hurl=http://hdn.xnimg.cn/photos/hdn121/20120831/1320/h_main_TxSR_427400004c2a1376.jpg; t=f007e6498cb3979d5994754ac269ee596; societyguester=f007e6498cb3979d5994754ac269ee596; id=88074396; xnsid=446fa945; ver=7.0; loginfrom=null; jebe_key=ffd6f5b2-dacd-41cd-a412-c6a607584e21%7Cb0d21ff9588dc759f4abf20782b29263%7C1562219928198%7C1%7C1562219928039; wp_fold=0; JSESSIONID=abcm-bV3S6Khc5WJvK7Uw");
        header.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
        header.put("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
    }

    Queue<String> uQueue;
    Set<String> uidSet;
    public FrientListThread(Queue<String> uQueue,
                            Set<String> uidSet){
        this.uQueue = uQueue;
        this.uidSet = uidSet;
    }
    @Override
    public void run() {
        while (true){
            if(uQueue.size()>0){
               String uid = uQueue.poll();
                listFriend(uid);
            }
        }
    }

    private void listFriend(String uid){
        try {
            com.google.common.io.Files.append("------------"+uid+"------------\n", new File("D://renren.txt"), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int page = 1;
        JSONArray jsonArray;

        do {
            jsonArray = listFriend(uid,100,page);
            page++;
        }while (jsonArray!=null&&jsonArray.size()>0);
    }

    private JSONArray listFriend(String uid,int pageSize,int pageNumber){
        try {
            header.put("Accept","application/json, text/javascript, */*; q=0.01");
            header.put("Accept-Encoding","gzip, deflate");
            header.put("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8");

            header.put("Host","friend.renren.com");
            header.put("Origin","http://friend.renren.com");
            header.put("Referer","http://friend.renren.com/otherfriends?id="+uid);

            header.put("Pragma","no-cache");
            header.put("Cache-Control","no-cache");
            header.put("X-Requested-With","XMLHttpRequest");

            Map<String,String> body = new HashMap<>();
            body.put("p","{ \"fid\":\"" +
                    uid +
                    "\",\"pz\":\"" +
                    pageSize +
                    "\",\"type\":\"WEB_FRIEND\",\"pn\":\"" +
                    pageNumber +
                    "\"}");
            body.put("requestToken","1082777162");
            body.put("_rtk","c4adca4");
            HttpResult httpResult = HttpClientBuilder.getClient("http://friend.renren.com/friend/api/getotherfriendsdata")
                    .setHeader(header)
                    .setBody(body)
                    .post();
            JSONObject jsonObject = JSON.parseObject(httpResult.getBody());
            int code =jsonObject.getInteger("code");
            if(code != 0){
                return new JSONArray();
            }
            JSONArray jsonArray =jsonObject.getJSONObject("data").getJSONArray("friends");
            Iterator iterator = jsonArray.iterator();
            while (iterator.hasNext()){
                JSONObject object = (JSONObject)iterator.next();
                System.out.println(object.getString("fid")+" = [" + object + "]");
            }

            writeFile(jsonArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private void writeFile(JSONArray jsonArray){
        Iterator iterator = jsonArray.iterator();
        StringBuilder stringBuilder = new StringBuilder();
        while (iterator.hasNext()){
            JSONObject object = (JSONObject)iterator.next();
            String uid = object.getString("fid");
            if(!uidSet.contains(uid)){
                uQueue.offer(uid);
                uidSet.add(uid);
                stringBuilder.append(object.getString("fid")+" = [" + object + "]");
                stringBuilder.append("\n");
                System.out.println(object.getString("fid") + " = [" + object + "]");
            }
        }
        try {
            com.google.common.io.Files.append(stringBuilder.toString(), new File("D://renren1.txt"), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
