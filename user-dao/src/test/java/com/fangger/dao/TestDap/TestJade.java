package com.fangger.dao.TestDap;

import com.fangger.dao.TestDAO;
import com.fangger.mapper.XiaoQuMapper;
import com.fangger.model.XiaoQu;
import com.fangger.model.XiaoQuCondition;
import org.apache.commons.jexl2.UnifiedJEXL;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by p0po on 15-3-21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-dataSource.xml"})
public class TestJade {
    @Autowired
    TestDAO testDAO;
    @Autowired
    XiaoQuMapper xiaoQuMapper;

    @Test
    @Transactional
    public void testJade(){
        List<XiaoQu> xiaoQuList = testDAO.getAll();
        for(XiaoQu xiaoqu:xiaoQuList){
            System.out.println(xiaoqu.getName());
        }


       /* XiaoQu xiaoQu = new XiaoQu();
        xiaoQu.setName("p0po----");
        xiaoQu.setDistrictId("ddfd");
        xiaoQu.setSource(123);
        xiaoQuMapper.insert(xiaoQu);*/

       /* try{
            throw new RuntimeException("----");
        }catch (Exception e){

        }*/


        XiaoQuCondition xiaoQuCondition = new XiaoQuCondition();
        xiaoQuCondition.createCriteria().andIdIsNotNull();
        List<XiaoQu> xiaoQuList1 = xiaoQuMapper.selectByCondition(xiaoQuCondition);
        System.out.println("==============================");

        for(XiaoQu xiaoqu:xiaoQuList1){
            System.out.println(xiaoqu.getName());
        }


        System.out.println("==============================");
        XiaoQuCondition xiaoQuCondition1 = new XiaoQuCondition();
        xiaoQuCondition1.createCriteria().andNameEqualTo("p0po----");
        xiaoQuMapper.deleteByCondition(xiaoQuCondition1);



        List<XiaoQu> xiaoQuList2 = xiaoQuMapper.selectByCondition(xiaoQuCondition);

        for(XiaoQu xiaoqu:xiaoQuList2){
            System.out.println(xiaoqu.getName());
        }
        Assert.assertTrue(xiaoQuList.size() != xiaoQuList2.size());
    }
}
