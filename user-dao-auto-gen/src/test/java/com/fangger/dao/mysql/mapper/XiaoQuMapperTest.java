package com.fangger.dao.mysql.mapper;

import com.fangger.mapper.XiaoQuMapper;
import com.fangger.model.XiaoQu;
import com.fangger.model.XiaoQuCondition;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by popo on 2014/10/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-dataSource.xml"})
public class XiaoQuMapperTest {
    @Autowired
    XiaoQuMapper xiaoQuMapper;

    @Test
    public void testCondition(){
        XiaoQuCondition xiaoQuCondition = new XiaoQuCondition();
        xiaoQuCondition.createCriteria().andIdIsNotNull();
        List<XiaoQu> xiaoQuList = xiaoQuMapper.selectByCondition(xiaoQuCondition);
        Assert.assertTrue(xiaoQuList.size()>0);
    }
}
