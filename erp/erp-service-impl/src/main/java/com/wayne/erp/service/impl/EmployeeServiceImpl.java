package com.wayne.erp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wayne.erp.entity.Employee;
import com.wayne.erp.entity.EmployeeExample;
import com.wayne.erp.entity.Parts;
import com.wayne.erp.exception.PermissionsException;
import com.wayne.erp.mapper.EmployeeMapper;
import com.wayne.erp.service.EmployeeService;
import com.wayne.erp.util.Constant;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author LV
 * @date 2018/7/26
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 查找并分页，如果需要按条件查找，只需给params put值即可
     *
     * @param pageNum 页码
     * @param params  参数
     * @return 分页对象
     */
    @Override
    public PageInfo<Employee> findByParams(Integer pageNum, Map<String, Object> params) {
        PageHelper.startPage(pageNum, Constant.DEFAULT_PAGE_SIZE);

        List<Employee> employeeList = employeeMapper.selectByParams(params);

        PageInfo<Employee> page = new PageInfo<>(employeeList);

        return page;
    }

    /**
     * 通过手机号查找对象
     *
     * @param userTel 手机号
     * @return 对象
     */
    @Override
    public Employee findByEmployeeTel(String userTel) {
        EmployeeExample employeeExample = new EmployeeExample();
        employeeExample.createCriteria().andEmployeeTelEqualTo(userTel);

        List<Employee> employeeList = employeeMapper.selectByExample(employeeExample);
        Employee employee = null;
        if(employeeList != null && employeeList.size() > 0){
            employee = employeeList.get(0);

        }
        return employee;
    }
}
