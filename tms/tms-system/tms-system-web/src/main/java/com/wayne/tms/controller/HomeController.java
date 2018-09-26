package com.wayne.tms.controller;

import com.wayne.tms.dto.ResponseBean;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LV
 * @date 2018/8/30
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String loginPage () {
        Subject subject = SecurityUtils.getSubject();

        System.out.println("isAuthenticated()?" + subject.isAuthenticated());
        System.out.println("isRemembered()?" + subject.isRemembered());

        //判断当前是否有已经认证的账号，如果有，则退出该账号
        if(subject.isAuthenticated()) {
            subject.logout();
        }

        if(subject.isRemembered()) {
            //如果当前为被记住（通过rememberMe认证），则直接跳转到登录成功页面 home
            return "redirect:/home";
        }

        return "index";
    }

    @PostMapping("/")
    @ResponseBody
    public ResponseBean login(String accountMobile,
                        String password,
                        String rememberMe,
                        HttpServletRequest request) {

        Subject subject = SecurityUtils.getSubject();

        String ip = request.getRemoteAddr();

        UsernamePasswordToken token = new UsernamePasswordToken(accountMobile, DigestUtils.md5Hex(password), rememberMe != null, ip);

        try {
            subject.login(token);

            //登录后跳转目标的判断
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            String url = "/home";
            if(savedRequest != null) {
                url = savedRequest.getRequestUrl();
            }

            return ResponseBean.success(url);
        } catch (UnknownAccountException | IncorrectCredentialsException ex) {
            ex.printStackTrace();
            return ResponseBean.error(ex.getMessage());
        } catch (LockedAccountException ex) {
            ex.printStackTrace();
            return ResponseBean.error(ex.getMessage());
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
            return ResponseBean.error("账号或密码错误");
        }

    }

    /**
     * 登录后的首页
     */
    @GetMapping("/home")
    public String home() {
        return "home";
    }


    @GetMapping("/401")
    public String unauthorizedUrl() {
        return "error/401";
    }


}
