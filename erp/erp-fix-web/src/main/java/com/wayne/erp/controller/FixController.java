package com.wayne.erp.controller;

import com.wayne.erp.dto.ResponseBean;
import com.wayne.erp.entity.Employee;
import com.wayne.erp.entity.FixOrder;
import com.wayne.erp.entity.Order;
import com.wayne.erp.exception.ServiceException;
import com.wayne.erp.service.FixService;
import com.wayne.erp.util.Constant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @author LV
 * @date 2018/8/7
 */
@Controller
@RequestMapping("/fix")
public class FixController {

    @Autowired
    private FixService fixService;

    @GetMapping("/list")
    public String listPage(Model model) {
        List<FixOrder> fixOrderList = fixService.findAllOrderWithState(Order.ORDER_STATE_TRANS);
        List<FixOrder> overFixOrderList = fixService.findAllOrderWithState(Order.ORDER_STATE_FIXING);
        fixOrderList.addAll(overFixOrderList);

        model.addAttribute("fixOrderList", fixOrderList);
        return "fix/list";
    }

    @ResponseBody
    @GetMapping("/trans/{id:\\d+}")
    public ResponseBean transState(@PathVariable Integer id) {
        try {
            fixService.editFixOrderStateById(id, Order.ORDER_STATE_FIXING, getCurrEmployee());
            return ResponseBean.success();
        } catch (ServiceException e) {
            return ResponseBean.error(e.getMessage());
        }
    }

    @GetMapping("/detail/{id:\\d+}")
    public String detailPage(@PathVariable Integer id, Model model) {
        FixOrder fixOrder = fixService.findFixOrderByOrderId(id);
        model.addAttribute("curr_employee_id", getCurrEmployee().getId());
        model.addAttribute("fixOrder", fixOrder);
        return "fix/detail";
    }

    @GetMapping("/done/{id:\\d+}")
    public String done(@PathVariable Integer id, RedirectAttributes attributes) {
        fixService.editFixOrderStateById(id, Order.ORDER_STATE_FIXED, getCurrEmployee());
        attributes.addFlashAttribute(Constant.SEND_PAGE_MASSAGE_KEY, Constant.SEND_PAGE_MASSAGE_CONTENT);
        return "redirect:/fix/list";
    }


    private Employee getCurrEmployee() {
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();
        return employee;
    }


}
