package com.fangger.model;

import com.alibaba.fastjson.JSON;

/**
 * Created by popo on 2014/10/3.
 */
public class JsonResponse {
    int code = 0;
    Object content;
    public static JsonResponse ok(){
        return new JsonResponse(0,"");
    }
    public static JsonResponse ok(Object content){
        return new JsonResponse(0,content);
    }
    public static JsonResponse bad(){
        return new JsonResponse(1,"");
    }
    public static JsonResponse bad(Object content){
        return new JsonResponse(1,content);
    }
    public static JsonResponse other(int code,Object content){
        return new JsonResponse(code,content);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    private JsonResponse(int code,Object content){
            this.code = code;
            this.content = content;
    }

}
