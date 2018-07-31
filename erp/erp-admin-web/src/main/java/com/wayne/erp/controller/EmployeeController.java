package com.wayne.erp.controller;

import com.github.pagehelper.PageInfo;
import com.wayne.erp.entity.Employee;
import com.wayne.erp.entity.Parts;
import com.wayne.erp.entity.Type;
import com.wayne.erp.service.EmployeeService;
import com.wayne.erp.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
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

    @GetMapping
    public String listPage(@RequestParam(name = "p", defaultValue = "1", required = false) Integer pageNum,
                           Model model){

        Map<String, Object> params = new HashMap<>();

        PageInfo<Employee> page = employeeService.findByParams(pageNum, params);

        model.addAttribute("page", page);
        return "manage/employee/list";
    }

}
