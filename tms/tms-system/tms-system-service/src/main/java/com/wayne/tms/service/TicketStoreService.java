package com.wayne.tms.service;

import com.github.pagehelper.PageInfo;
import com.wayne.tms.entity.TicketStore;

import java.util.Map;

/**
 * @author LV
 * @date 2018/9/2
 */
public interface TicketStoreService {

    /**
     * 查找所有售票点信息并分页
     * @param pageNo 页码
     * @param params 查询条件
     * @return 结果集
     */
    PageInfo<TicketStore> findAllTicketStoreByParams(Integer pageNo, Map<String,Object> params);
}
