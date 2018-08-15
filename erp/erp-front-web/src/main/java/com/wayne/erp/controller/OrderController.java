package com.wayne.erp.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.wayne.erp.dto.ResponseBean;
import com.wayne.erp.entity.*;
import com.wayne.erp.exception.ServiceException;
import com.wayne.erp.service.OrderService;
import com.wayne.erp.service.PartsService;
import com.wayne.erp.service.TypeService;
import com.wayne.erp.vo.OrderInfoVo;
import com.wayne.erp.vo.OrderVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LV
 * @date 2018/8/2
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private PartsService partsService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/undone/list")
    public String unListPage(@RequestParam(name = "p",defaultValue = "1",required = false) Integer pageNo,
                           @RequestParam(required = false) String licenceNo,
                           @RequestParam(required = false) String tel,
                           @RequestParam(required = false) String startTime,
                           @RequestParam(required = false) String endTime,
                           Model model){

        // 封装筛选的queryMap集合
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("pageNo", pageNo);
        queryMap.put("licenceNo", licenceNo);
        queryMap.put("tel", tel);
        queryMap.put("startTime", startTime);
        queryMap.put("endTime", endTime);
        queryMap.put("exState", Order.ORDER_STATE_DONE);

        PageInfo<Order> page = orderService.findPageByParam(queryMap);

        model.addAttribute("type","");
        model.addAttribute("page", page);

        return "order/unList";
    }

    @GetMapping("/done/list")
    public String doneListPage(@RequestParam(name = "p",defaultValue = "1",required = false) Integer pageNo,
                           @RequestParam(required = false) String licenceNo,
                           @RequestParam(required = false) String tel,
                           @RequestParam(required = false) String startTime,
                           @RequestParam(required = false) String endTime,
                           Model model){

        // 封装筛选的queryMap集合
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("pageNo", pageNo);
        queryMap.put("licenceNo", licenceNo);
        queryMap.put("tel", tel);
        queryMap.put("startTime", startTime);
        queryMap.put("endTime", endTime);
        queryMap.put("state", Order.ORDER_STATE_DONE);

        PageInfo<Order> page = orderService.findPageByParam(queryMap);

        model.addAttribute("type","done");
        model.addAttribute("page", page);

        return "order/doneList";
    }

    @GetMapping("/add")
    public String addOrderPage(){
        return "order/add";
    }

    @ResponseBody
    @PostMapping("/add")
    public ResponseBean addOrder(String json) {
        // 将前端数据转化成对对象
        Gson gson = new Gson();
        OrderVo orderVo = gson.fromJson(json, OrderVo.class);

        System.out.println(orderVo);

        // 获得当前登录的员工对象
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();

        orderService.saveOrder(orderVo, employee.getId());

        return ResponseBean.success();
    }

    @GetMapping("/edit/{id:\\d+}")
    public String editPage(@PathVariable Integer id, Model model) {
        model.addAttribute("orderId", id);
        return "order/edit";
    }

    @ResponseBody
    @GetMapping("/info/{id:\\d+}")
    public ResponseBean info(@PathVariable Integer id) {
        // 获得订单信息
        Order order = orderService.findOrderById(id);
        // 获得订单服务类型信息
        ServiceType serviceType = orderService.findServiceTypeById(order.getServiceTypeId());

        // 获得订单配件列表
        List<Parts> partsList = partsService.findPartsByOrderId(order.getId());

        OrderInfoVo orderInfoVo = new OrderInfoVo();
        orderInfoVo.setOrder(order);
        orderInfoVo.setPartsList(partsList);
        orderInfoVo.setServiceType(serviceType);

        return ResponseBean.success(orderInfoVo);
    }


    @ResponseBody
    @PostMapping("/edit")
    public ResponseBean edit(String json) {
        // 将前端数据转化成对对象
        Gson gson = new Gson();
        OrderVo orderVo = gson.fromJson(json, OrderVo.class);
        orderService.editOrder(orderVo);

        return ResponseBean.success();
    }

    @ResponseBody
    @PostMapping("/type")
    public ResponseBean partsByType(Integer typeId) {
        List<Parts> partsList = partsService.findByTypeId(typeId);
        if (partsList == null || partsList.size() == 0) {
            return ResponseBean.error("暂无符合该类型的配件!");
        }
        return ResponseBean.success(partsList);
    }

    @ResponseBody
    @GetMapping("/service/types")
    public ResponseBean serviceTypesPage() {
        List<ServiceType> serviceTypeList = orderService.findAllServiceType();
        return ResponseBean.success(serviceTypeList);
    }

    @ResponseBody
    @GetMapping("/parts/types")
    public ResponseBean partsTypesPage() {
        List<Type> typeList = typeService.findAll();
        return ResponseBean.success(typeList);
    }

    @GetMapping("/detail/{id:\\d+}")
    public String detailPage(@PathVariable Integer id, Model model) {
        // 获得订单信息
        Order order = orderService.findOrderById(id);

        // 获得订单服务类型信息
        ServiceType serviceType = orderService.findServiceTypeById(order.getServiceTypeId());

        // 获得订单配件列表
        List<Parts> partsList = partsService.findPartsByOrderId(order.getId());

        model.addAttribute("order", order);
        model.addAttribute("serviceType", serviceType);
        model.addAttribute("partsList", partsList);
        return "order/detail";
    }

    @ResponseBody
    @GetMapping("/trans/{id:\\d+}")
    public ResponseBean trans(@PathVariable Integer id) {
        try {
            orderService.transOrder(id, Order.ORDER_STATE_TRANS);

        } catch (ServiceException e) {
            return ResponseBean.error(e.getMessage());
        }
        return ResponseBean.success();
    }

    @GetMapping("/del/{id:\\d+}")
    public String deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
        return "redirect:/order/undone/list";
    }

}
