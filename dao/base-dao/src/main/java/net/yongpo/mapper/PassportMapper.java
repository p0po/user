package net.yongpo.mapper;

import java.util.List;
import net.yongpo.model.Passport;
import net.yongpo.model.PassportCondition;
import org.apache.ibatis.annotations.Param;

public interface PassportMapper {
    int countByCondition(PassportCondition example);

    int deleteByCondition(PassportCondition example);

    int deleteById(Integer id);

    int insert(Passport record);

    int insertSelective(Passport record);

    List<Passport> selectByCondition(PassportCondition example);

    Passport selectById(Integer id);

    int updateByConditionSelective(@Param("record") Passport record, @Param("example") PassportCondition example);

    int updateByCondition(@Param("record") Passport record, @Param("example") PassportCondition example);

    int updateByIdSelective(Passport record);

    int updateById(Passport record);
}