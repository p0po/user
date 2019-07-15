package net.yongpo.utils;

/**
 * Created by benben on 2016/1/8.
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils{
    public static boolean isEmail(String str){
        return isEmpty(str)?false:str.matches("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");
    }

    public static boolean isMobile(String str){
        return isEmpty(str)?false:str.matches("^(13|14|15|17|18)\\d{9}$");
    }

}
