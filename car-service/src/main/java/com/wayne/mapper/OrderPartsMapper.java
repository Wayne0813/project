package com.wayne.mapper;

import com.wayne.entity.OrderParts;
import com.wayne.entity.OrderPartsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderPartsMapper {
    long countByExample(OrderPartsExample example);

    int deleteByExample(OrderPartsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderParts record);

    int insertSelective(OrderParts record);

    List<OrderParts> selectByExample(OrderPartsExample example);

    OrderParts selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderParts record, @Param("example") OrderPartsExample example);

    int updateByExample(@Param("record") OrderParts record, @Param("example") OrderPartsExample example);

    int updateByPrimaryKeySelective(OrderParts record);

    int updateByPrimaryKey(OrderParts record);
}