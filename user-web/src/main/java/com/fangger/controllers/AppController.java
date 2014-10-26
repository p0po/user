package com.fangger.controllers;

import com.fangger.dao.mysql.model.App;
import com.fangger.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by popo on 2014/10/25.
 */
@Controller
@RequestMapping("/app")
public class AppController {
    @Autowired
    AppService appService;

    @RequestMapping(value="add",method = RequestMethod.GET)
    public String addGet(){
        return "apk/add";
    }

    @RequestMapping(value="add",method = RequestMethod.POST)
    @ResponseBody
    public String addPost(App app){
        System.out.println(app);
        appService.saveApk(app);
        return "apk/add";
    }

}
