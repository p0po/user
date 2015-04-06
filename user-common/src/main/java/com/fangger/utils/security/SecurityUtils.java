package com.fangger.utils.security;

/**
 * Created by p0po on 15/1/20.
 */

import com.google.common.base.Charsets;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

public class SecurityUtils {
    public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";
    public static final String PRIVATE_KEY = "87bd9232acabd325314dbaada8a4ebc843a6b5257abc7172dc3515032f7bd44e";

    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @param key  加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     * @throws Exception
     */
    final static byte[] bytes = new byte[]{1,2,3,4,5,6,7,8};
    public static String encode(String key,String data) {
        if(data == null)
            return null;
        try{
            SecureRandom sr = new SecureRandom();


            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);



            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec(bytes);
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.ENCRYPT_MODE, secretKey,iv);

/*
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey,sr);
*/
            byte[] bytes = cipher.doFinal(data.getBytes(Charsets.UTF_8));
            BASE64Encoder base64Encoder = new BASE64Encoder();

            //return byte2hex(bytes);
            return base64Encoder.encode(bytes);
        }catch(Exception e){
            e.printStackTrace();
            return data;
        }
    }

    /**
     * DES算法，解密
     *
     * @param data 待解密字符串
     * @param key  解密私钥，长度不能够小于8位
     * @return 解密后的字节数组
     * @throws Exception 异常
     */
    public static String decode(String key,String data) {
        if(data == null)
            return null;
        try {
            SecureRandom sr = new SecureRandom();

            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);

            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec(bytes);
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

/*

            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
*/
            BASE64Decoder base64Decoder = new BASE64Decoder();

            return new String(cipher.doFinal(base64Decoder.decodeBuffer(data)));
        } catch (Exception e){
            e.printStackTrace();
            return data;
        }
    }

    /**
     * 二行制转字符串
     * @param b
     * @return
     */
    private static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b!=null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }

    private static byte[] hex2byte(byte[] b) {
        if((b.length%2)!=0)
            throw new IllegalArgumentException();
        byte[] b2 = new byte[b.length/2];
        for (int n = 0; n < b.length; n+=2) {
            String item = new String(b,n,2);
            b2[n/2] = (byte)Integer.parseInt(item,16);
        }
        return b2;
    }

    public static void main(String[] args) {
        String aaa = SecurityUtils.encode("87bd9232acabd325314dbaada8a4ebc843a6b5257abc7172dc3515032f7bd44e","我爱你");
        System.out.println(aaa);
        String bbb = SecurityUtils.decode("87bd9232acabd325314dbaada8a4ebc843a6b5257abc7172dc3515032f7bd44e",aaa);
        System.out.println(bbb);
    }
}
