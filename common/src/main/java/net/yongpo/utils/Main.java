package net.yongpo.utils;

import net.yongpo.utils.httpclient.HttpClientBuilder;
import net.yongpo.utils.httpclient.HttpResult;

import java.io.IOException;

/**
 * Created by benben on 2015/9/20.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        /*for(int i =0;i<100000;i++){
            DUTestThread duTestThread = new DUTestThread();
            Thread thread = new Thread(duTestThread);
            thread.start();

            DuThread1 thread1 = new DuThread1();
            Thread thread2 = new Thread(thread1);
            thread2.start();
        }*/


        /*while (true){
            HttpResult httpResult = HttpClientBuilder.getClient("http://www.fengjr.com/api/v2/creditassign/filter?minRate=0&maxRate=5000&minLeftMonths=0&maxLeftMonths=50&repayMethod=all&status=OPEN&pageSize=20&page=1").get();
            if(httpResult.getStatusCode() == 200){
                String body = httpResult.getBody();
                JSONObject jsonObject = JSON.parseObject(body);
                if(jsonObject.getInteger("totalSize")>0){
                    System.out.println("==========" + jsonObject.getInteger("totalSize"));
                    return;
                }else {
                    System.out.println(jsonObject.toJSONString());
                }
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/

        if(sendMobile("13777777771")){

        }

    }

    private static boolean sendMobile(String mobile){
        HttpResult httpResult = null;
        try {
            httpResult = HttpClientBuilder.getClient("https://m.fengjr.com/api/user/register/code").setToPostJsonString("{\"mobile\": \""+mobile+"\"}").post();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(httpResult.getStatusCode() == 200&&"{}".equals(httpResult.getBody())){
            System.out.println(httpResult.getBody());
            return true;
        }
        return false;
    }

    
}
