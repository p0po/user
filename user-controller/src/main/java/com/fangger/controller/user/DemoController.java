package com.fangger.controller.user;

import com.fangger.utils.json.JsonResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

@Controller
@RequestMapping("/demo")
public class DemoController {
    Logger logger = LoggerFactory.getLogger(DemoController.class);



    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public String helloWorld() {
        return "helloWorld";
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String helloView(Model model) {
        logger.debug("Thread ID:{}", Thread.currentThread().getId());
        model.addAttribute("message", "Hello World!");
        return "helloWorld";
    }


    /*
        @RequestMapping(value="/apktest",method = RequestMethod.GET)
        @ResponseBody
        public Callable<Map<String,String>> testApk(Model model) {
            return new Callable<Map<String,String>>() {
                public Map<String,String> call() throws Exception {
                    Map<String,String> permission = new HashMap<String,String>();
                    try {
                        permission = ApkUtils.getAndroidManifestUsePermission("d:/apk/weixin540android480.apk");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return permission;
                }
            };
        }

    */
    @RequestMapping(value = "/map", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> map() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key1", 1);
        Map<String, Object> key2 = new HashMap<String, Object>();
        key2.put("k21", 21);
        key2.put("k22", "你好");
        map.put("key2", key2);
        map.put("key3", "value3");
        return map;
    }

    @RequestMapping(value = "/jsonString1", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String map1() {
        String result = "hello：王永珀";
        return result;
    }

    @RequestMapping(value = "/jsonString2", method = RequestMethod.GET)
    @ResponseBody
    public String map2() {
        String result = "hello：王永珀";
        return result;
    }

    @RequestMapping(value = "/jsonString3", method = RequestMethod.GET)
    @ResponseBody
    public Object map3() {
        String result = "hello：王永珀";
        Map<String, String> map = new HashMap<String, String>();
        map.put("1", "english");
        map.put("2", "中文");
        map.put("3", null);
        return JsonResponse.ok(map);
    }

    @RequestMapping(value = "/owners/{ownerId}", method = RequestMethod.GET)
    @ResponseBody
    public String findOwner(@PathVariable("ownerId") String theOwner, Model model) {
        return "string: " + theOwner;
    }

	/*
    @RequestMapping(value="/owners/{ownerId}", method=RequestMethod.GET)
	@ResponseBody
	public String findOwner2(@PathVariable("ownerId") int theOwner,Model model) {
		return "int: "+theOwner;
	}
*/

    @RequestMapping(value = "/owners/me", method = RequestMethod.GET)
    @ResponseBody
    public String findOwner1(Model model) {
        return "me";
    }


	/*
	@RequestMapping(value="/owners/{ownerId}", method=RequestMethod.GET)
	@ResponseBody
	public String findOwner1(@PathVariable String ownerId, Model model) {
		return ownerId;
	}
	*/

    @RequestMapping(value = "/owners/{ownerId}/pets/{petId}", method = RequestMethod.GET)
    @ResponseBody
    public void findPet(
            @MatrixVariable Map<String, String> matrixVars,
            @MatrixVariable(pathVar = "petId") Map<String, String> petMatrixVars) {

        System.out.println(matrixVars);
        System.out.println(petMatrixVars);
		/*
		for(String key:matrixVars.keySet()){
			System.out.println("key:"+key+"  value:"+matrixVars.get(key).toString());
		}


		for(String key:petMatrixVars.keySet()){
			System.out.println("key:"+key+"  value:"+petMatrixVars.get(key).toString());
		}
		*/
        // matrixVars: ["q" : [11,22], "r" : 12, "s" : 23]
        // petMatrixVars: ["q" : 11, "s" : 23]
        //return "dd";
    }

    @RequestMapping(value = "/callable", method = RequestMethod.GET)
    public Callable<String> callabel() {
        return new Callable<String>() {
            public String call() throws Exception {
                Thread.sleep(5000);
                return "helloWorld";
            }
        };
    }


    DeferredResult<String> deferredResult = null;

    @RequestMapping("/defe")
    @ResponseBody
    public DeferredResult<String> quotes() {
        System.out.println("=======================");
        DeferredResult<String> deferredResult = new DeferredResult<String>(0);
        this.deferredResult = deferredResult;
        deferredResult.onCompletion(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("---------over---------");
                    }
                }
        );
        return deferredResult;
    }

    @RequestMapping("/defe1")
    @ResponseBody
    public void quotes1() {
        System.out.println("=========1111==============");
        if (this.deferredResult != null)
            this.deferredResult.setResult("11111111");
    }

    @RequestMapping("/meinv.jpg")
    public void meinv(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        String contextPath = httpServletRequest.getContextPath();

        logger.debug("contextPath:{}", contextPath);

        String userMsg = httpServletRequest.getHeader("Authorization");

        if (StringUtils.isEmpty(userMsg)) {
            httpServletResponse.setStatus(401);
            httpServletResponse.setHeader("WWW-Authenticate", "Basic realm=\" \"");
        } else {
            String userIp = getIpAddrByRequest(httpServletRequest);
            logger.debug("ip:{}  msg:{}", userIp, userMsg);
        }

        try {
            FileInputStream fileInputStream = new FileInputStream("/Users/p0po/Downloads/zzy.jpg");
            byte[] cache = new byte[fileInputStream.available()];
            int end = fileInputStream.read(cache);
            if (end != -1) {
                httpServletResponse.getOutputStream().write(cache);

                end = fileInputStream.read(cache);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String getIpAddrByRequest(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("Proxy-Client-IP");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("WL-Proxy-Client-IP");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getRemoteAddr();

        }

        return ip;

    }


}
