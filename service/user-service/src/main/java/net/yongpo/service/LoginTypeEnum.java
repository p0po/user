package net.yongpo.service;

/**
 * Created by benben on 2015/9/4.
 */
public enum LoginTypeEnum{
    NickName("昵称"),
    Email("邮箱"),
    Phone("手机号");
    String desc;
    LoginTypeEnum(String desc){
        this.desc = desc;
    }

}
