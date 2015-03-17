package com.fangger.dao.rose;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by p0po on 15-3-15.
 */
@RestController
@RequestMapping("/demo1")
public class AController {
    @Autowired
    TestDAO testDAO;


    @RequestMapping(value = "rose-jade", method = RequestMethod.GET)
    @ResponseBody
    public Object jade() {
        return testDAO.getAll();
    }
}
