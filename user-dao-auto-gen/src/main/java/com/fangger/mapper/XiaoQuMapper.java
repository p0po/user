package com.fangger.mapper;

import com.fangger.model.XiaoQu;
import com.fangger.model.XiaoQuCondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface XiaoQuMapper {
    int countByCondition(XiaoQuCondition example);

    int deleteByCondition(XiaoQuCondition example);

    int deleteById(Integer id);

    int insert(XiaoQu record);

    int insertSelective(XiaoQu record);

    List<XiaoQu> selectByCondition(XiaoQuCondition example);

    XiaoQu selectById(Integer id);

    int updateByConditionSelective(@Param("record") XiaoQu record, @Param("example") XiaoQuCondition example);

    int updateByCondition(@Param("record") XiaoQu record, @Param("example") XiaoQuCondition example);

    int updateByIdSelective(XiaoQu record);

    int updateById(XiaoQu record);
}