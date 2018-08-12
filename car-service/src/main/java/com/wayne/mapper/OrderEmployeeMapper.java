package com.wayne.mapper;

import com.wayne.entity.OrderEmployee;
import com.wayne.entity.OrderEmployeeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderEmployeeMapper {
    long countByExample(OrderEmployeeExample example);

    int deleteByExample(OrderEmployeeExample example);

    int insert(OrderEmployee record);

    int insertSelective(OrderEmployee record);

    List<OrderEmployee> selectByExample(OrderEmployeeExample example);

    int updateByExampleSelective(@Param("record") OrderEmployee record, @Param("example") OrderEmployeeExample example);

    int updateByExample(@Param("record") OrderEmployee record, @Param("example") OrderEmployeeExample example);
}