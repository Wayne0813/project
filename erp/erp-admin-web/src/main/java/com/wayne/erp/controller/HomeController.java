package com.wayne.erp.controller;

import com.wayne.erp.entity.Employee;
import com.wayne.erp.service.EmployeeLoginLogService;
import com.wayne.erp.util.Constant;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author LV
 * @date 2018/7/26
 */
@Controller
public class HomeController {

    @Autowired
    private EmployeeLoginLogService employeeLoginLogService;

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/home")
    public String homePage(){
        return "home";
    }

    @GetMapping("/")
    public String loginPage(){
        Subject subject = SecurityUtils.getSubject();

        if(subject.isAuthenticated()){
            subject.logout();
        }
        if(subject.isRemembered()){
            return "redirect:/home";
        }
        return "login";
    }

    @PostMapping("/")
    public String login(String userTel,
                        String password,
                        String remember,
                        HttpServletRequest request,
                        RedirectAttributes attributes){

        UsernamePasswordToken token = new UsernamePasswordToken(userTel, DigestUtils.md5Hex(password + Constant.PASSWORD_SALT), remember != null);
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(token);
            Employee employee = (Employee) subject.getPrincipal();
            employeeLoginLogService.save(request.getRemoteAddr(), employee.getId());
            logger.debug("{}--{}, 登录系统,时间{}", employee.getEmployeeName(), employee.getEmployeeTel(), new Date());

            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            String url = "/home";
            if(savedRequest != null){
                url = savedRequest.getRequestUrl();
            }
            return "redirect:" + url;
        } catch (UnknownAccountException | IncorrectCredentialsException e) {
            attributes.addFlashAttribute("message", "账户或密码错误!");
        } catch (LockedAccountException e) {
            attributes.addFlashAttribute("message", "账户状态异常!");
        } catch (AuthenticationException e) {
            attributes.addFlashAttribute("message", "登录失败!");
        }
        return "redirect:/";
    }

    @GetMapping("/401")
    public String permissionError(){
        return "error/401";
    }

}
