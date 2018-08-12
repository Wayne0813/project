package com.wayne.erp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 
 */
public class FixOrder implements Serializable {
    /**
     * 订单号
     */
    private Integer orderId;

    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 订单日期
     */
    private Date orderTime;

    /**
     * 订单状态：2：已下发 3：维修中 4：维修完成 5：质检中 6：结算中
     */
    private String orderState;

    /**
     * 订单金额
     */
    private BigDecimal orderMoney;

    /**
     * 订单工时
     */
    private String serviceHour;

    /**
     * 订单工时费
     */
    private BigDecimal serviceHourFee;

    /**
     * 订单配件费用
     */
    private BigDecimal partsFee;

    /**
     * 车类型
     */
    private String carType;

    /**
     * 车颜色
     */
    private String carColor;

    /**
     * 车牌号
     */
    private String carLicence;

    /**
     * 车主名称
     */
    private String customerName;

    /**
     * 车主电话
     */
    private String customerTel;

    /**
     * 维修员id
     */
    private Integer fixEmployeeId;

    /**
     * 维修员姓名
     */
    private String fixEmployeeName;

    /**
     * 质检员id
     */
    private Integer checkEmployeeId;

    /**
     * 质检员姓名
     */
    private String checkEmployeeName;

    /**
     * 配件信息
     */
    private List<FixParts> fixPartsList;

    private static final long serialVersionUID = 1L;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getServiceHour() {
        return serviceHour;
    }

    public void setServiceHour(String serviceHour) {
        this.serviceHour = serviceHour;
    }

    public BigDecimal getServiceHourFee() {
        return serviceHourFee;
    }

    public void setServiceHourFee(BigDecimal serviceHourFee) {
        this.serviceHourFee = serviceHourFee;
    }

    public BigDecimal getPartsFee() {
        return partsFee;
    }

    public void setPartsFee(BigDecimal partsFee) {
        this.partsFee = partsFee;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarLicence() {
        return carLicence;
    }

    public void setCarLicence(String carLicence) {
        this.carLicence = carLicence;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }

    public Integer getFixEmployeeId() {
        return fixEmployeeId;
    }

    public void setFixEmployeeId(Integer fixEmployeeId) {
        this.fixEmployeeId = fixEmployeeId;
    }

    public String getFixEmployeeName() {
        return fixEmployeeName;
    }

    public void setFixEmployeeName(String fixEmployeeName) {
        this.fixEmployeeName = fixEmployeeName;
    }

    public Integer getCheckEmployeeId() {
        return checkEmployeeId;
    }

    public void setCheckEmployeeId(Integer checkEmployeeId) {
        this.checkEmployeeId = checkEmployeeId;
    }

    public String getCheckEmployeeName() {
        return checkEmployeeName;
    }

    public void setCheckEmployeeName(String checkEmployeeName) {
        this.checkEmployeeName = checkEmployeeName;
    }

    public List<FixParts> getFixPartsList() {
        return fixPartsList;
    }

    public void setFixPartsList(List<FixParts> fixPartsList) {
        this.fixPartsList = fixPartsList;
    }

    @Override
    public String toString() {
        return "FixOrder{" +
                "orderId=" + orderId +
                ", orderType='" + orderType + '\'' +
                ", orderTime=" + orderTime +
                ", orderState='" + orderState + '\'' +
                ", orderMoney=" + orderMoney +
                ", serviceHour='" + serviceHour + '\'' +
                ", serviceHourFee=" + serviceHourFee +
                ", partsFee=" + partsFee +
                ", carType='" + carType + '\'' +
                ", carColor='" + carColor + '\'' +
                ", carLicence='" + carLicence + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerTel='" + customerTel + '\'' +
                ", fixEmployeeId=" + fixEmployeeId +
                ", fixEmployeeName='" + fixEmployeeName + '\'' +
                ", checkEmployeeId=" + checkEmployeeId +
                ", checkEmployeeName='" + checkEmployeeName + '\'' +
                ", fixPartsList=" + fixPartsList +
                '}';
    }
}