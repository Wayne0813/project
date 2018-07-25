package com.wayne.service;

import com.wayne.entity.Type;

import java.util.List;

/**
 * @author LV
 * @date 2018/7/24
 */
public interface TypeService {

    /** 查找所有类型
     * @return 所有类型
     */
    List<Type> findAll();
}
