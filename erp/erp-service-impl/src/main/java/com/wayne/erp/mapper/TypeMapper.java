package com.wayne.erp.mapper;

import com.wayne.erp.entity.Type;
import com.wayne.erp.entity.TypeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TypeMapper {
    long countByExample(TypeExample example);

    int deleteByExample(TypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Type record);

    int insertSelective(Type record);

    List<Type> selectByExample(TypeExample example);

    List<Type> selectByParams(Map<String, Object> params);

    Type selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Type record, @Param("example") TypeExample example);

    int updateByExample(@Param("record") Type record, @Param("example") TypeExample example);

    int updateByPrimaryKeySelective(Type record);

    int updateByPrimaryKey(Type record);
}