package com.wayne.erp.service;

import com.wayne.erp.entity.Car;
import com.wayne.erp.entity.Customer;

/**
 * @author LV
 * @date 2018/8/2
 */
public interface CarService {


    /**
     * 新增车主与车辆信息
     * @param car 车辆信息
     * @param customer 车主信息
     * @return 新增车辆信息
     */
    Car saveCarAndCustomer(Car car, Customer customer);


    /**
     * 通过许可证查找车辆信息
     * @param licenseNo 车辆许可证
     * @return 查找到的车辆信息
     */
    Car findCarByLicenseNo(String licenceNo);
}
