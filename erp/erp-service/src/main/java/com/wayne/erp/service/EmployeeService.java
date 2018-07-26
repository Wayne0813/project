package com.wayne.erp.service;

import com.wayne.erp.entity.Employee;
import com.wayne.erp.exception.PermissionsException;

/**
 * @author LV
 * @date 2018/7/26
 */
public interface EmployeeService {

    /**
     * 登录验证
     * @param userTel 登录手机号
     * @param password 密码
     * @return 对象
     * @throws PermissionsException 账号密码错误
     */
    Employee findByUserTel(String userTel, String password) throws PermissionsException;
}
