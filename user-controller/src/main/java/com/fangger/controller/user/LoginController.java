package com.fangger.controller.user;


import com.fangger.mapper.UserMapper;
import com.fangger.utils.json.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by benben on 2015/9/4.
 */
@RequestMapping("user")
@RestController
public class LoginController {
    @Autowired
    UserMapper userMapper;

    @RequestMapping(value = "login")
    public Object login(){
        userMapper.selectById(0);
        return JsonResponse.ok();
    }
}
