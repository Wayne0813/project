package com.wayne.erp.listener;

import com.google.gson.Gson;
import com.wayne.erp.dto.OrderInfoDto;
import com.wayne.erp.service.FixService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author LV
 * @date 2018/8/8
 */
@Component
public class FixOrderListener implements SessionAwareMessageListener {

    private Logger logger = LoggerFactory.getLogger(FixOrderListener.class);

    @Autowired
    private FixService fixService;

    @Override
    public void onMessage(Message message, Session session) throws JMSException {
        TextMessage textMessage = (TextMessage) message;

        try {
            String content = textMessage.getText();
            OrderInfoDto orderInfoDto = new Gson().fromJson(content, OrderInfoDto.class);
            logger.info("[维修系统]从队列中获取==> {}", orderInfoDto);
            fixService.insert(orderInfoDto);
        } catch (JMSException e) {
            e.printStackTrace();
            session.recover();
        }
    }
}
