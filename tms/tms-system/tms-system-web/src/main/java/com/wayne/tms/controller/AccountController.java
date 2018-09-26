package com.wayne.tms.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wayne.tms.dto.ResponseBean;
import com.wayne.tms.entity.Account;
import com.wayne.tms.entity.Roles;
import com.wayne.tms.exception.ServiceException;
import com.wayne.tms.service.AccountService;
import com.wayne.tms.service.RolePermissionService;
import org.apache.commons.codec.digest.DigestUtils;
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

/**
 * @author LV
 * @date 2018/9/1
 */
@Controller
@RequestMapping("/manage/account")
public class AccountController {

    @Reference(version = "1.0")
    private AccountService accountService;

    @Reference(version = "1.0")
    private RolePermissionService rolePermissionService;

    @GetMapping
    public String accountList(Model model) {
        List<Account> accountList = accountService.findAccountAll();
        model.addAttribute("accountList", accountList);
        return "manage/account/home";
    }

    @GetMapping("/new")
    public String accountAddPage(Model model) {
        List<Roles> rolesList = rolePermissionService.findAllRole();
        model.addAttribute("rolesList", rolesList);
        return "manage/account/new";
    }

    @PostMapping("/new")
    public String accountAdd(Account account, Integer[] rolesIds, RedirectAttributes attributes) {
        account.setAccountPassword(DigestUtils.md5Hex(account.getAccountPassword()));
        account.setAccountState(Account.STATE_NORMAL);
        accountService.saveAccount(account, rolesIds, getAccountId());
        attributes.addFlashAttribute("message", "用户添加成功");
        return "redirect:/manage/account";
    }

    @GetMapping("/del/{id:\\d+}")
    public String accountDel(@PathVariable Integer id, RedirectAttributes attributes) {
        try {
            accountService.removeAccountById(id, getAccountId());
            attributes.addFlashAttribute("message", "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/manage/account";
    }

    @GetMapping("/edit/{id:\\d+}")
    public String accountEditPage(@PathVariable Integer id, Model model) {
        try {
            Account account = accountService.findAccountById(id);
            List<Roles> rolesList = rolePermissionService.findAllRole();
            model.addAttribute("account", account);
            model.addAttribute("rolesList", checkRolesListIsChecked(rolesList, account.getRolesList()));
            return "manage/account/edit";
        } catch (ServiceException e) {
            e.printStackTrace();
            model.addAttribute("message", e.getMessage());
            return "redirect:/manage/account";
        }
    }

    @PostMapping("/edit")
    public String accountEdit(Account account, Integer[] rolesIds, RedirectAttributes attributes) {
        try {
            accountService.editAccount(account, rolesIds, getAccountId());
            attributes.addFlashAttribute("message", "修改成功!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/manage/account";
    }

    private Map<Roles,Boolean> checkRolesListIsChecked(List<Roles> rolesList, List<Roles> accountRolesList) {
        Map<Roles,Boolean> map = new LinkedHashMap<>();
        for(Roles roles : rolesList) {
            boolean flag = false;
            for (Roles accountRoles : accountRolesList) {
                if(accountRoles.getId().equals(roles.getId())) {
                    flag = true;
                }
            }
            map.put(roles,flag);
        }
        return map;
    }

    private Integer getAccountId() {
        Subject subject = SecurityUtils.getSubject();
        Account account = (Account) subject.getPrincipal();
        return account.getId();
    }

}
