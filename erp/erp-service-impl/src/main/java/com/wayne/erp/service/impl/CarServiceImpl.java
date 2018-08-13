package com.wayne.erp.service.impl;

import com.wayne.erp.entity.Car;
import com.wayne.erp.entity.Customer;
import com.wayne.erp.entity.CustomerExample;
import com.wayne.erp.mapper.CarMapper;
import com.wayne.erp.mapper.CustomerMapper;
import com.wayne.erp.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author LV
 * @date 2018/8/2
 */
public class CarServiceImpl implements CarService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CarMapper carMapper;

    /**
     * 新增车主与车辆信息
     * @param car      车辆信息
     * @param customer 车主信息
     * @return         新增车辆信息
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Car saveCarAndCustomer(Car car, Customer customer) {

        // 根据车主身份证号查找车主信息
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andIdCardEqualTo(customer.getIdCard());
        List<Customer> customerList = customerMapper.selectByExample(customerExample);

        Integer customerId;
        if (customerList == null || customerList.size() == 0) {
            // 如果车主信息不存在，则添加车主信息,并获取自动增长的主键
            customerMapper.insertSelective(customer);
            customerId = customer.getId();
        } else {
            // 如果车主信息存在，则获取车主id
            customerId = customerList.get(0).getId();
        }

        // 添加车辆信息
        car.setCustomerId(customerId);
        carMapper.insertSelective(car);

        // 查找新添加的车辆信息，并返回
        Car newCar = carMapper.selectByPrimaryKey(car.getId());
        Customer newCustomer = customerMapper.selectByPrimaryKey(newCar.getCustomerId());
        newCar.setCustomer(newCustomer);
        return newCar;
    }




    /**
     * 通过许可证查找车辆信息
     *
     * @param licenceNo 车辆许可证
     * @return 查找到的车辆信息
     */
    @Override
    public Car findCarByLicenseNo(String licenceNo) {
        return carMapper.selectByLicenseNo(licenceNo);
    }
}
