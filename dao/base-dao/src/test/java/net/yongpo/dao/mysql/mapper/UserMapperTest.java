package net.yongpo.dao.mysql.mapper;

import net.yongpo.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by popo on 2014/10/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-dataSource.xml"})
public class UserMapperTest {
    @Autowired
    UserMapper userMapper;

    @Test
    public void testCondition(){
        userMapper.selectById(16);
    }
}
