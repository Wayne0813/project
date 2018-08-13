package com.wayne.erp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wayne.erp.entity.*;
import com.wayne.erp.exception.PermissionsException;
import com.wayne.erp.mapper.EmployeeMapper;
import com.wayne.erp.mapper.EmployeeRoleMapper;
import com.wayne.erp.mapper.RoleMapper;
import com.wayne.erp.service.EmployeeService;
import com.wayne.erp.service.PermissionService;
import com.wayne.erp.util.Constant;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author LV
 * @date 2018/7/26
 */
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeRoleMapper employeeRoleMapper;

    /**
     * 查找并分页，如果需要按条件查找，只需给params put值即可
     *
     * @param pageNum 页码
     * @param params  参数
     * @return 分页对象
     */
    @Override
    public List<Employee> findByParams(Integer pageNum, Map<String, Object> params) {
        return employeeMapper.selectByParams(params);
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

    /**
     * 保存员工
     *
     * @param employee 员工对象
     * @param roleIds  员工角色id
     */
    @Override
    public void saveEmployee(Employee employee, Integer[] roleIds) {
        employeeMapper.insertSelective(employee);
        if (roleIds != null) {
            saveEmployeeRole(employee, roleIds);
        }
    }

    /**
     * 通过id查找员工信息
     *
     * @param id 需要查找的id
     * @return 员工对象
     */
    @Override
    public Employee findById(Integer id) {
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        EmployeeRoleExample employeeRoleExample = new EmployeeRoleExample();
        employeeRoleExample.createCriteria().andEmployeeIdEqualTo(id);

        List<EmployeeRole> employeeRoleList = employeeRoleMapper.selectByExample(employeeRoleExample);

        List<Role> roleList = new ArrayList<>();

        for (EmployeeRole employeeRole : employeeRoleList) {
            Role role = new Role();
            role.setId(employeeRole.getRoleId());
            roleList.add(role);
        }

        employee.setRoleList(roleList);

        return employee;
    }

    /**
     * 修改员工信息
     *
     * @param employee 员工对象
     * @param roleIds  角色id
     */
    @Override
    public void editEmployee(Employee employee, Integer[] roleIds) {
        employeeMapper.updateByPrimaryKeySelective(employee);

        EmployeeRoleExample employeeRoleExample = new EmployeeRoleExample();
        employeeRoleExample.createCriteria().andEmployeeIdEqualTo(employee.getId());

        employeeRoleMapper.deleteByExample(employeeRoleExample);

        if(roleIds != null){
            saveEmployeeRole(employee, roleIds);
        }

    }

    /**
     * 根据id修改用户状态
     *
     * @param id 需要修改的id
     */
    @Override
    public void editEmployeeStateById(Integer id) {
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        Integer state = employee.getState();
        state = 1 - state;
        employee.setState(state);
        employeeMapper.updateByPrimaryKeySelective(employee);
    }

    /**
     * 根据id删除员工对象
     *
     * @param id id
     */
    @Override
    public void deleteEmployee(Integer id) {
        employeeMapper.deleteByPrimaryKey(id);
        EmployeeRoleExample employeeRoleExample = new EmployeeRoleExample();
        employeeRoleExample.createCriteria().andEmployeeIdEqualTo(id);
        employeeRoleMapper.deleteByExample(employeeRoleExample);
    }

    private void saveEmployeeRole(Employee employee, Integer[] roleIds){
        for (Integer roleId : roleIds) {
            EmployeeRole employeeRole = new EmployeeRole();
            employeeRole.setRoleId(roleId);
            employeeRole.setEmployeeId(employee.getId());

            employeeRoleMapper.insertSelective(employeeRole);
        }
    }

}
