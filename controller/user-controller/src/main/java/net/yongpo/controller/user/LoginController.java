package net.yongpo.controller.user;


import net.yongpo.model.Passport;
import net.yongpo.model.User;
import net.yongpo.service.UserService;
import net.yongpo.service.UserSourceEnum;
import net.yongpo.utils.StringUtils;
import net.yongpo.utils.json.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by benben on 2015/9/4.
 */
@Controller
@RequestMapping("user")
public class LoginController {
    Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    UserService userService;

    @RequestMapping(value = "login")
    public String login(){
        logger.info("login");
        return null;
    }

    @RequestMapping(value = "login",method = RequestMethod.POST,params = {})
    @ResponseBody
    public Object login(
            @RequestParam(value = "nick_name",required = false,defaultValue = "")String nickName,
            @RequestParam(value = "phone",required = false,defaultValue = "0")int phone,
            @RequestParam(value = "email",required = false,defaultValue = "")String email,
            @RequestParam(value = "pass",required = true)String password
            ){

        boolean successfull = false;
        if(StringUtils.isNotEmpty(nickName)){
            successfull = userService.loginByNickName(nickName, password);
        }else if(StringUtils.isNotEmpty(email)){
            successfull = userService.loginByEmail(email,password);
        }else if(phone>0){
            successfull = userService.loginByPhone(phone,password);
        }

        if(successfull){
            logger.info("nickName:{},phone:{},email:{}", nickName, phone, email);
            return JsonResponse.ok();
        }else {
            logger.info("nickName:{},phone:{},email:{},bad password:{}",password);
            return JsonResponse.bad();
        }

    }

    @RequestMapping(value = "reg",method = RequestMethod.GET)
    public String reg(){
        return "user/reg";
    }

    @RequestMapping(value = "reg",method = RequestMethod.POST, params = {})
    @ResponseBody
    public Object reg(
            @RequestParam(value = "nick_name",required = true)String nickName,
            @RequestParam(value = "password",required = true)String password,
            @RequestParam(value = "email",required = true)String email,
            @RequestParam(value = "phone",required = false)long phone,
            @RequestParam(value = "gender",required = false)boolean gender,
            @RequestParam(value = "birth",required = false)long birth
    ){
        if(StringUtils.isEmpty(email)){
            return JsonResponse.bad("email 必须填写");
        }

        if(!StringUtils.isEmail(email)){
            return JsonResponse.bad("email 不合法");
        }

        if(StringUtils.isEmpty(nickName)){
            return JsonResponse.bad("昵称 必须填写");
        }

        if(!StringUtils.isAlphanumeric(nickName)){
            return JsonResponse.bad("用户名只允许小写字符和数字");
        }

        if(nickName.length()<4){
            return JsonResponse.bad("用户名至少4个字符");
        }

        if(StringUtils.isEmpty(password)){
            return JsonResponse.bad("密码不允许为空");
        }

        if(password.length()<6){
            return JsonResponse.bad("密码至少6个字符");
        }

        logger.info("nickName:{},phone:{},email:{},password:{},gender:{}", nickName, phone, email, password,gender);
        User user = new User();
        user.setNickname(nickName);
        user.setSource(UserSourceEnum.WEB.name());
        user.setPhone(phone);
        user.setEmail(email);
        user.setGender(gender);
        user.setBirthday(new Date(birth));

        Passport passport = new Passport();
        passport.setSource(UserSourceEnum.WEB.name());
        passport.setPassword(password);
        int userId = userService.add(user,passport);

        return userId>0?JsonResponse.ok():JsonResponse.bad();
    }

}
