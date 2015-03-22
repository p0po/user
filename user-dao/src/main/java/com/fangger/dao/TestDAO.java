package com.fangger.dao;

import com.fangger.model.XiaoQu;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import java.util.List;

/**
 * Created by p0po on 15-3-15.
 */
@DAO
public interface TestDAO {
    @SQL("select * from xiaoqu ")
    public List<XiaoQu> getAll();
}