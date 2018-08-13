package com.wayne.erp.service;

import com.wayne.erp.entity.Employee;

import java.util.List;
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
    List<Employee> findByParams(Integer pageNum, Map<String,Object> params);

    /**
     * 通过手机号查找对象
     * @param userTel 手机号
     * @return 对象
     */
    Employee findByEmployeeTel(String userTel);

    /**
     * 保存员工
     * @param employee 员工对象
     * @param roleIds 员工角色id
     */
    void saveEmployee(Employee employee, Integer[] roleIds);

    /**
     * 通过id查找员工信息
     * @param id 需要查找的id
     * @return 员工对象
     */
    Employee findById(Integer id);

    /**
     * 修改员工信息
     * @param employee 员工对象
     * @param roleIds 角色id
     */
    void editEmployee(Employee employee, Integer[] roleIds);

    /**
     * 根据id修改用户状态
     * @param id 需要修改的id
     */
    void editEmployeeStateById(Integer id);

    /**
     * 根据id删除员工对象
     * @param id id
     */
    void deleteEmployee(Integer id);
}
