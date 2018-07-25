package com.wayne.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wayne.entity.Parts;
import com.wayne.mapper.PartsMapper;
import com.wayne.service.PartsService;
import com.wayne.util.Constant;
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
@Service
public class PartsServiceImpl implements PartsService {
    private Logger logger = LoggerFactory.getLogger(PartsServiceImpl.class);

    @Autowired
    private PartsMapper partsMapper;

    /**
     * 根据id查找
     * @param id
     * @return 返回Parts对象
     */
    @Override
    public Parts findById(Integer id){
        return partsMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据Id删除对象
     *
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        partsMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据参数查询：页码，类型，名字
     *
     * @param pageNum
     * @param params
     * @return 对象
     */
    @Override
    public PageInfo<Parts> findByParams(Integer pageNum, Map<String, Object> params) {
        PageHelper.startPage(pageNum, Constant.DEFAULT_PAGE_SIZE);
        List<Parts> partsList = partsMapper.selectByParams(params);

        PageInfo<Parts> pageInfo = new PageInfo<>(partsList);

        return pageInfo;
    }

    /**
     * 新增并保存类型
     *
     * @param parts
     * @return 自动增长的id
     */
    @Override
    public void save(Parts parts) {
        partsMapper.insertSelective(parts);
        logger.debug("新增配件 : {}", parts);
    }

    /**
     * 更新信息
     * @param parts
     */
    @Override
    public void update(Parts parts) {
        partsMapper.updateByPrimaryKeySelective(parts);
        logger.debug("修改配件 : {}", findById(parts.getId()));
    }


}
