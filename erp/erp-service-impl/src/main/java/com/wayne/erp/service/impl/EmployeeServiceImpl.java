package com.wayne.erp.service.impl;

import com.wayne.erp.entity.Employee;
import com.wayne.erp.entity.EmployeeExample;
import com.wayne.erp.exception.PermissionsException;
import com.wayne.erp.mapper.EmployeeMapper;
import com.wayne.erp.service.EmployeeService;
import com.wayne.erp.util.Constant;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LV
 * @date 2018/7/26
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 登录验证
     * @param userTel 登录手机号
     * @param password 密码
     * @return 对象
     * @throws PermissionsException 账号密码错误
     */
    @Override
    public Employee findByUserTel(String userTel, String password) throws PermissionsException {
        password = DigestUtils.md5Hex(password + Constant.PASSWORD_SALT);
        System.out.println("passwprd : " + password);
        EmployeeExample employeeExample = new EmployeeExample();
        employeeExample.createCriteria().andEmployeeTelEqualTo(userTel);
        List<Employee> employeeList = employeeMapper.selectByExample(employeeExample);

        if(employeeList == null){
            throw new PermissionsException("手机号或密码错误!");
        }
        if(employeeList.size() == 0){
            throw new PermissionsException("手机号或密码错误!");
        }
        Employee employee = employeeList.get(0);
        if(!employee.getPassword().equals(password)){
            throw new PermissionsException("手机号或密码错误!");
        }
        return employee;
    }
}
