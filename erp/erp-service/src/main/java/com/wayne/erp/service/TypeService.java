package com.wayne.erp.service;

import com.github.pagehelper.PageInfo;
import com.wayne.erp.entity.Type;

import java.util.List;
import java.util.Map;

/**
 * @author LV
 * @date 2018/7/24
 */
public interface TypeService {

    /** 查找所有类型
     * @return 所有类型
     */
    List<Type> findAll();


    /**
     * 查找并分页
     * @param pageNum
     * @param params
     * @return 所有并分页
     */
    PageInfo<Type> findAllAndLimit(Integer pageNum, Map<String, Object> params);

    /**
     *  根据类型名称查找
     * @param addTypeName
     * @return 对象
     */
    Type findByName(String addTypeName);


    /**
     * 保存
     * @param type
     */
    void save(Type type);

    /**
     * 更新
     * @param type
     */
    void update(Type type);

    /**
     * 根据id查找对象
     * @param id
     * @return
     */
    Type findById(Integer id);


    /**
     * 根据id删除对象
     * @param id
     */
    void delete(Integer id);
}
