package com.wayne.erp.controller;

import com.wayne.erp.controller.exceptionhandler.NotFoundException;
import com.wayne.erp.entity.Permission;
import com.wayne.erp.entity.Role;
import com.wayne.erp.exception.PermissionsException;
import com.wayne.erp.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("/manage/role")
public class RoleController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    public String listPage(Model model){
        List<Role> roleList = permissionService.findAllRole();
        model.addAttribute("roleList", roleList);
        return "manage/role/list";
    }

    @GetMapping("/add")
    public String addPage(Model model){
        List<Permission> permissionList = permissionService.findAllPermission();
        model.addAttribute("permissionList", permissionList);
        return "manage/role/add";
    }

    @PostMapping("/add")
    public String add(Role role, Integer[] permissionId, RedirectAttributes attributes){
        permissionService.saveRole(role, permissionId);
        attributes.addFlashAttribute("message", "新增成功!");
        return "redirect:/manage/role";
    }

    @GetMapping("/edit/{id:\\d+}")
    public String editPage(@PathVariable Integer id, Model model){
        List<Permission> permissionList = permissionService.findAllPermission();
        Role role = permissionService.findRoleById(id);
        for (Permission permission : permissionList) {
            for (Permission perm : role.getPermissionList()) {
                if(permission.getId().equals(perm.getId())) {
                    permission.setIsChecked("checked");
                }
            }
        }
        model.addAttribute("permissionList", permissionList);
        model.addAttribute("role", role);
        return "manage/role/edit";
    }

    @PostMapping("/edit")
    public String edit(Role role, Integer[] permissionId, RedirectAttributes attributes){
        permissionService.editRole(role, permissionId);
        attributes.addFlashAttribute("message", "修改成功!");
        return "redirect:/manage/role";
    }

    @GetMapping("/delete/{id:\\d+}")
    public String delete(@PathVariable Integer id,  RedirectAttributes attributes){
        if(permissionService.findRoleById(id) == null){
            throw new NotFoundException();
        }

        try {
            permissionService.deleteRole(id);
            attributes.addFlashAttribute("message", "删除成功!");
        } catch (PermissionsException e) {
            attributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/manage/role";
    }


}
