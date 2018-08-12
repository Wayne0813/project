package com.wayne.erp.mapper;

import com.wayne.erp.entity.FixParts;
import com.wayne.erp.entity.FixPartsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FixPartsMapper {
    long countByExample(FixPartsExample example);

    int deleteByExample(FixPartsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FixParts record);

    int insertSelective(FixParts record);

    List<FixParts> selectByExample(FixPartsExample example);

    FixParts selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FixParts record, @Param("example") FixPartsExample example);

    int updateByExample(@Param("record") FixParts record, @Param("example") FixPartsExample example);

    int updateByPrimaryKeySelective(FixParts record);

    int updateByPrimaryKey(FixParts record);
}