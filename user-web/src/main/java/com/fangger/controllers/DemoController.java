package com.fangger.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.fangger.annotation.Reg;
import com.fangger.apk.ApkUtils;
import com.fangger.dao.mysql.model.User;
import com.fangger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

@Controller
@RequestMapping("/demo")
public class DemoController {
	@Autowired
    UserService userService;

    @RequestMapping(value="",method = RequestMethod.GET)
	@ResponseBody
    public String helloWorld() {
        return "helloWorld";
    }

	@RequestMapping(value="/view",method = RequestMethod.GET)
	public String helloView(Model model) {
		model.addAttribute("message", "Hello World!");
		return "helloWorld";
    }

    @RequestMapping(value="/mysql",method = RequestMethod.GET)
    @ResponseBody
    public Object getMysqlData(Model model) {
        List<User> userList = userService.getAllUser();
        StringBuilder sb = new StringBuilder();
        for(User user:userList){
            sb.append(user.toString());
        }
        return userList;
    }

    @RequestMapping(value="/apktest",method = RequestMethod.GET)
    @ResponseBody
    public Callable<Object> testApk(Model model) {
        return new Callable<Object>() {
            public Object call() throws Exception {
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


	@RequestMapping(value="/map",method = RequestMethod.GET,produces="application/json")
	@ResponseBody
	public Map<String,Object> map(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("key1", 1);
		Map<String,Object> key2 = new HashMap<String,Object>();
		key2.put("k21", 21);
		key2.put("k22", "22");
		map.put("key2", key2);
		map.put("key3", "value3");
		return map;
	}

	@RequestMapping(value="/owners/{ownerId}", method=RequestMethod.GET)
	@ResponseBody
	public String findOwner(@Reg("\\d+")@PathVariable("ownerId") String theOwner,Model model) {
		return theOwner;
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
	        @MatrixVariable(pathVar="petId") Map<String, String> petMatrixVars) {

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
	public Callable<String> callabel(){
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
	    if(this.deferredResult != null)
		this.deferredResult.setResult("11111111");
	}
}
