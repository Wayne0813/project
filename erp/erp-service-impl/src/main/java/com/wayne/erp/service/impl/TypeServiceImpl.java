package com.wayne.erp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wayne.erp.entity.Type;
import com.wayne.erp.entity.TypeExample;
import com.wayne.erp.mapper.TypeMapper;
import com.wayne.erp.service.TypeService;
import com.wayne.erp.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author LV
 * @date 2018/7/24
 */
public class TypeServiceImpl implements TypeService {

    private Logger logger = LoggerFactory.getLogger(TypeServiceImpl.class);

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

    /**
     * 查找并分页
     * @param pageNum
     * @param params
     * @return 所有并分页
     */
    @Override
    public PageInfo<Type> findAllAndLimit(Integer pageNum, Map<String, Object> params) {
        PageHelper.startPage(pageNum, Constant.DEFAULT_PAGE_SIZE);

        List<Type> typeList = typeMapper.selectByParams(params);

        PageInfo<Type> pageInfo = new PageInfo<>(typeList);

        return pageInfo;
    }

    /**
     * 根据类型名称查找
     *
     * @param addTypeName
     * @return 对象
     */
    @Override
    public Type findByName(String addTypeName) {
        TypeExample typeExample = new TypeExample();
        typeExample.createCriteria().andTypeNameEqualTo(addTypeName);

        List<Type> typeList = typeMapper.selectByExample(typeExample);
        Type type = null;


        if(typeList.size() != 0){
            type = typeList.get(0);
        }

        return type;
    }

    /**
     * 保存
     *
     * @param type
     */
    @Override
    public void save(Type type) {
        typeMapper.insertSelective(type);
        logger.debug("新增类型 : {}", type);
    }

    /**
     * 更新
     *
     * @param type
     */
    @Override
    public void update(Type type) {
        typeMapper.updateByPrimaryKeySelective(type);
        logger.debug("更新类型 : {}", type);
    }

    /**
     * 根据id查找对象
     *
     * @param id
     * @return
     */
    @Override
    public Type findById(Integer id) {
        return typeMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据id删除对象
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        typeMapper.deleteByPrimaryKey(id);
    }


}
