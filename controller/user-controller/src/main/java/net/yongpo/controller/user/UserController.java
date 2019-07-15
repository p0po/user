package net.yongpo.controller.user;

import net.yongpo.controller.common.AppResponse;
import net.yongpo.model.Passport;
import net.yongpo.model.User;
import net.yongpo.service.UserService;
import net.yongpo.service.UserSourceEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * Created by benben on 2016/1/9.
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "reg", method = RequestMethod.POST, params = {})
    public Object reg(
            //@RequestParam("uuid") String uuid,
            /** 昵称 */
            @RequestParam("nickname") String nickname,

            /** 真实姓名 */
            @RequestParam("realName") String realName,

            /** 手机号 */
            @RequestParam("phone") Long phone,

            /** 邮箱 */
            @RequestParam("email") String email,

            /** 性别 */
            @RequestParam("gender") Boolean gender,

            /** 出生日期 */
            @RequestParam("birthday") long birthday,

            /** 微信ID */
            @RequestParam("openid") String openid

    ) {
        User user = new User();

        user.setSource(UserSourceEnum.WEB.name());
        user.setNickname(nickname);
        user.setRealName(realName);
        user.setPhone(phone);
        user.setEmail(email);
        user.setGender(gender);
        user.setDeleted(false);
        user.setBirthday(new Date(birthday));

        Passport passport = new Passport();
        passport.setPassword("null");
        try {
            int row = userService.add(user,passport);
            return AppResponse.ok(row);
        }catch (Exception e){
            return AppResponse.bad(e.getMessage());
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, params = {})
    public Object home(
            @PathVariable("id") String uid
    ) {
        return "user/home";
    }

    @RequestMapping(value = "benben", method = RequestMethod.GET, params = {})
    public Object benben(

    ) {
        return "user/benben";
    }
}
