package com.fangger.controller.user;


import com.fangger.mapper.UserMapper;
import com.fangger.model.Passport;
import com.fangger.model.User;
import com.fangger.service.UserService;
import com.fangger.service.UserSourceEnum;
import com.fangger.utils.json.JsonResponse;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by benben on 2015/9/4.
 */
@Controller
@RequestMapping("user")
public class LoginController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "login")
    public Object login(){

        return JsonResponse.ok();
    }

    @RequestMapping(value = "reg",method = RequestMethod.GET)
    public String reg(){
        return "user/reg";
    }

    @RequestMapping(value = "reg",method = RequestMethod.POST, params = "")
    public Object reg(
            @RequestParam(value = "nickName",required = true)String nickName,
            @RequestParam(value = "password",required = true)String password,
            @RequestParam(value = "phone",required = false,defaultValue = "0")long phone,
            @RequestParam(value = "email",required = false,defaultValue = "")String email,
            @RequestParam(value = "gender",required = false,defaultValue = "true")boolean gender,//true 1,默认是男
            @RequestParam(value = "birth",required = true)Date birth
    ){

        User user = new User();
        user.setNickname(nickName);
        user.setSource(UserSourceEnum.WEB.name());
        user.setPhone(phone);
        user.setEmail(email);
        user.setGender(gender);
        user.setBirthday(birth);

        Passport passport = new Passport();
        passport.setSource(UserSourceEnum.WEB.name());
        passport.setPassword(password);
        int userId = userService.add(user,passport);

        return userId>0?JsonResponse.ok():JsonResponse.bad();
    }


}
