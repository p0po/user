package net.yongpo.utils.httpclient;

import com.google.common.collect.ImmutableMap;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by p0po on 15-6-26.
 */
public class Demo {
    public static void main(String[] args) {
        //HttpHost httpHost = new HttpHost("111.161.65.83",80);
  /*      try {
            HttpResult httpResult = HttpClientBuilder.getClient("http://1111.ip138.com/ic.asp")
                    .setCharset(Charset.forName("gb2312"))
                   // .setProxy(httpHost)
                    .get();
            if(httpResult.getStatusCode() == 200){
                String result = httpResult.getBody();
                System.out.println(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/



        try {

            Map<String,String> body = ImmutableMap.builder()
                    .put("captcha","")
                    .build().of(
                            "user[verify_code]", "123456",
                            "user[password]", "1234567",
                            "user[login]:", "13011111111",
                            "commit", "注册",
                            "captcha_key", "739 c13d93ab8edca0b35a8a9eacedbe63c8c0f0f"
                    );

            HttpResult httpResult = HttpClientBuilder.getClient("https://passport.36kr.com/users/sign_in.json")
                    .setCharset(Charset.forName("UTF-8"))
                            .setHeader(ImmutableMap.of(
                                    "X-CSRF-Token", "eQdIWIEnm983CPspcZ3lgQrH2uvPoy2y3+d56Tm3e6Q=",
                                    "Cookie", "kr_stat_uuid = RixAm23865582; _auth_session=cfa08e3ef8c14fe9fe8f2149f5312143; _krypton_session = f8d8b20c7191fb73757c420999325cdc; Hm_lvt_713123c60a0e86982326bae1a51083e1 = 1435141720, 1437559940;Hm_lpvt_713123c60a0e86982326bae1a51083e1 = 1437559993;krchoasss = eyJpdiI6IlNUdzZiUU5hd1VaUnAyMmpIMXY2ZUE9PSIsInZhbHVlIjoib3drTXlMVUd6TzYxQ2p1T3VaXC9nc0FKdEhyT2xXMTZ2WEFBU2lReTE0WE9PTkJSU2JoWmRQOWhGZnhtNlZWK1krVEZPMzBLN1p2TW55QnJaOXRXR0ZnPT0iLCJtYWMiOiJjNzQyNWZhZjIzY2I1OGIwOGQ4NmQ5MDJhOTU2ZTcwNzZjZTRmOWEyN2EyNzYxYzViYTMyNDEyMjYyNjJhNGQ2In0%3D; _ga=GA1.2.323180639.1430385682; request_method=POST; _passport_session=2074dd08426cacb7bd7b21e1a096ea9b",
                                    "Host", "passport.36kr.com",
                                    "Origin", "https://passport.36kr.com"
                            ))
                            .setBody(body)
            // .setProxy(httpHost)
                    .post();
                System.out.println(httpResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
