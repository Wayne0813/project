package com.wayne.erp.dto;

import com.wayne.erp.entity.Order;
import com.wayne.erp.entity.Parts;
import com.wayne.erp.entity.ServiceType;

import java.util.List;

/**
 * @author LV
 * @date 2018/8/9
 */
public class OrderInfoDto {

    private Order order;
    private ServiceType serviceType;
    private List<Parts> partsList;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public List<Parts> getPartsList() {
        return partsList;
    }

    public void setPartsList(List<Parts> partsList) {
        this.partsList = partsList;
    }

    @Override
    public String toString() {
        return "OrderInfoDto{" +
                "order=" + order +
                ", serviceType=" + serviceType +
                ", partsList=" + partsList +
                '}';
    }
}
