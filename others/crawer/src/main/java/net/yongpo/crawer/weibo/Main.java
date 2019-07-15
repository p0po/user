package com.fangger.crawer.weibo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fangger.utils.httpclient.HttpClientBuilder;
import com.fangger.utils.httpclient.HttpResult;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by p0po on 2019/7/12.
 */
public class Main {
    static String LIST_URL = "https://m.weibo.cn/api/container/getIndex?containerid=2304133180086671_-_WEIBO_SECOND_PROFILE_WEIBO&page_type=03&page=";
    //https://m.weibo.cn/comments/hotflow?id=4393162494005524&mid=4393162494005524&max_id=139249048064522&max_id_type=0  //评论
    //https://m.weibo.cn/api/statuses/repostTimeline?id=4393235365271392&page=1  //转发
    //https://m.weibo.cn/api/attitudes/show?id=4393235365271392&page=1  //赞
    public static void main(String[] args) {

        try {
            int page = 1;
           for(;;){
               HttpResult httpResult = HttpClientBuilder.getClient(LIST_URL+page).get();
               if(httpResult.getStatusCode()==200&&httpResult != null&&httpResult.getBody()!=null){
                   String body = httpResult.getBody();
                   JSONObject r = JSON.parseObject(body);
                   JSONObject data = r.getJSONObject("data");
                   JSONArray cards = data.getJSONArray("cards");
                   for(Object o:cards){
                       JSONObject card = (JSONObject)o;
                       int type = card.getInteger("card_type");
                       String detail = card.getString("scheme");
                       JSONObject blob = card.getJSONObject("mblog");
                       long id = blob.getLong("id");
                       if(type == 9){
                           System.out.println(card.toString()+"\n");
                           write("3180086671",card.toJSONString()+"\n");
                       }
                   }
               }else {
                   System.err.println(httpResult.getStatusCode() + "\n" + httpResult.getBody());
               }
               page++;
               try {
                   Thread.sleep(3000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void write(String dir,String content){
        try {
            File pathDir = new File("D://weibo/"+dir);
            if(!pathDir.exists()){
                pathDir.mkdirs();
            }
            Files.append(content, new File(pathDir,"list"), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
