package com.wayne.erp.listener;

import com.wayne.erp.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author LV
 * @date 2018/8/9
 */
public class OrderStateListener implements SessionAwareMessageListener {

    private Logger logger = LoggerFactory.getLogger(OrderStateListener.class);

    @Autowired
    private OrderService orderService;

    @Override
    public void onMessage(Message message, Session session) throws JMSException {

        TextMessage textMessage = (TextMessage) message;

        try {
            String res = textMessage.getText();

            logger.info("[front系统]从队列中取值==> {}", res);

            String[] datas =  res.split("/");
            String state = datas[0];
            String orderId = datas[1];
            String employeeId = datas[2];

            orderService.editOrderWithJms(Integer.valueOf(orderId), Integer.valueOf(employeeId), state);
        } catch (JMSException e) {
            e.printStackTrace();
            session.recover();
        }

    }
}
