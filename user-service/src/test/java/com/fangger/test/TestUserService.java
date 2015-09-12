package com.fangger.test;

import com.fangger.model.Passport;
import com.fangger.model.User;
import com.fangger.service.UserService;
import com.fangger.service.UserSourceEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by benben on 2015/9/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mvc*.xml",})
public class TestUserService {
    @Autowired
    UserService userService;

    @Test
    public void testAdd(){
        User user = new User();
        user.setNickname("testUser1");
        user.setGender(false);
        user.setSource(UserSourceEnum.WEB.name());

        Passport passport = new Passport();
        passport.setSource(UserSourceEnum.WEB.name());
        passport.setPassword("123456");
        passport.setLastLoginIp("10.0.0.1");
        passport.setLastLoginTime(new Date());

        userService.add(user,passport);
    }
}
