package com.fangger.crawer;

import com.fangger.utils.httpclient.HttpClientBuilder;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import org.apache.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by p0po on 15-2-24.
 */
public class Main {
    //static volatile boolean toContinue =true;
    public static int interval = 0;
    final static int ThreadNum = 10;
    private static final Logger logger = LoggerFactory.getLogger("success");
    public static final ExecutorService exec = Executors.newFixedThreadPool(ThreadNum);
    public static final List<HttpHost> hostList = new ArrayList<>();


    private static final String filePath = "/app/Dm_Mobile.txt";

    public static boolean resolveLine(String line){
        String[] lineArray = line.split("\\s{2,}");
        int index = Integer.valueOf(lineArray[0]); //序号
        String prefixNum = lineArray[1]; //号码段
        String area = lineArray[2];//地区
        String corperation = lineArray[3];//运营商
        //String areaCode = lineArray[4];//区号

    if (area.equals("北京") && corperation.contains("移动") && !corperation.contains("虚拟运营商")) {
        System.out.println("prefixNum = [" + prefixNum + "] area=[" + area + "] 运营商=[" + corperation + "]");

        if(Integer.valueOf(prefixNum) >= 0){
            crow(prefixNum);
            //toContinue = false;534017
        }
    }
        return true;
    }

    //private static String url = "https://user.lufax.com/user/service/user/check-phone-is-used?newPhoneNum=";
    //private static String url = "https://www.fengjr.com/api/v2/register/check_mobile";
    //private static String url = "https://www.ezubo.com/member/common/PhoneUnique/";
     private static  String url = "https://licai.lianjia.com/register/checkUserExist?mobile=";
    static Random random = new Random();

    public static void crow(String phonePre){

        //HttpHost proxy = hostList.get(random.nextInt(hostList.size()));
        //System.out.println("proxy = [" + proxy + "]");

        List<String> phones = genPhoneNum(phonePre);
        for(String phone:phones){
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.getClient(url+phone);
            //Map<String,String> map = new HashMap<>();
            //map.put("cellphone", phone);
            // HttpClientBuilder httpClientBuilder = HttpClientBuilder.getClient(url)
                    //.setBody(map)
             //       .setConnectionTimeOut(10000)
                       // .setProxy(proxy)
                        ;
            AThread aThread = new AThread(httpClientBuilder);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            exec.execute(aThread);
        }
    }

    public static void main(String[] args) {

        try {
            List<String> lines = Files.readLines(new File("/app/proxy.txt"),Charset.defaultCharset());
            Set<String> set = new HashSet<>();
            for(String s:lines){
                set.add(s);
            }
            for(String line:set){
                String[] array = line.split(" ");
                hostList.add(new HttpHost(array[0],Integer.valueOf(array[1]),array[4]));
            }
        } catch (IOException e) {
            System.out.println("errr");
        }

        //System.out.println("https://user.lufax.com/user/service/user/check-phone-is-used?newPhoneNum=13501011001".substring(73));
        File file = new File(filePath);

        try {
           List<String> dd = Files.readLines(file, Charset.forName("GBK"),
                    new LineProcessor<List<String>>() {
                        @Override
                        public boolean processLine(String line) throws IOException {
                            long start = System.currentTimeMillis();
                            while (((ThreadPoolExecutor)exec).getActiveCount() != 0){
                                try {
                                    Thread.sleep(interval);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            System.out.println("耗时:"+(System.currentTimeMillis()-start));
                            return resolveLine(line);
                        }

                        @Override
                        public List<String> getResult() {
                            return null;
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("============================================================================");
            exec.shutdown();
        }
    }




    private static List<String> genPhoneNum(String head) {
        List<String> num = new ArrayList<>(10000);
        for (int i = 9999; i >= 0; i--) {
            if (i > 999) {
                num.add(head + i);
            } else if (i >= 100) {
                num.add(head + "0" + i);
            } else if (i >= 10) {
                num.add(head + "00" + i);
            } else {
                num.add(head + "000" + i);
            }
        }
        return num;
    }

}
