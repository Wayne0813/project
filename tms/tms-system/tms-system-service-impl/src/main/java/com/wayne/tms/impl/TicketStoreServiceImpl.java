package com.wayne.tms.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wayne.tms.entity.TicketStore;
import com.wayne.tms.entity.TicketStoreExample;
import com.wayne.tms.mapper.TicketStoreMapper;
import com.wayne.tms.service.TicketStoreService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author LV
 * @date 2018/9/2
 */
@Service(version = "1.0", timeout = 5000)
public class TicketStoreServiceImpl implements TicketStoreService {

    @Autowired
    private TicketStoreMapper ticketStoreMapper;

    /**
     * 查找所有售票点信息并分页
     * @param pageNo 页码
     * @param params 查询条件
     * @return 结果集
     */
    @Override
    public PageInfo<TicketStore> findAllTicketStoreByParams(Integer pageNo, Map<String, Object> params) {
        PageHelper.startPage(pageNo, 15);

        String storeName = (String)params.get("storeName");
        String storeManager = (String) params.get("storeManager");
        String storeTel = (String) params.get("storeTel");

        TicketStoreExample ticketStoreExample = new TicketStoreExample();
        TicketStoreExample.Criteria criteria = ticketStoreExample.createCriteria();
        if(StringUtils.isNotEmpty(storeName)) {
            criteria.andStoreNameLike("%"+storeName+"%");
        }
        if(StringUtils.isNotEmpty(storeManager)) {
            criteria.andStoreManagerLike("%"+storeManager+"%");
        }
        if(StringUtils.isNotEmpty(storeTel)) {
            criteria.andStoreTelEqualTo(storeTel);
        }
        ticketStoreExample.setOrderByClause("id desc");

        List<TicketStore> ticketStoreList = ticketStoreMapper.selectByExample(ticketStoreExample);
        return new PageInfo<>(ticketStoreList);
    }
}
