package com.wayne.tms.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import com.wayne.tms.dto.ResponseBean;
import com.wayne.tms.entity.Account;
import com.wayne.tms.entity.Permission;
import com.wayne.tms.entity.Roles;
import com.wayne.tms.entity.RolesPermissionKey;
import com.wayne.tms.exception.ServiceException;
import com.wayne.tms.service.RolePermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author LV
 * @date 2018/8/31
 */
@Controller
@RequestMapping("/manage/roles")
public class RoleController {

    @Reference(version = "1.0")
    private RolePermissionService rolePermissionService;

    @GetMapping
    public String roleListPage(Model model) {
        List<Roles> rolesList = rolePermissionService.findAllRole();
        model.addAttribute("rolesList", rolesList);
        return "/manage/roles/home";
    }

    @GetMapping("/new")
    public String rolesAddPage(Model model){
        List<Permission> permissionList = rolePermissionService.findAllPermission();
        model.addAttribute("permissionList", permissionList);
        return "/manage/roles/new";
    }

    @PostMapping("/new")
    public String rolesAdd(Roles roles, Integer[] permissionId) {
        rolePermissionService.saveNewRoles(roles, permissionId, getAccountId());
        return "redirect:/manage/roles";
    }

    @GetMapping("/del/{id:\\d+}")
    public String rolesDel(@PathVariable Integer id, RedirectAttributes attributes) {
        try {
            rolePermissionService.removeRolesById(id, getAccountId());
            attributes.addFlashAttribute("message", "删除成功!");
        } catch (ServiceException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/manage/roles";
    }

    @GetMapping("/edit/{id:\\d+}")
    public String rolesEditPage(@PathVariable Integer id, Model model, RedirectAttributes attributes) {
        try {
            List<Permission> permissionList = rolePermissionService.findAllPermission();
            Roles roles = rolePermissionService.findRolesById(id);
            List<RolesPermissionKey> rolesPermissionKeyList = rolePermissionService.findRolesPermissionKeyByRolesId(id);
            Map<Permission, Boolean> permissionMap = isChecked(permissionList, rolesPermissionKeyList);
            model.addAttribute("permissionMap", permissionMap);
            model.addAttribute("roles", roles);
            return "/manage/roles/edit";
        } catch (ServiceException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/manage/roles";
        }
    }

    private Map<Permission,Boolean> isChecked(List<Permission> permissionList, List<RolesPermissionKey> rolesPermissionKeyList) {
        Map<Permission,Boolean> resultMap = Maps.newLinkedHashMap();
        for(Permission permission : permissionList) {
            boolean flag = false;
            for(RolesPermissionKey rolesPermissionKey : rolesPermissionKeyList) {
                if(permission.getId().equals(rolesPermissionKey.getPermissionId())) {
                    flag = true;
                    break;
                }
            }
            resultMap.put(permission,flag);
        }
        return resultMap;
    }

    @PostMapping("/edit")
    public String rolesEdit(Roles roles, Integer[] permissionId, RedirectAttributes attributes) {
        try {
            rolePermissionService.editRoles(roles, permissionId, getAccountId());
            attributes.addFlashAttribute("message", "更新成功!");
        } catch (ServiceException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("", e.getMessage());
        }
        return "redirect:/manage/roles";
    }


    private Integer getAccountId() {
        Subject subject = SecurityUtils.getSubject();
        Account account = (Account) subject.getPrincipal();
        return account.getId();
    }


}
