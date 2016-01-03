package net.yongpo.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by popo on 2014/10/7.
 */
@Controller
@RequestMapping("")
public class IndexController {
    Logger logger = LoggerFactory.getLogger(IndexController.class);
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home() {
        logger.info("==========="+System.currentTimeMillis());
        return "home";
    }

    @RequestMapping(value = "weixin", method = RequestMethod.GET)
    public String app() {

        return "detail";
    }


    @RequestMapping(value = "apk", method = RequestMethod.GET)
    @ResponseBody
    public Object doShell(@RequestParam("url") String apkurl) {
        return shell("sh ./bin/app/do.sh " + apkurl);
    }

    private List<String> shell(String cmd) {
        Process process = null;
        List<String> processList = new ArrayList<String>();
        try {
            process = Runtime.getRuntime().exec(cmd);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
                processList.add(line);
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return processList;
    }
}
