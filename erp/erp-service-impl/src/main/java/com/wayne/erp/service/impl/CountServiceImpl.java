package com.wayne.erp.service.impl;

import com.wayne.erp.entity.CountDaily;
import com.wayne.erp.entity.Order;
import com.wayne.erp.mapper.OrderMapper;
import com.wayne.erp.service.CountService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LV
 * @date 2018/8/17
 */
public class CountServiceImpl implements CountService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void countDailyOrder() {

        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");

        DateTime dt = new DateTime();
        dt = dt.minusDays(1);

        String orderDate = fmt.print(dt);


        Map<String, String> params = new HashMap<>();
        params.put("state", Order.ORDER_STATE_DONE);
        params.put("dateTime", orderDate);

        List<Order> orderList = orderMapper.selectByWithDaily(params);

        CountDaily countDaily = new CountDaily();

        countDaily.setDateTime(orderDate);

        if(orderList != null && orderList.size() > 0) {
            countDaily.setSumNum(orderList.size());

            for(Order order : orderList) {
                // 计算总金额
            }

        }

    }
}
