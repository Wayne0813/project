package com.wayne.service.impl;

import com.wayne.entity.Type;
import com.wayne.entity.TypeExample;
import com.wayne.mapper.TypeMapper;
import com.wayne.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LV
 * @date 2018/7/24
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    /**
     * 查找所有类型
     *
     * @return 所有类型
     */
    @Override
    public List<Type> findAll() {
        TypeExample typeExample = new TypeExample();
        return typeMapper.selectByExample(typeExample);
    }
}
