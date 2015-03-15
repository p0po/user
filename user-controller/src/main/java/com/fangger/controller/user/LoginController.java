package com.fangger.controller.user;

import com.fangger.utils.cookie.CookieUtil;
import com.fangger.dao.mysql.model.User;
import com.fangger.utils.json.JsonResponse;
import com.fangger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by popo on 2014/10/7.
 */
@Controller
@RequestMapping("")
public class LoginController {
    @Autowired
    UserService userService;

    @RequestMapping(value="login",method = RequestMethod.GET)
    public String loginGet(){
        return "user/login";
    }

    @RequestMapping(value="ajaxlogin",method = RequestMethod.POST)
    @ResponseBody
    public Object ajaxLoginPost(@RequestParam("account")String account,@RequestParam("password")String password){
        if(StringUtils.isEmpty(account)){
            return JsonResponse.bad("用户名不能为空");
        }
        if(StringUtils.isEmpty(password)){
            return JsonResponse.bad("密码不能为空");
        }
        User user = userService.login(account,password);
        if(user != null){
            return JsonResponse.ok();
        }
        return JsonResponse.bad("用户名或者密码错误");
    }

    @RequestMapping(value="login",method = RequestMethod.POST)
    public ModelAndView loginPost(@RequestParam("account")String account
            ,@RequestParam("password")String password
            ,HttpServletRequest request
            ,HttpServletResponse response){
        User user = userService.login(account,password);
        if(user != null){
            CookieUtil.getIntence(response)
                    .add("uid",String.valueOf(user.getId()));
            return new ModelAndView(new RedirectView("/success"));
        }
        return new ModelAndView(new RedirectView("/failure"));
    }
}
