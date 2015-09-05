package com.fangger.dao.TestDao;

import com.fangger.dao.TestDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by p0po on 15-3-21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-dataSource.xml"})
public class TestJade {
    @Autowired
    TestDAO testDAO;
    @Test
    public void testJade(){
        testDAO.getAll();
    }
}
