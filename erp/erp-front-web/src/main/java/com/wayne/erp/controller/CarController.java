package com.wayne.erp.controller;

import com.wayne.erp.dto.ResponseBean;
import com.wayne.erp.entity.Car;
import com.wayne.erp.entity.Customer;
import com.wayne.erp.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author LV
 * @date 2018/8/2
 */
@Controller
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @ResponseBody
    @PostMapping("/add")
    public ResponseBean addCar(Car car, Customer customer){
        Car newCar = carService.saveCarAndCustomer(car, customer);
        System.out.println("car" + newCar);
        System.out.println("customer " + newCar.getCustomer());
        return ResponseBean.success(newCar);
    }

    @ResponseBody
    @GetMapping("/check")
    public ResponseBean check(String licenceNo){
        Car car = carService.findCarByLicenseNo(licenceNo);
        if (car == null) {
            return ResponseBean.error("查无此车");
        } else {
            return ResponseBean.success(car);
        }
    }
}

