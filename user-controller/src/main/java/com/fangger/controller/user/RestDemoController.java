package com.fangger.controller.user;

import com.fangger.utils.json.JsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by p0po on 15/1/9.
 */
@RestController
@RequestMapping("/demo1")
public class RestDemoController {
    @RequestMapping(value = "/jsonString3", method = RequestMethod.GET)
    @ResponseBody
    public Object map3() {
        String result = "hello：王永珀";
        Map<String, String> map = new HashMap<>();
        map.put("1", "english");
        map.put("2", "中文");
        map.put("3", null);
        return JsonResponse.ok(map);
    }
    @RequestMapping(value = "ok",method = RequestMethod.GET)
    public Object ok() {
        return new ResponseEntity(JsonResponse.ok("dddd"), HttpStatus.OK);
    }
    @RequestMapping(value = "bad",method = RequestMethod.GET)
    public Object bad() {
        return new ResponseEntity(JsonResponse.bad("dddd"), HttpStatus.BAD_REQUEST);
    }

}
