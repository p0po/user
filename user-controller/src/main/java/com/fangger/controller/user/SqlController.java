package com.fangger.controller.user;

import com.fangger.dao.TestDAO;
import com.fangger.mapper.XiaoQuMapper;
import com.fangger.model.XiaoQuCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by p0po on 15-3-15.
 */
@RestController
@RequestMapping("/sql")
public class SqlController {
    @Autowired
    TestDAO testDAO;

    @Autowired
    XiaoQuMapper xiaoQuMapper;


    @RequestMapping(value = "jade", method = RequestMethod.GET)
    @ResponseBody
    public Object jade() {
        return testDAO.getAll();
    }

    @RequestMapping(value = "mybatis", method = RequestMethod.GET)
    @ResponseBody
    public Object mybatis() {
        XiaoQuCondition xiaoQuCondition = new XiaoQuCondition();
        xiaoQuCondition.createCriteria().andIdIsNotNull();
        return xiaoQuMapper.selectByCondition(xiaoQuCondition);
    }
}
