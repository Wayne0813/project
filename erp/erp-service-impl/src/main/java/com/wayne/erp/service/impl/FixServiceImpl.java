package com.wayne.erp.service.impl;

import com.wayne.erp.dto.OrderInfoDto;
import com.wayne.erp.entity.*;
import com.wayne.erp.exception.ServiceException;
import com.wayne.erp.mapper.FixOrderMapper;
import com.wayne.erp.mapper.FixPartsMapper;
import com.wayne.erp.service.FixService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.math.BigDecimal;
import java.util.List;


/**
 * @author LV
 * @date 2018/8/8
 */
@Service
public class FixServiceImpl implements FixService {

    private Logger logger = LoggerFactory.getLogger(FixServiceImpl.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private FixService fixService;

    @Autowired
    private FixOrderMapper fixOrderMapper;

    @Autowired
    private FixPartsMapper fixPartsMapper;


    /**
     * 将从队列中取的值解析成对象，再拆分解析保存到数据库
     * @param orderInfoDto 队列中取得值
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void insert(OrderInfoDto orderInfoDto) {

        // 订单基本信息
        FixOrder fixOrder = new FixOrder();
        fixOrder.setOrderId(orderInfoDto.getOrder().getId());
        fixOrder.setOrderState(orderInfoDto.getOrder().getState());
        fixOrder.setOrderTime(orderInfoDto.getOrder().getCreateTime());

        // 车辆信息
        fixOrder.setCarLicence(orderInfoDto.getOrder().getCar().getLicenceNo());
        fixOrder.setCarType(orderInfoDto.getOrder().getCar().getCarType());
        fixOrder.setCarColor(orderInfoDto.getOrder().getCar().getColor());

        // 封装车主信息
        fixOrder.setCustomerName(orderInfoDto.getOrder().getCustomer().getUserName());
        fixOrder.setCustomerTel(orderInfoDto.getOrder().getCustomer().getTel());

        // 封装订单类型及计算金额
        fixOrder.setOrderType(orderInfoDto.getServiceType().getServiceName());
        fixOrder.setServiceHour(orderInfoDto.getServiceType().getServiceHour());
        fixOrder.setServiceHour(orderInfoDto.getServiceType().getServiceHour());
        fixOrder.setServiceHourFee(new BigDecimal(Integer.parseInt(orderInfoDto.getServiceType().getServiceHour()) * 50));

        fixOrder.setOrderMoney(orderInfoDto.getOrder().getOrderMoney());
        fixOrder.setPartsFee(fixOrder.getOrderMoney().subtract(fixOrder.getServiceHourFee()));

        fixOrderMapper.insertSelective(fixOrder);

        for (Parts parts : orderInfoDto.getPartsList()) {
            FixParts fixParts = new FixParts();
            fixParts.setOrderId(orderInfoDto.getOrder().getId());
            fixParts.setPartsId(parts.getId());
            fixParts.setPartsName(parts.getPartsName());
            fixParts.setPartsNo(parts.getPartsNo());
            fixParts.setPartsNum(parts.getNum());

            fixPartsMapper.insertSelective(fixParts);
        }

    }

    /**
     * 通过传入的状态，查找所有符合该状态的订单
     *
     * @param state 需要查询的状态
     * @return 符合状态的订单集合
     */
    @Override
    public List<FixOrder> findAllOrderWithState(String state) {
        return fixOrderMapper.selectOrderAndPartsByState(state);
    }

    /**
     * 根据id及传入状态修改订单
     * @param id    需要修改的订单的id
     * @param state 目标状态
     * @param employee 接单员工
     */
    @Override
    public void editFixOrderStateById(Integer id, String state, Employee employee) {
        FixOrder fixOrder = fixOrderMapper.selectByPrimaryKey(id);
        if(fixOrder == null) {
            throw new ServiceException("该订单不存在,或已被处理!");
        }

        if (fixOrder.getOrderState().equals(state)) {
            throw new ServiceException("操作异常!");
        }

        if(Order.ORDER_STATE_FIXING.equals(state)) {
            // 添加维修员工及修改状态
            fixOrder.setFixEmployeeId(employee.getId());
            fixOrder.setFixEmployeeName(employee.getEmployeeName());
        } else if(Order.ORDER_STATE_CHECKING.equals(state)) {
            // 添加质检员工及修改状态
            fixOrder.setCheckEmployeeId(employee.getId());
            fixOrder.setCheckEmployeeName(employee.getEmployeeName());
        }
        fixOrder.setOrderState(state);
        fixOrderMapper.updateByPrimaryKeySelective(fixOrder);

        // 将数据发送到队列，由front系统接收
        sendStateToFront(id, state, employee.getId());
    }

    /**
     * 通过id查找订单
     * @param id 参照id
     * @return 目标订单
     */
    @Override
    public FixOrder findFixOrderByOrderId(Integer id) {
        FixOrder fixOrder =  fixOrderMapper.selectByPrimaryKey(id);
        List<FixParts> fixPartsList = selectFixPartsByOrderId(id);
        fixOrder.setFixPartsList(fixPartsList);
        return fixOrder;
    }

    /**
     * 根据订单id查找所需配件集合
     * @param orderId 订单id
     * @return 目标配件集合
     */
    private  List<FixParts> selectFixPartsByOrderId(Integer orderId) {
        FixPartsExample fixPartsExample = new FixPartsExample();
        fixPartsExample.createCriteria().andOrderIdEqualTo(orderId);
        return fixPartsMapper.selectByExample(fixPartsExample);
    }

    /**
     * 将数据发送到队列中
     * @param orderId 订单id
     * @param state 修改后的状态
     * @param employeeId 维修员id
     */
    private void sendStateToFront(Integer orderId, String state, Integer employeeId) {

        String res = state + "/" + orderId + "/" + employeeId;

        jmsTemplate.send("order-state", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                logger.info("[Fix系统]将数据添加至队列中==> {}", res);
                return session.createTextMessage(res);
            }
        });
    }

}
