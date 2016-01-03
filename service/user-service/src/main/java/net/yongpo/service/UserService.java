package net.yongpo.service;

import net.yongpo.mapper.PassportMapper;
import net.yongpo.mapper.UserMapper;
import net.yongpo.model.Passport;
import net.yongpo.model.PassportCondition;
import net.yongpo.model.User;
import net.yongpo.model.UserCondition;
import com.google.common.base.Preconditions;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by p0po on 15-6-14.
 */
@Service
public class UserService {
    static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    UserMapper userMapper;
    @Autowired
    PassportMapper passportMapper;

    /**
     * 添加用户
     */
    @Transactional
    public int add(User user,Passport passport){
        /**
         * check user
         */
        Preconditions.checkNotNull(user,"user can not be null!");
        Preconditions.checkArgument(user.getId() == null, "userId must be null");
        Preconditions.checkNotNull(user.getNickname(),"nickName can not be null");

        /**
         * check passport
         */
        Preconditions.checkNotNull(passport,"passport can not be null!");
        Preconditions.checkNotNull(passport.getPassword(), "Password can not be null!");

        /**
         * check nickName
         */
        UserCondition userCondition = new UserCondition();
        userCondition.createCriteria().andNicknameEqualTo(user.getNickname());
        List<User> userNicknameList = userMapper.selectByCondition(userCondition);
        Preconditions.checkArgument(CollectionUtils.isEmpty(userNicknameList), "nickname has been used");

        /**
         * add user
         */
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userMapper.insert(user);
        logger.info("add user:{}", user);

        /**
         * add passport
         */
        if(user.getId()>0){
            passport.setUserId(user.getId());

            String salt = RandomStringUtils.randomAlphabetic(16);
            passport.setSalt(salt);

            String password = DigestUtils.sha1Hex(salt+passport.getPassword());
            passport.setPassword(password);
            passport.setUpdateTime(new Date());

            passportMapper.insert(passport);
        }

        return user.getId();
    }


    /**
     * 昵称登录
     * @param name
     * @param password
     * @return
     */
    boolean loginByNickName(String name,String password){
        UserCondition userCondition = new UserCondition();
        userCondition.createCriteria().andNicknameEqualTo(name);
        List<User> userList = userMapper.selectByCondition(userCondition);
        if(CollectionUtils.isNotEmpty(userList)){
            User user = userList.get(0);
            PassportCondition passportCondition = new PassportCondition();
            passportCondition.createCriteria().andIdEqualTo(user.getId());
            List<Passport> passportList = passportMapper.selectByCondition(passportCondition);

            if(CollectionUtils.isNotEmpty(passportList)){
                Passport passport = passportList.get(0);
                String salt = passport.getSalt();
                return  DigestUtils.sha1Hex(salt+password).equals(passport.getPassword());
            }

        }
       return false;
    }

    /**
     * Email登录
     * @param email
     * @param password
     * @return
     */
    boolean loginByEmail(String email,String password){
        UserCondition userCondition = new UserCondition();
        userCondition.createCriteria().andEmailEqualTo(email);
        List<User> userList = userMapper.selectByCondition(userCondition);
        if(CollectionUtils.isNotEmpty(userList)){
            User user = userList.get(0);
            PassportCondition passportCondition = new PassportCondition();
            passportCondition.createCriteria().andIdEqualTo(user.getId());
            List<Passport> passportList = passportMapper.selectByCondition(passportCondition);

            if(CollectionUtils.isNotEmpty(passportList)){
                Passport passport = passportList.get(0);
                String salt = passport.getSalt();
                return  DigestUtils.sha1Hex(salt+password).equals(passport.getPassword());
            }
        }
        return false;
    }

    /**
     * 手机号登录
     * @param phone
     * @param password
     * @return
     */
    boolean loginByPhone(long phone,String password){
        UserCondition userCondition = new UserCondition();
        userCondition.createCriteria().andPhoneEqualTo(phone);
        List<User> userList = userMapper.selectByCondition(userCondition);
        if(CollectionUtils.isNotEmpty(userList)){
            User user = userList.get(0);
            PassportCondition passportCondition = new PassportCondition();
            passportCondition.createCriteria().andIdEqualTo(user.getId());
            List<Passport> passportList = passportMapper.selectByCondition(passportCondition);

            if(CollectionUtils.isNotEmpty(passportList)){
                Passport passport = passportList.get(0);
                String salt = passport.getSalt();
                return  DigestUtils.sha1Hex(salt+password).equals(passport.getPassword());
            }
        }
        return false;
    }

}
