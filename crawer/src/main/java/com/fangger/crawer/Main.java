package com.fangger.crawer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fangger.utils.httpclient.GetClient;

/**
 * Created by p0po on 15-2-24.
 */
public class Main {
    int[] city = {110000};
    int[] district = {1, 2, 3, 4, 5, 8, 9, 10, 11, 12, 14, 13, 15, 16, 17};

    public static void main(String[] args) {
        String result = GetClient.getWithPool("http://m.lianjia.com/wap/serverapi/communityByCondition?type=0&cityId=110000&districtId=2&start=0&rows=300");

        JSONObject jsonObject = (JSONObject) JSON.parse(result);

        int total = jsonObject.getInteger("numFound");
        int start = jsonObject.getInteger("start");
        JSONArray jsonArray = jsonObject.getJSONArray("docs");

        System.out.println(jsonArray.size() + "  " + total);
    }
}
