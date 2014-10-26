package com.fangger.dao.mysql.mapper;

import com.fangger.dao.mysql.model.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Created by popo on 2014/10/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-dataSource.xml")
public class AppMapperTest {
    @Autowired
    AppMapper appMapper;

    @Test
    public void testInsert(){
        return;
        /*App app = new App();
        app.setName("test");
        app.setVersion("1.0");
        app.setDescribes("aaaa");
        app.setUrl("http://www.baidu.com/app.apk");
        System.out.println(app);
        int id = appMapper.insertSelective(app);
        assertTrue(id>0);*/
    }

    @Test
    public void testBatchaUpadte(){
        List<Integer> idSet = new ArrayList<Integer>();
        idSet.add(1);
        idSet.add(2);
        idSet.add(3);
        App app = new App();
        app.setStatus((byte)4);
        appMapper.batchUpdateStatusByPrimaryKey(app,idSet);
    }
}
