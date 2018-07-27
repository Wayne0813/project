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

    private Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

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
        logger.debug("{}--{}, 登录系统,时间{}", employee.getEmployeeName(), employee.getEmployeeTel(), new Date());
        return employee;
    }

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
}
