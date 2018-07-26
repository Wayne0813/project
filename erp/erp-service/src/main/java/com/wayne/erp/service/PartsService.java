package com.wayne.erp.service;

import com.github.pagehelper.PageInfo;
import com.wayne.erp.entity.Parts;

import java.util.List;
import java.util.Map;

/**
 * @author LV
 * @date 2018/7/23
 */
public interface PartsService {

    /**
     * 根据id查找
     * @param id
     * @return 返回Parts对象
     */
    Parts findById(Integer id);

    /**
     * 根据Id删除对象
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据参数查询：页码，类型，名字
     * @param pageNum
     * @param params
     * @return 对象
     */
    PageInfo<Parts> findByParams(Integer pageNum, Map<String, Object> params);

    /**
     * 新增并保存类型
     * @param parts
     * @return 自动增长的id
     */
    void save(Parts parts);


    /**
     * 更新信息
     * @param parts
     */
    void update(Parts parts);

    /**
     * 根据配配件编码查找对象
     * @param partsNo
     * @return 对象
     */
    Parts findByPartsNo(String partsNo);

    /**
     * 根据typeId查找对象
     * @param id
     * @return 集合
     */
    List<Parts> findByTypeId(Integer id);
}
