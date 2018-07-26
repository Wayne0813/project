package com.wayne.erp.mapper;

import com.wayne.erp.entity.Parts;
import com.wayne.erp.entity.PartsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PartsMapper {
    long countByExample(PartsExample example);

    int deleteByExample(PartsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Parts record);

    int insertSelective(Parts record);

    List<Parts> selectByExample(PartsExample example);

    List<Parts> selectPageWithType();

    List<Parts> selectByParams(Map<String, Object> params);

    Parts selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Parts record, @Param("example") PartsExample example);

    int updateByExample(@Param("record") Parts record, @Param("example") PartsExample example);

    int updateByPrimaryKeySelective(Parts record);

    int updateByPrimaryKey(Parts record);
}