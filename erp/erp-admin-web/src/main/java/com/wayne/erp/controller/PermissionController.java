package com.wayne.erp.controller;

import com.wayne.erp.entity.Permission;
import com.wayne.erp.exception.PermissionsException;
import com.wayne.erp.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @author LV
 * @date 2018/7/27
 */
@Controller
@RequestMapping("/manage/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    public String permissionListPage(Model model){
        List<Permission> permissionList = permissionService.findAllPermission();
        model.addAttribute("permissionList", permissionList);
        return "manage/permission/list";
    }

    @GetMapping("/add")
    public String permissionAddPage(Model model){
        List<Permission> permissionListAndTypeIsMenu = permissionService.findPermissionByType(Permission.PERMISSION_TYPE_MENU);
        model.addAttribute("permissionListAndTypeIsMenu", permissionListAndTypeIsMenu);
        return "manage/permission/add";
    }

    @PostMapping("/add")
    public String permissionAdd(Permission permission, RedirectAttributes attributes){
        permissionService.save(permission);
        attributes.addFlashAttribute("message", "新增成功!");
        return "redirect:/manage/permission";
    }

    @GetMapping("/edit/{id:\\d+}")
    public String permissionEditPage(@PathVariable Integer id, Model model){

        List<Permission> permissionListAndTypeIsMenu = permissionService.findPermissionByType(Permission.PERMISSION_TYPE_MENU);
        model.addAttribute("permissionListAndTypeIsMenu", permissionListAndTypeIsMenu);

        Permission permission = permissionService.findPermissionById(id);

        model.addAttribute("permission", permission);
        return "manage/permission/edit";
    }

    @PostMapping("/edit")
    public String permissionEdit(Permission permission, RedirectAttributes attributes){
        permissionService.edit(permission);
        attributes.addFlashAttribute("message", "修改成功!");
        return "redirect:/manage/permission";
    }

    @GetMapping("/delete/{id:\\d+}")
    public String permissionDelete(@PathVariable Integer id, RedirectAttributes attributes){
        try {
            permissionService.delete(id);
            attributes.addFlashAttribute("message", "删除成功");
        } catch (PermissionsException e) {
            attributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/manage/permission";
    }

}
