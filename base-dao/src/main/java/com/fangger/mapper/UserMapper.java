package com.fangger.mapper;

import com.fangger.model.User;
import com.fangger.model.UserCondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int countByCondition(UserCondition example);

    int deleteByCondition(UserCondition example);

    int deleteById(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByCondition(UserCondition example);

    User selectById(Integer id);

    int updateByConditionSelective(@Param("record") User record, @Param("example") UserCondition example);

    int updateByCondition(@Param("record") User record, @Param("example") UserCondition example);

    int updateByIdSelective(User record);

    int updateById(User record);
}