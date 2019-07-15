package com.fangger.crawer.renren;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fangger.utils.httpclient.HttpClientBuilder;
import com.fangger.utils.httpclient.HttpResult;
import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by p0po on 2019/7/7.
 */
public class RenrenPicThread implements Runnable {
    static Map<String,String> header = new HashMap<>();

    static {
        header.put("Cookie","anonymid=jxo99nno-9urv2t; _r01_=1; ln_uact=w-y-p-x-l@163.com; ln_hurl=http://hdn.xnimg.cn/photos/hdn121/20120831/1320/h_main_TxSR_427400004c2a1376.jpg; jebe_key=ffd6f5b2-dacd-41cd-a412-c6a607584e21%7Cb0d21ff9588dc759f4abf20782b29263%7C1562219928198%7C1%7C1562219928039; __utmz=10481322.1562415543.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); depovince=GW; __utma=10481322.562789003.1562415543.1562415543.1562500043.2; _ga=GA1.2.562789003.1562415543; fenqi_user_city=36; _de=8A1DD4841FA220A4F7F8145B70242E336DEBB8C2103DE356; jebecookies=625a938d-b88b-4675-9479-b7bcc768ca04|||||; ick_login=9397d2bc-1207-4ac5-a5fb-25f8a4182f02; p=9118f8c82c235f5e6990571383f3242c6; first_login_flag=1; t=e07d0b49b2b3a7969bb48ef851ad6d956; societyguester=e07d0b49b2b3a7969bb48ef851ad6d956; id=88074396; xnsid=259b17d6; ver=7.0; loginfrom=null; JSESSIONID=abc6b6DNtKQEV76ZXzAVw; wp_fold=0; jebe_key=ffd6f5b2-dacd-41cd-a412-c6a607584e21%7Cb0d21ff9588dc759f4abf20782b29263%7C1562219928198%7C1%7C1562738357938");
        header.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
        header.put("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
    }

    Queue<String> uQueue;
    public RenrenPicThread(Queue<String> uQueue){
        this.uQueue = uQueue;
    }

    @Override
    public void run() {
        while (true){
                String uid = uQueue.poll();
                if(uid==null){
                    break;
                }
                getPhotoList(uid);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static AtomicInteger count = new AtomicInteger(0);
    private void getPhotoList(String uid){
        header.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
        header.put("Accept-Encoding","gzip, deflate");
        header.put("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8");

        header.put("Host","photo.renren.com");
        header.put("Referer","http://photo.renren.com/photo/"+uid+"/albumlist/v7?offset=0&limit=40");

        header.put("Upgrade-Insecure-Requests","1");

        header.put("Pragma","no-cache");
        header.put("Cache-Control","no-cache");
        //header.put("X-Requested-With","XMLHttpRequest");
        boolean found = false;
        try {
            HttpResult httpResult = HttpClientBuilder.getClient("http://photo.renren.com/photo/" + uid + "/albumlist/v7?offset=0&limit=100").setHeader(header).get();
            if(httpResult!= null&& StringUtils.isNotBlank(httpResult.getBody())){
                System.out.println(Thread.currentThread().getId()+" uid = [" + uid + "]"+(count.addAndGet(1)));
                String[] list = httpResult.getBody().split("\n");

                for(String line:list){
                    if(line.startsWith("'albumList'")){
                        found = true;
                        line = line.substring(13,line.length()-2);
                        line = line+"\n";
                    /*JSONArray jsonArray = JSON.parseArray(line);
                    Iterator<Object> iterator = jsonArray.iterator();
                    StringBuilder stringBuilder = new StringBuilder();
                    while (iterator.hasNext()){
                        JSONObject jsonObject = (JSONObject)iterator.next();
                        String id = jsonObject.getString("albumId");
                        String user = jsonObject.getString("ownerId");
                        String name = jsonObject.getString("albumName");
                        System.out.println("uid = [" + user + "]"+"id = [" + id + "]"+"name = [" + name + "]");

                        stringBuilder.append(user).append(" ");
                        stringBuilder.append(id).append(" ");

                        //http://photo.renren.com/photo/51176182/album-239303313/v7
                    }*/
                        writeFile(line);

                        break;
                    }
                }
            }else {
                //System.err.println("uid = <" + uid + ">" + httpResult.getBody());
            }

            if(!found){
                System.err.println("uid = <" + uid + ">" + httpResult.getStatusCode()+"\n"+httpResult.getBody());
                uQueue.offer(uid);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //System.out.println("uid = [" + httpResult.getBody() + "]");
        } catch (IOException e) {
            uQueue.offer(uid);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    private synchronized void writeFile(String line){
        try {
            Files.append(line, new File("D://renren-photo.txt"), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getPhoto(String uid,String albumId){
        //http://photo.renren.com/photo/88074396/album-955023740/v7
    }

    public static void main(String[] args) {
        String line = "'albumList': [\n" +
                "{\"cover\":\"http://fmn.xnpic.com/fmn056/20130926/0325/p/m3w232h162q85lt_large_AqmW_20a100000dc2118e.jpg\",\"albumName\":\"我滴宝\",\"albumId\":\"927341258\",\"ownerId\":222639720,\"sourceControl\":99,\"photoCount\":1,\"type\":0},\n" +
                "{\"cover\":\"http://fmn.xnpic.com/fmn056/20121124/2055/p/m3w232h162q85lt_large_jrSP_3d7c00003da5125f.jpg\",\"albumName\":\"20121020 婚\",\"albumId\":\"837958215\",\"ownerId\":222639720,\"sourceControl\":99,\"photoCount\":8,\"type\":0},{\"cover\":\"http://fmn.rrfmn.com/fmn061/20120614/1615/p/m3w232h162q85lt_large_sDKc_128d00000065118e.jpg\",\"albumName\":\"封面相册\",\"albumId\":\"638494584\",\"ownerId\":222639720,\"sourceControl\":99,\"photoCount\":1,\"type\":0},{\"cover\":\"http://fmn.rrfmn.com/fmn059/20120509/1110/p/m3w232h162q85lt_large_2DxN_7d0a00000814118e.jpg\",\"albumName\":\"20120507  婚纱照原片\",\"albumId\":\"624614177\",\"ownerId\":222639720,\"sourceControl\":99,\"photoCount\":81,\"type\":0},{\"cover\":\"http://fmn.xnpic.com/fmn056/20120507/1600/p/m3w232h162q85lt_large_JPc9_5d6e00000e19118d.jpg\",\"albumName\":\"20120507  婚纱照自拍\",\"albumId\":\"623820037\",\"ownerId\":222639720,\"sourceControl\":99,\"photoCount\":10,\"type\":0},{\"cover\":\"http://fmn.rrimg.com/fmn064/20120506/1525/p/m3w232h162q85lt_large_DXiA_27bd000005a4125e.jpg\",\"albumName\":\"{老梁的快乐生活~}\",\"albumId\":\"205039691\",\"ownerId\":222639720,\"sourceControl\":99,\"photoCount\":79,\"type\":0},{\"cover\":\"http://fmn.rrimg.com/fmn065/20110929/2310/p/m3w232h162q85lt_p_large_v2tl_36150001fd021269.jpg\",\"albumName\":\"手机相册\",\"albumId\":\"289313870\",\"ownerId\":222639720,\"sourceControl\":99,\"photoCount\":8,\"type\":3},{\"cover\":\"http://img.xiaonei.com/photos/0/0/main.jpg\",\"albumName\":\"人人爱车相册\",\"albumId\":\"476699937\",\"ownerId\":222639720,\"sourceControl\":99,\"photoCount\":0,\"type\":0},{\"cover\":\"http://img.xiaonei.com/photos/0/0/main.jpg\",\"albumName\":\"快速上传\",\"albumId\":\"448574575\",\"ownerId\":222639720,\"sourceControl\":99,\"photoCount\":0,\"type\":5},{\"cover\":\"http://fmn.xnpic.com/fmn040/20100613/2135/p/m3w232h162q85lt_p_large_cOgg_7d4b00004cbb2d0f.jpg\",\"albumName\":\"{国旗哥~}\",\"albumId\":\"377412262\",\"ownerId\":222639720,\"sourceControl\":99,\"photoCount\":2,\"type\":0},{\"cover\":\"http://fmn.rrfmn.com/fmn039/20100517/1920/p/m3w232h162q85lt_p_large_3S9M_47870000f18e2d0e.jpg\",\"albumName\":\"{不腹黑不雷不抽，我不爱~}\",\"albumId\":\"342147406\",\"ownerId\":222639720,\"sourceControl\":99,\"photoCount\":50,\"type\":0},{\"cover\":\"http://fmn.xnpic.com/fmn041/20100124/1700/p/m3w232h162q85lt_p_large_Lzjv_6b70000409ef2d0b.jpg\",\"albumName\":\"【100123】很RP很圆满的北京二巡\",\"albumId\":\"350883214\",\"ownerId\":222639720,\"sourceControl\":99,\"photoCount\":27,\"type\":0},{\"cover\":\"http://hdn.xnimg.cn/photos/hdn321/20150217/2020/p/m3w232h162q85lt_h_large_U319_ae9900063e5a1986.jpg\",\"albumName\":\"头像相册\",\"albumId\":\"224756432\",\"ownerId\":222639720,\"sourceControl\":99,\"photoCount\":13,\"type\":1}],\n";
        line = line.substring(13,line.length()-2);
        System.out.println();
        JSONArray jsonArray = JSON.parseArray(line);
        Iterator<Object> iterator = jsonArray.iterator();
        while (iterator.hasNext()){
            JSONObject jsonObject = (JSONObject)iterator.next();
            String id = jsonObject.getString("albumId");
            String user = jsonObject.getString("ownerId");
            String name = jsonObject.getString("albumName");
            System.out.println("uid = [" + user + "]"+"id = [" + id + "]"+"name = [" + name + "]");
        }
    }
}
