package net.yongpo.service;

/**
 * Created by benben on 2015/9/5.
 */
public enum  UserSourceEnum{
    WEB("网站");

    UserSourceEnum(String desc){
        this.desc = desc;
    }
    String desc;
}
