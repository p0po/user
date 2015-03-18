package com.fangger.dao.mysql.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by popo on 2014/10/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-dataSource.xml")
public class PermissionMapperTest {
    @Autowired
    PermissionMapper permissionMapper;

    @Test
    public void testInsert(){
        /*

        */
    }

}
