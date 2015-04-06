package com.fangger.crawer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fangger.utils.httpclient.GetClient;
import com.fangger.utils.httpclient.HttpResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by p0po on 15-2-24.
 */
public class Main {
    int[] city = {110000};
    int[] district = {1, 2, 3, 4, 5, 8, 9, 10, 11, 12, 14, 13, 15, 16, 17};

    private static final ExecutorService exec = Executors.newFixedThreadPool(512);

    public static void main(String[] args) {
        /*String result = GetClient.getWithPool("http://m.lianjia.com/wap/serverapi/communityByCondition?type=0&cityId=110000&districtId=2&start=0&rows=300");

        JSONObject jsonObject = (JSONObject) JSON.parse(result);

        int total = jsonObject.getInteger("numFound");
        int start = jsonObject.getInteger("start");
        JSONArray jsonArray = jsonObject.getJSONArray("docs");

        System.out.println(jsonArray.size() + "  " + total);*/
        long s = System.currentTimeMillis();
        int success = 0;
        int failure = 0;
        try {
            List<Future<HttpResult>> list = new ArrayList<>();

            for(int i=0;i<10240;i++)
            list.add(GetClient.getWithPool("http://www.baidu.com",exec));


            Iterator<Future<HttpResult>> iterable = list.iterator();
            while (iterable.hasNext()){
                Future<HttpResult>  httpResultFuture = iterable.next();
                success++;
                try {
                    HttpResult httpResult1 = httpResultFuture.get();
                    System.out.println(success+"   "+httpResult1.getStatusCode());
                    iterable.remove();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

            //HttpResult httpResult = GetClient.get("http://www.fangger.com/");

            //System.out.println(httpResult.getBody());
            //System.out.println("============"+httpResult.getStatusCode());
        }catch (Exception e){
            failure++;
        }finally {
            exec.shutdown();
        }

        System.out.println("total:"+(success+failure)+" success:"+success+" failure:"+failure);
        System.out.println("成功率:"+1.00*success/(success+failure));
        long t = System.currentTimeMillis()-s;
        System.out.println("耗时:"+(t)+"ms");
        System.out.println(1.0*(success+failure)/t+" 个/毫秒");
    }
}
