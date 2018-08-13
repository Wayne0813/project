package com.wayne.erp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.wayne.erp.dto.OrderInfoDto;
import com.wayne.erp.entity.*;
import com.wayne.erp.exception.ServiceException;
import com.wayne.erp.mapper.*;
import com.wayne.erp.service.OrderService;
import com.wayne.erp.service.PartsService;
import com.wayne.erp.util.Constant;
import com.wayne.erp.vo.OrderVo;
import com.wayne.erp.vo.PartsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.List;
import java.util.Map;

/**
 * @author LV
 * @date 2018/8/6
 */
public class OrderServiceImpl implements OrderService {

    private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private PartsMapper partsMapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PartsService partsService;

    @Autowired
    private OrderPartsMapper orderPartsMapper;

    @Autowired
    private ServiceTypeMapper serviceTypeMapper;

    @Autowired
    private OrderEmployeeMapper orderEmployeeMapper;

    /**
     * 查找所有服务类型
     * @return 所有服务的集合
     */
    @Override
    public List<ServiceType> findAllServiceType() {
        ServiceTypeExample serviceTypeExample = new ServiceTypeExample();
        serviceTypeExample.createCriteria();
        return serviceTypeMapper.selectByExample(serviceTypeExample);
    }

    /**
     * 添加订单信息
     * @param orderVo    订单
     * @param employeeId 员工id
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveOrder(OrderVo orderVo, Integer employeeId) {
        // 新增订单
        Order order = new Order();
        order.setCarId(orderVo.getCarId());
        order.setOrderMoney(orderVo.getFee());
        order.setServiceTypeId(orderVo.getServiceTypeId());
        order.setState(Order.ORDER_STATE_NEW);

        orderMapper.insertSelective(order);

        // 新增员工订单关系表
        saveOrderEmployee(order.getId(), employeeId);

        // 新增配件订单关系表
        List<PartsVo> partsVoList = orderVo.getPartsLists();
        addOrderParts(order.getId(), partsVoList);
        logger.info("{}--新增订单{}", employeeId, order.getId());
    }

    /**
     * 通过条件查找订单
     * @param queryMap 参数集合
     * @return 订单分页
     */
    @Override
    public PageInfo<Order> findPageByParam(Map<String, Object> queryMap) {
        PageHelper.startPage(Integer.parseInt(String.valueOf(queryMap.get("pageNo"))),Constant.DEFAULT_PAGE_SIZE);
        List<Order> orderList = orderMapper.findUndonePageByParam(queryMap);
        PageInfo<Order> pageInfo = new PageInfo<>(orderList);
        return pageInfo;
    }

    /**
     * 根据id查找订单信息
     * @param id 订单id
     * @return 订单详情
     */
    @Override
    public Order findOrderById(Integer id) {
        return orderMapper.findWithCarInfoById(id);
    }

    /**
     * 根据服务类型id查找服务信息
     * @param id 服务类型id
     * @return 服务详情
     */
    @Override
    public ServiceType findServiceTypeById(Integer id) {
        return serviceTypeMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改订单
     * @param orderVo 订单信息
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void editOrder(OrderVo orderVo) {
        // 更新订单
        Order order = orderMapper.selectByPrimaryKey(orderVo.getId());

        order.setOrderMoney(orderVo.getFee());
        order.setCarId(orderVo.getCarId());
        order.setServiceTypeId(orderVo.getServiceTypeId());

        orderMapper.updateByPrimaryKeySelective(order);

        // 删除原有的关联关系
        OrderPartsExample orderPartsExample = new OrderPartsExample();
        orderPartsExample.createCriteria().andOrderIdEqualTo(orderVo.getId());
        orderPartsMapper.deleteByExample(orderPartsExample);

        // 新增订单和配件关系表
        List<PartsVo> partsVoList = orderVo.getPartsLists();
        addOrderParts(order.getId(), partsVoList);

        logger.info("更新订单{}", order.getId());
    }

    /**
     * 根据订单id删除订单
     * @param orderId 订单id
     */
    @Override
    public void deleteOrder(Integer orderId) {
        OrderPartsExample orderPartsExample = new OrderPartsExample();
        orderPartsExample.createCriteria().andOrderIdEqualTo(orderId);
        orderPartsMapper.deleteByExample(orderPartsExample);

        OrderEmployeeExample orderEmployeeExample = new OrderEmployeeExample();
        orderEmployeeExample.createCriteria().andOrderIdEqualTo(orderId);
        orderEmployeeMapper.deleteByExample(orderEmployeeExample);

        orderMapper.deleteByPrimaryKey(orderId);
    }

    /**
     * 修改订单状态，根据传入状态修改
     * @param orderId 订单id
     * @param state 目标状态
     */
    @Override
    public void transOrder(Integer orderId, String state) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null) {
            throw new ServiceException("操作异常!");
        }
        if (order.getState().equals(state)) {
            throw new ServiceException("操作异常!");
        }
        // 设置订单状态为已下发
        order.setState(state);
        orderMapper.updateByPrimaryKeySelective(order);

        // 将订单放到队列中
        sendToFixOrderQueue(orderId);
    }

    /**
     * 新增员工订单关系表
     * @param orderId    订单id
     * @param employeeId 员工id
     */
    @Override
    public void saveOrderEmployee(Integer orderId, Integer employeeId) {
        OrderEmployee orderEmployee = new OrderEmployee();
        orderEmployee.setOrderId(orderId);
        orderEmployee.setEmployeeId(employeeId);
        orderEmployeeMapper.insertSelective(orderEmployee);
    }

    /**
     * 根据从队列中获取的数据，修改订单
     * @param orderId    订单id
     * @param employeeId 员工id
     * @param state      目标状态
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void editOrderWithJms(Integer orderId, Integer employeeId, String state) {
        transOrder(orderId, state);
        if(state.equals(Order.ORDER_STATE_FIXED)) {
            logger.info("{}维修订单{}完成!", employeeId, orderId);
        } else {
            if(state.equals(Order.ORDER_STATE_SETTLEMENT)) {
                logger.info("{}质检订单{}完成!", employeeId, orderId);
            } else {
                saveOrderEmployee(orderId, employeeId);
                logger.info("{}领取订单{}!", employeeId, orderId);
            }
        }

        List<OrderParts> orderPartsList = findOrderPartsByOrderid(orderId);

        // 更新库存
        for (OrderParts orderParts : orderPartsList) {
            Parts parts = partsMapper.selectByPrimaryKey(orderParts.getPartsId());
            parts.setInventory(parts.getInventory() - orderParts.getNum());
            partsMapper.updateByPrimaryKeySelective(parts);
        }
    }

    /**
     * 根据订单id查找订单配件关系表
     * @param orderId 订单id
     * @return 订单配件关系表
     */
    private List<OrderParts> findOrderPartsByOrderid(Integer orderId) {
        OrderPartsExample orderPartsExample = new OrderPartsExample();
        orderPartsExample.createCriteria().andOrderIdEqualTo(orderId);

        return orderPartsMapper.selectByExample(orderPartsExample);
    }

    /**
     *    新增配件订单关联关系
     */
    private void addOrderParts(Integer orderId, List<PartsVo> partsVoList) {
        for(PartsVo partsVo : partsVoList) {
            OrderParts orderParts = new OrderParts();
            orderParts.setOrderId(orderId);
            orderParts.setPartsId(partsVo.getId());
            orderParts.setNum(partsVo.getNum());

            orderPartsMapper.insertSelective(orderParts);
        }
    }

    /**
     * 将数据添加到队列中，发送给维修系统
     * @param orderId 订单id
     */
    private void sendToFixOrderQueue(Integer orderId) {

        OrderInfoDto orderInfoDto = new OrderInfoDto();

        Order order = orderService.findOrderById(orderId);
        ServiceType serviceType = orderService.findServiceTypeById(order.getServiceTypeId());
        List<Parts> partsList =  partsService.findPartsByOrderId(order.getId());

        orderInfoDto.setOrder(order);
        orderInfoDto.setServiceType(serviceType);
        orderInfoDto.setPartsList(partsList);

        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                logger.info("添加到队列中==> {}", orderInfoDto);
                return session.createTextMessage(new Gson().toJson(orderInfoDto));
            }
        });
    }

}
