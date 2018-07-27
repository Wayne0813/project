package com.wayne.erp.controller;

import com.wayne.erp.entity.Employee;
import com.wayne.erp.exception.PermissionsException;
import com.wayne.erp.service.EmployeeLoginLogService;
import com.wayne.erp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author LV
 * @date 2018/7/26
 */
@Controller
public class HomeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeLoginLogService employeeLoginLogService;

    @GetMapping("/")
    public String loginPage(@CookieValue(value = "userTel", defaultValue = "") String userTel,
                            Model model){
        model.addAttribute("userTel", userTel);
        return "login";
    }

    @PostMapping("/")
    public String login(String userTel,
                        String password,
                        String remember,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        HttpSession session,
                        RedirectAttributes attributes){

        Employee employee = null;
        try {
            employee = employeeService.findByUserTel(userTel, password);
        } catch (PermissionsException e){
            attributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/";
        }
        session.setAttribute("curr_user", employee);

        String ip = request.getRemoteAddr();
        employeeLoginLogService.save(ip, employee.getId());


        if(remember != null){
            Cookie cookie = new Cookie("userTel", employee.getEmployeeTel());
            cookie.setDomain("localhost");
            cookie.setPath("/");
            cookie.setMaxAge(60*60*24*7);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        } else {
            Cookie[] cookies = request.getCookies();
            if(cookies != null){
                for (Cookie cookie : cookies) {
                    if("userTel".equals(cookie.getName())){
                        cookie.setDomain("localhost");
                        cookie.setPath("/");
                        cookie.setMaxAge(0);
                        cookie.setHttpOnly(true);
                        response.addCookie(cookie);
                        break;
                    }
                }
            }

        }
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String homePage(){
        return "home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("curr_user");
        return "redirect:/";
    }


}
