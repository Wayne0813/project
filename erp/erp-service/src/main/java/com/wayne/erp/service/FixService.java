package com.wayne.erp.service;


import com.wayne.erp.dto.OrderInfoDto;
import com.wayne.erp.entity.Employee;
import com.wayne.erp.entity.FixOrder;

import java.util.List;

/**
 * @author LV
 * @date 2018/8/8
 */
public interface FixService {

    /**
     * 将从队列中取的值解析成对象，再拆分解析保存到数据库
     * @param orderInfoDto 队列中取得值
     */
    void insert(OrderInfoDto orderInfoDto);

    /**
     * 通过传入的状态，查找所有符合该状态的订单
     * @param state 需要查询的状态
     * @return 符合状态的订单集合
     */
    List<FixOrder> findAllOrderWithState(String state);

    /**
     * 根据id及传入状态修改订单
     * @param id 需要修改的订单的id
     * @param state 目标状态
     * @param employee 接单员工
     */
    void editFixOrderStateById(Integer id, String state, Employee employee);

    /**
     * 通过id查找订单
     * @param id 参照id
     * @return 目标订单
     */
    FixOrder findFixOrderByOrderId(Integer id);
}
