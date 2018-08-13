package com.wayne.erp.service.impl;

import com.wayne.erp.entity.EmployeeLoginLog;
import com.wayne.erp.mapper.EmployeeLoginLogMapper;
import com.wayne.erp.service.EmployeeLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LV
 * @date 2018/7/26
 */
public class EmployeeLoginLogServiceImpl implements EmployeeLoginLogService {

    @Autowired
    private EmployeeLoginLogMapper employeeLoginLogMapper;

    /**
     * 记录用户使用信息
     * @param ip 用户ip
     * @param id 用户id
     */
    @Override
    public void save(String ip, Integer id) {
        EmployeeLoginLog employeeLoginLog = new EmployeeLoginLog();
        employeeLoginLog.setLoginIp(ip);
        employeeLoginLog.setEmployeeId(id);
        employeeLoginLogMapper.insertSelective(employeeLoginLog);
    }
}
