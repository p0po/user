package net.yongpo.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;
import net.yongpo.model.User;

import java.util.List;

/**
 * Created by p0po on 15-3-15.
 */
@DAO
public interface TestDAO {
    @SQL("select * from user limit 10")
    List<User> getAll();

    @SQL("insert into popo.user(user.NICKNAME,user.REAL_NAME,user.PHONE,user.EMAIL,user.GENDER,user.BIRTHDAY,user.SOURCE,user.CREATE_TIME) values (:1.user)")
    @ReturnGeneratedKeys
    int addUser(User user);
}
