package TestDao;

import net.yongpo.dao.TestDAO;
import net.yongpo.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

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
        List<User> userList = testDAO.getAll();
        System.out.println(userList.size());
    }
}
