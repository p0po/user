package com.fangger.controller.user;

import com.fangger.utils.json.JsonResponse;
import org.apache.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by p0po on 15-6-2.
 */


@RequestMapping("/rest")
@org.springframework.web.bind.annotation.RestController
public class RestController {
    @RequestMapping(value = "ok",method = RequestMethod.GET)
    public Object ok() {
        return new ResponseEntity(JsonResponse.ok("dddd"), HttpStatus.OK);
    }
    @RequestMapping(value = "bad",method = RequestMethod.GET)
    public Object bad() {
        return new ResponseEntity(JsonResponse.bad("dddd"), HttpStatus.BAD_REQUEST);
    }
}
