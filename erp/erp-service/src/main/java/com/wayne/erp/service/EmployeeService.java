package com.wayne.erp.service;

import com.github.pagehelper.PageInfo;
import com.wayne.erp.entity.Employee;
import com.wayne.erp.entity.Parts;
import com.wayne.erp.exception.PermissionsException;

import java.util.Map;

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


    /**
     * 查找并分页，如果需要按条件查找，只需给params put值即可
     * @param pageNum 页码
     * @param params 参数
     * @return 分页对象
     */
    PageInfo<Employee> findByParams(Integer pageNum, Map<String,Object> params);
}
