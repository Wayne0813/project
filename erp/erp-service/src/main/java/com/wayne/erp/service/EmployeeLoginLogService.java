package com.wayne.erp.service;

/**
 * @author LV
 * @date 2018/7/26
 */
public interface EmployeeLoginLogService {

    /**
     * 记录用户使用信息
     * @param ip 用户ip
     * @param id 用户id
     */
    void save(String ip, Integer id);
}
