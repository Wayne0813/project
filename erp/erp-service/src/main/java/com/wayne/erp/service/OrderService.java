package com.wayne.erp.service;

import com.github.pagehelper.PageInfo;
import com.wayne.erp.entity.Car;
import com.wayne.erp.entity.Employee;
import com.wayne.erp.entity.Order;
import com.wayne.erp.entity.ServiceType;
import com.wayne.erp.vo.OrderInfoVo;
import com.wayne.erp.vo.OrderVo;

import java.util.List;
import java.util.Map;

/**
 * @author LV
 * @date 2018/8/6
 */
public interface OrderService {

    /**查找所有服务类型
     * @return 所有服务的集合
     */
    List<ServiceType> findAllServiceType();


    /**
     * 添加订单信息
     * @param orderVo 订单
     * @param employeeId 员工id
     */
    void saveOrder(OrderVo orderVo, Integer employeeId);

    /**
     * 通过条件查找订单
     * @param queryMap 参数集合
     * @return 订单分页
     */
    PageInfo<Order> findPageByParam(Map<String,Object> queryMap);

    /** 根据id查找订单信息
     * @param id 订单id
     * @return 订单详情
     */
    Order findOrderById(Integer id);

    /** 根据服务类型id查找服务信息
     * @param serviceTypeId 服务类型id
     * @return 服务详情
     */
    ServiceType findServiceTypeById(Integer serviceTypeId);

    /**
     * 修改订单
     * @param orderVo 订单信息
     */
    void editOrder(OrderVo orderVo);

    /**
     * 根据订单id删除订单
     * @param orderId 订单id
     */
    void deleteOrder(Integer orderId);


    /**
     * 修改订单状态，根据传入状态修改
     * @param orderId 订单id
     * @param state 目标状态
     */
    void transOrder(Integer orderId, String state);

    /**
     * 新增员工订单关系表
     * @param orderId 订单id
     * @param employeeId 员工id
     */
    void saveOrderEmployee(Integer orderId, Integer employeeId);

    /**
     * 根据从队列中获取的数据，修改订单
     * @param orderId 订单id
     * @param employeeId 员工id
     * @param state 目标状态
     */
    void editOrderWithJms(Integer orderId, Integer employeeId, String state);
}
