package net.yongpo.controller.user;

import net.yongpo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by benben on 2016/1/9.
 */
@Controller
@RequestMapping("user")
public class HomeController {
    @Autowired
    UserService userService;
    @RequestMapping(value = "{id}",method = RequestMethod.GET,params = {})
    public Object home(
    @PathVariable("id") String uid
    ){
            return "user/home";
    }

    @RequestMapping(value = "benben",method = RequestMethod.GET,params = {})
    public Object benben(

    ){
        return "user/benben";
    }
}
