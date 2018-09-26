package com.wayne.tms.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wayne.tms.dto.ResponseBean;
import com.wayne.tms.entity.Account;
import com.wayne.tms.entity.Permission;
import com.wayne.tms.exception.ServiceException;
import com.wayne.tms.service.RolePermissionService;
import com.wayne.tms.shiro.CustomerFilterChainDefinition;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @author LV
 * @date 2018/8/31
 */
@Controller
@RequestMapping("/manage/permission")
public class PermissionController {

    @Reference(version = "1.0")
    private RolePermissionService rolePermissionService;

    @Autowired
    private CustomerFilterChainDefinition customerFilterChainDefinition;

    @GetMapping
    public String permissionListPage(Model model) {
        List<Permission> permissionList = rolePermissionService.findAllPermission();
        model.addAttribute("permissionList", permissionList);
        return "manage/permission/home";
    }

    @GetMapping("/new")
    public String permissionNewPage(Model model) {
        List<Permission> permissionList = rolePermissionService.findPermissionByType(Permission.MENU_TYPE);
        model.addAttribute("permissionList", permissionList);
        return "manage/permission/new";
    }

    @PostMapping("/new")
    public String permissionNew(Permission permission, RedirectAttributes attributes) {
        rolePermissionService.saveNewPermission(permission, getAccountId());
        customerFilterChainDefinition.updateUrlPermission();
        attributes.addFlashAttribute("message", "新增成功!");
        return "redirect:/manage/permission";
    }

    @GetMapping("/del/{id:\\d+}")
    @ResponseBody
    public ResponseBean permissionDel(@PathVariable Integer id) {
        try {
            rolePermissionService.removePermissionById(id, getAccountId());
            customerFilterChainDefinition.updateUrlPermission();
            return ResponseBean.success();
        } catch (ServiceException e) {
            e.printStackTrace();
            return ResponseBean.error(e.getMessage());
        }
    }

    @GetMapping("/edit/{id:\\d+}")
    public String permissionEditPage(@PathVariable Integer id, Model model, RedirectAttributes attributes) {
        try {
            Permission permission = rolePermissionService.findPermissionById(id);
            List<Permission> permissionList = rolePermissionService.findPermissionAndExCurr(id);
            model.addAttribute("permission", permission);
            model.addAttribute("permissionList", permissionList);

        } catch (ServiceException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/manage/permission";
        }
        return "manage/permission/edit";
    }

    @PostMapping("/edit")
    public String permissionEdit(Permission permission, RedirectAttributes attributes) {
        try {
            rolePermissionService.editPermission(permission, getAccountId());
            customerFilterChainDefinition.updateUrlPermission();
            attributes.addFlashAttribute("message", "权限更新成功!");
        } catch (ServiceException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/manage/permission";
    }



    /**
     * 获得当前登录用户的id
     * @return 当前登录用户did
     */
    private Integer getAccountId() {
        Subject subject = SecurityUtils.getSubject();
        Account account = (Account) subject.getPrincipal();
        return account.getId();
    }


}
