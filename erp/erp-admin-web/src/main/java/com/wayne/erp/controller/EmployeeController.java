package com.wayne.erp.controller;

import com.wayne.erp.entity.Employee;
import com.wayne.erp.entity.Role;
import com.wayne.erp.service.EmployeeService;
import com.wayne.erp.service.PermissionService;
import com.wayne.erp.util.Constant;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LV
 * @date 2018/7/26
 */
@Controller
@RequestMapping("/manage/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    public String listPage(@RequestParam(name = "p", defaultValue = "1", required = false) Integer pageNum,
                           String nameMobile,
                           Integer roleId,
                           Model model){

        Map<String, Object> params = new HashMap<>();
        params.put("nameMobile", nameMobile);
        params.put("roleId", roleId);

        List<Employee> employeeList = employeeService.findByParams(pageNum, params);

        List<Role> roleList = permissionService.findAllRole();

        model.addAttribute("employeeList", employeeList);
        model.addAttribute("roleList", roleList);
        return "manage/employee/list";
    }

    @GetMapping("/add")
    public String addPage(Model model){
        List<Role> roleList = permissionService.findAllRole();
        model.addAttribute("roleList", roleList);
        return "manage/employee/add";
    }

    @PostMapping("/add")
    public String add(Employee employee, Integer[] roleIds, RedirectAttributes attributes){
        employee.setPassword(DigestUtils.md5Hex(employee.getPassword() + Constant.PASSWORD_SALT));
        employeeService.saveEmployee(employee, roleIds);
        attributes.addFlashAttribute(Constant.SEND_PAGE_MASSAGE_KEY, Constant.SEND_PAGE_MASSAGE_ADD_SECCESS);
        return "redirect:/manage/employee";
    }

    @GetMapping("/edit/{id:\\d+}")
    public String editPage(@PathVariable Integer id, Model model){
        List<Role> roleList = permissionService.findAllRole();
        Employee employee = employeeService.findById(id);

        model.addAttribute("roleList", roleList);
        model.addAttribute("employee", employee);

        return "manage/employee/edit";
    }

    @PostMapping("/edit")
    public String edit(Employee employee, Integer[] roleIds, RedirectAttributes attributes){
        employeeService.editEmployee(employee, roleIds);
        attributes.addFlashAttribute(Constant.SEND_PAGE_MASSAGE_KEY, Constant.SEND_PAGE_MASSAGE_EDIT_SECCESS);
        return "redirect:/manage/employee";
    }

    @GetMapping("/state/{id:\\d+}")
    public String state(@PathVariable Integer id, RedirectAttributes attributes){
        employeeService.editEmployeeStateById(id);
        attributes.addFlashAttribute(Constant.SEND_PAGE_MASSAGE_KEY, Constant.SEND_PAGE_MASSAGE_EDIT_SECCESS);
        return "redirect:/manage/employee";
    }

    @GetMapping("/delete/{id:\\d+}")
    public String delete(@PathVariable Integer id, RedirectAttributes attributes){
        employeeService.deleteEmployee(id);
        attributes.addFlashAttribute(Constant.SEND_PAGE_MASSAGE_KEY, Constant.SEND_PAGE_MASSAGE_DELETE_SECCESS);
        return "redirect:/manage/employee";
    }

}
