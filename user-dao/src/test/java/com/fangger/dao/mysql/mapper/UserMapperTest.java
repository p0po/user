package com.fangger.dao.mysql.mapper;

import com.fangger.dao.mysql.model.User;
import com.fangger.dao.mysql.model.UserExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-dataSource.xml")
public class UserMapperTest {
    @Autowired
    UserMapper userMapper;

    @Test
	public void testCountByExample() {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIsNotNull();
        int size = userMapper.countByExample(userExample);
		assertTrue(size>0);
		//fail("Not yet implemented");
	}

	@Test
	public void testDeleteByExample() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteByPrimaryKey() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsert() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertSelective() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectByExample() {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIsNotNull();
        List<User> userList =userMapper.selectByExample(userExample);
        assertTrue(userList.size() > 0);
        //fail("Not yet implemented");
	}

	@Test
	public void testSelectByPrimaryKey() {
        UserExample userExample = new UserExample();
        //userExample.createCriteria().andAccountIsNotNull();
        User user =userMapper.selectByPrimaryKey(1);
        assertNotNull(user);
	}

	@Test
	public void testUpdateByExampleSelective() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateByExample() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateByPrimaryKeySelective() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateByPrimaryKey() {
		fail("Not yet implemented");
	}

}
