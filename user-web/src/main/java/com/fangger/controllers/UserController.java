package com.fangger.controllers;

import com.fangger.dao.mysql.model.User;
import com.fangger.exception.UserException;
import com.fangger.model.JsonResponse;
import com.fangger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by popo on 2014/10/3.
 */
@Controller
@RequestMapping("/user/")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value="add",method = RequestMethod.GET)
    public String addUserGet() {
        return "user/add";
    }

    @RequestMapping(value="add",method = RequestMethod.POST)
    @ResponseBody
    public Object addUserPost(User user) {
        try {
            userService.addUser(user);
        } catch (UserException e) {
            return JsonResponse.bad(e.getMessage());
        }
        return JsonResponse.ok(new String("11"));
    }

}
