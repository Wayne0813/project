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
     * 查找并分页，如果需要按条件查找，只需给params put值即可
     * @param pageNum 页码
     * @param params 参数
     * @return 分页对象
     */
    PageInfo<Employee> findByParams(Integer pageNum, Map<String,Object> params);

    /**
     * 通过手机号查找对象
     * @param userTel 手机号
     * @return 对象
     */
    Employee findByEmployeeTel(String userTel);
}
