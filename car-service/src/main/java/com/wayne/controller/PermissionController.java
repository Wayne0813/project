package com.wayne.controller;

import com.wayne.entity.Permission;
import com.wayne.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

/**
 * @author LV
 * @date 2018/8/2
 */
@Controller
@RequestMapping("/manage/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    public String listPage(Model model){

        List<Permission> permissionList = permissionService.findAllPermission();
        model.addAttribute("permissionList", permissionList);

        return "manage/permission/list";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        List<Permission> permissionList = permissionService.findAllMenuPermission(Permission.PERMISSION_TYPE_MENU);
        model.addAttribute("permissionList", permissionList);
        return "manage/permission/add";
    }

    @PostMapping("/add")
    public String add(Permission permission, RedirectAttributes attributes){
        permissionService.savePermission(permission);
        attributes.addFlashAttribute("massage", "新增成功!");
        return "redirect:/manage/permission";
    }


    @GetMapping("/edit/{id:\\d+}")
    public String editPage(@PathVariable Integer id, Model model){
        List<Permission> permissionList = permissionService.findAllMenuPermission(Permission.PERMISSION_TYPE_MENU);
        // Permission permission = permissionService.findPermissionById(id);
        Permission permission =  permissionService.findPermissionById(id, permissionList);
        model.addAttribute("permissionList", permissionList);
        model.addAttribute("permission", permission);
        return "manage/permission/edit";
    }


}
