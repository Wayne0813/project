package com.wayne.erp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wayne.erp.entity.Parts;
import com.wayne.erp.entity.PartsExample;
import com.wayne.erp.mapper.PartsMapper;
import com.wayne.erp.service.PartsService;
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

    /**
     * 根据配配件编码查找对象
     *
     * @param partsNo
     * @return 对象
     */
    @Override
    public Parts findByPartsNo(String partsNo) {
        PartsExample partsExample = new PartsExample();
        partsExample.createCriteria().andPartsNoEqualTo(partsNo);
        List<Parts> partsList = partsMapper.selectByExample(partsExample);
        Parts parts = null;
        if(partsList.size() != 0){
            parts = partsList.get(0);
        }

        return parts;
    }

    /**
     * 根据typeId查找对象
     *
     * @param id
     * @return 集合
     */
    @Override
    public List<Parts> findByTypeId(Integer id) {
        PartsExample partsExample = new PartsExample();
        partsExample.createCriteria().andTypeIdEqualTo(id);
        return partsMapper.selectByExample(partsExample);
    }


}
