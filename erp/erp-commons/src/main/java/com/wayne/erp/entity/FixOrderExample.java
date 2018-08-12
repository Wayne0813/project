package com.wayne.erp.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FixOrderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public FixOrderExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(Integer value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(Integer value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(Integer value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(Integer value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(Integer value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<Integer> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<Integer> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(Integer value1, Integer value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(Integer value1, Integer value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNull() {
            addCriterion("order_type is null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNotNull() {
            addCriterion("order_type is not null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeEqualTo(String value) {
            addCriterion("order_type =", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotEqualTo(String value) {
            addCriterion("order_type <>", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThan(String value) {
            addCriterion("order_type >", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThanOrEqualTo(String value) {
            addCriterion("order_type >=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThan(String value) {
            addCriterion("order_type <", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThanOrEqualTo(String value) {
            addCriterion("order_type <=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLike(String value) {
            addCriterion("order_type like", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotLike(String value) {
            addCriterion("order_type not like", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIn(List<String> values) {
            addCriterion("order_type in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotIn(List<String> values) {
            addCriterion("order_type not in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeBetween(String value1, String value2) {
            addCriterion("order_type between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotBetween(String value1, String value2) {
            addCriterion("order_type not between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTimeIsNull() {
            addCriterion("order_time is null");
            return (Criteria) this;
        }

        public Criteria andOrderTimeIsNotNull() {
            addCriterion("order_time is not null");
            return (Criteria) this;
        }

        public Criteria andOrderTimeEqualTo(Date value) {
            addCriterion("order_time =", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeNotEqualTo(Date value) {
            addCriterion("order_time <>", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeGreaterThan(Date value) {
            addCriterion("order_time >", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("order_time >=", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeLessThan(Date value) {
            addCriterion("order_time <", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeLessThanOrEqualTo(Date value) {
            addCriterion("order_time <=", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeIn(List<Date> values) {
            addCriterion("order_time in", values, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeNotIn(List<Date> values) {
            addCriterion("order_time not in", values, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeBetween(Date value1, Date value2) {
            addCriterion("order_time between", value1, value2, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeNotBetween(Date value1, Date value2) {
            addCriterion("order_time not between", value1, value2, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderStateIsNull() {
            addCriterion("order_state is null");
            return (Criteria) this;
        }

        public Criteria andOrderStateIsNotNull() {
            addCriterion("order_state is not null");
            return (Criteria) this;
        }

        public Criteria andOrderStateEqualTo(String value) {
            addCriterion("order_state =", value, "orderState");
            return (Criteria) this;
        }

        public Criteria andOrderStateNotEqualTo(String value) {
            addCriterion("order_state <>", value, "orderState");
            return (Criteria) this;
        }

        public Criteria andOrderStateGreaterThan(String value) {
            addCriterion("order_state >", value, "orderState");
            return (Criteria) this;
        }

        public Criteria andOrderStateGreaterThanOrEqualTo(String value) {
            addCriterion("order_state >=", value, "orderState");
            return (Criteria) this;
        }

        public Criteria andOrderStateLessThan(String value) {
            addCriterion("order_state <", value, "orderState");
            return (Criteria) this;
        }

        public Criteria andOrderStateLessThanOrEqualTo(String value) {
            addCriterion("order_state <=", value, "orderState");
            return (Criteria) this;
        }

        public Criteria andOrderStateLike(String value) {
            addCriterion("order_state like", value, "orderState");
            return (Criteria) this;
        }

        public Criteria andOrderStateNotLike(String value) {
            addCriterion("order_state not like", value, "orderState");
            return (Criteria) this;
        }

        public Criteria andOrderStateIn(List<String> values) {
            addCriterion("order_state in", values, "orderState");
            return (Criteria) this;
        }

        public Criteria andOrderStateNotIn(List<String> values) {
            addCriterion("order_state not in", values, "orderState");
            return (Criteria) this;
        }

        public Criteria andOrderStateBetween(String value1, String value2) {
            addCriterion("order_state between", value1, value2, "orderState");
            return (Criteria) this;
        }

        public Criteria andOrderStateNotBetween(String value1, String value2) {
            addCriterion("order_state not between", value1, value2, "orderState");
            return (Criteria) this;
        }

        public Criteria andOrderMoneyIsNull() {
            addCriterion("order_money is null");
            return (Criteria) this;
        }

        public Criteria andOrderMoneyIsNotNull() {
            addCriterion("order_money is not null");
            return (Criteria) this;
        }

        public Criteria andOrderMoneyEqualTo(BigDecimal value) {
            addCriterion("order_money =", value, "orderMoney");
            return (Criteria) this;
        }

        public Criteria andOrderMoneyNotEqualTo(BigDecimal value) {
            addCriterion("order_money <>", value, "orderMoney");
            return (Criteria) this;
        }

        public Criteria andOrderMoneyGreaterThan(BigDecimal value) {
            addCriterion("order_money >", value, "orderMoney");
            return (Criteria) this;
        }

        public Criteria andOrderMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("order_money >=", value, "orderMoney");
            return (Criteria) this;
        }

        public Criteria andOrderMoneyLessThan(BigDecimal value) {
            addCriterion("order_money <", value, "orderMoney");
            return (Criteria) this;
        }

        public Criteria andOrderMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("order_money <=", value, "orderMoney");
            return (Criteria) this;
        }

        public Criteria andOrderMoneyIn(List<BigDecimal> values) {
            addCriterion("order_money in", values, "orderMoney");
            return (Criteria) this;
        }

        public Criteria andOrderMoneyNotIn(List<BigDecimal> values) {
            addCriterion("order_money not in", values, "orderMoney");
            return (Criteria) this;
        }

        public Criteria andOrderMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_money between", value1, value2, "orderMoney");
            return (Criteria) this;
        }

        public Criteria andOrderMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_money not between", value1, value2, "orderMoney");
            return (Criteria) this;
        }

        public Criteria andServiceHourIsNull() {
            addCriterion("service_hour is null");
            return (Criteria) this;
        }

        public Criteria andServiceHourIsNotNull() {
            addCriterion("service_hour is not null");
            return (Criteria) this;
        }

        public Criteria andServiceHourEqualTo(String value) {
            addCriterion("service_hour =", value, "serviceHour");
            return (Criteria) this;
        }

        public Criteria andServiceHourNotEqualTo(String value) {
            addCriterion("service_hour <>", value, "serviceHour");
            return (Criteria) this;
        }

        public Criteria andServiceHourGreaterThan(String value) {
            addCriterion("service_hour >", value, "serviceHour");
            return (Criteria) this;
        }

        public Criteria andServiceHourGreaterThanOrEqualTo(String value) {
            addCriterion("service_hour >=", value, "serviceHour");
            return (Criteria) this;
        }

        public Criteria andServiceHourLessThan(String value) {
            addCriterion("service_hour <", value, "serviceHour");
            return (Criteria) this;
        }

        public Criteria andServiceHourLessThanOrEqualTo(String value) {
            addCriterion("service_hour <=", value, "serviceHour");
            return (Criteria) this;
        }

        public Criteria andServiceHourLike(String value) {
            addCriterion("service_hour like", value, "serviceHour");
            return (Criteria) this;
        }

        public Criteria andServiceHourNotLike(String value) {
            addCriterion("service_hour not like", value, "serviceHour");
            return (Criteria) this;
        }

        public Criteria andServiceHourIn(List<String> values) {
            addCriterion("service_hour in", values, "serviceHour");
            return (Criteria) this;
        }

        public Criteria andServiceHourNotIn(List<String> values) {
            addCriterion("service_hour not in", values, "serviceHour");
            return (Criteria) this;
        }

        public Criteria andServiceHourBetween(String value1, String value2) {
            addCriterion("service_hour between", value1, value2, "serviceHour");
            return (Criteria) this;
        }

        public Criteria andServiceHourNotBetween(String value1, String value2) {
            addCriterion("service_hour not between", value1, value2, "serviceHour");
            return (Criteria) this;
        }

        public Criteria andServiceHourFeeIsNull() {
            addCriterion("service_hour_fee is null");
            return (Criteria) this;
        }

        public Criteria andServiceHourFeeIsNotNull() {
            addCriterion("service_hour_fee is not null");
            return (Criteria) this;
        }

        public Criteria andServiceHourFeeEqualTo(BigDecimal value) {
            addCriterion("service_hour_fee =", value, "serviceHourFee");
            return (Criteria) this;
        }

        public Criteria andServiceHourFeeNotEqualTo(BigDecimal value) {
            addCriterion("service_hour_fee <>", value, "serviceHourFee");
            return (Criteria) this;
        }

        public Criteria andServiceHourFeeGreaterThan(BigDecimal value) {
            addCriterion("service_hour_fee >", value, "serviceHourFee");
            return (Criteria) this;
        }

        public Criteria andServiceHourFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("service_hour_fee >=", value, "serviceHourFee");
            return (Criteria) this;
        }

        public Criteria andServiceHourFeeLessThan(BigDecimal value) {
            addCriterion("service_hour_fee <", value, "serviceHourFee");
            return (Criteria) this;
        }

        public Criteria andServiceHourFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("service_hour_fee <=", value, "serviceHourFee");
            return (Criteria) this;
        }

        public Criteria andServiceHourFeeIn(List<BigDecimal> values) {
            addCriterion("service_hour_fee in", values, "serviceHourFee");
            return (Criteria) this;
        }

        public Criteria andServiceHourFeeNotIn(List<BigDecimal> values) {
            addCriterion("service_hour_fee not in", values, "serviceHourFee");
            return (Criteria) this;
        }

        public Criteria andServiceHourFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("service_hour_fee between", value1, value2, "serviceHourFee");
            return (Criteria) this;
        }

        public Criteria andServiceHourFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("service_hour_fee not between", value1, value2, "serviceHourFee");
            return (Criteria) this;
        }

        public Criteria andPartsFeeIsNull() {
            addCriterion("parts_fee is null");
            return (Criteria) this;
        }

        public Criteria andPartsFeeIsNotNull() {
            addCriterion("parts_fee is not null");
            return (Criteria) this;
        }

        public Criteria andPartsFeeEqualTo(BigDecimal value) {
            addCriterion("parts_fee =", value, "partsFee");
            return (Criteria) this;
        }

        public Criteria andPartsFeeNotEqualTo(BigDecimal value) {
            addCriterion("parts_fee <>", value, "partsFee");
            return (Criteria) this;
        }

        public Criteria andPartsFeeGreaterThan(BigDecimal value) {
            addCriterion("parts_fee >", value, "partsFee");
            return (Criteria) this;
        }

        public Criteria andPartsFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("parts_fee >=", value, "partsFee");
            return (Criteria) this;
        }

        public Criteria andPartsFeeLessThan(BigDecimal value) {
            addCriterion("parts_fee <", value, "partsFee");
            return (Criteria) this;
        }

        public Criteria andPartsFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("parts_fee <=", value, "partsFee");
            return (Criteria) this;
        }

        public Criteria andPartsFeeIn(List<BigDecimal> values) {
            addCriterion("parts_fee in", values, "partsFee");
            return (Criteria) this;
        }

        public Criteria andPartsFeeNotIn(List<BigDecimal> values) {
            addCriterion("parts_fee not in", values, "partsFee");
            return (Criteria) this;
        }

        public Criteria andPartsFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("parts_fee between", value1, value2, "partsFee");
            return (Criteria) this;
        }

        public Criteria andPartsFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("parts_fee not between", value1, value2, "partsFee");
            return (Criteria) this;
        }

        public Criteria andCarTypeIsNull() {
            addCriterion("car_type is null");
            return (Criteria) this;
        }

        public Criteria andCarTypeIsNotNull() {
            addCriterion("car_type is not null");
            return (Criteria) this;
        }

        public Criteria andCarTypeEqualTo(String value) {
            addCriterion("car_type =", value, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeNotEqualTo(String value) {
            addCriterion("car_type <>", value, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeGreaterThan(String value) {
            addCriterion("car_type >", value, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeGreaterThanOrEqualTo(String value) {
            addCriterion("car_type >=", value, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeLessThan(String value) {
            addCriterion("car_type <", value, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeLessThanOrEqualTo(String value) {
            addCriterion("car_type <=", value, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeLike(String value) {
            addCriterion("car_type like", value, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeNotLike(String value) {
            addCriterion("car_type not like", value, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeIn(List<String> values) {
            addCriterion("car_type in", values, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeNotIn(List<String> values) {
            addCriterion("car_type not in", values, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeBetween(String value1, String value2) {
            addCriterion("car_type between", value1, value2, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeNotBetween(String value1, String value2) {
            addCriterion("car_type not between", value1, value2, "carType");
            return (Criteria) this;
        }

        public Criteria andCarColorIsNull() {
            addCriterion("car_color is null");
            return (Criteria) this;
        }

        public Criteria andCarColorIsNotNull() {
            addCriterion("car_color is not null");
            return (Criteria) this;
        }

        public Criteria andCarColorEqualTo(String value) {
            addCriterion("car_color =", value, "carColor");
            return (Criteria) this;
        }

        public Criteria andCarColorNotEqualTo(String value) {
            addCriterion("car_color <>", value, "carColor");
            return (Criteria) this;
        }

        public Criteria andCarColorGreaterThan(String value) {
            addCriterion("car_color >", value, "carColor");
            return (Criteria) this;
        }

        public Criteria andCarColorGreaterThanOrEqualTo(String value) {
            addCriterion("car_color >=", value, "carColor");
            return (Criteria) this;
        }

        public Criteria andCarColorLessThan(String value) {
            addCriterion("car_color <", value, "carColor");
            return (Criteria) this;
        }

        public Criteria andCarColorLessThanOrEqualTo(String value) {
            addCriterion("car_color <=", value, "carColor");
            return (Criteria) this;
        }

        public Criteria andCarColorLike(String value) {
            addCriterion("car_color like", value, "carColor");
            return (Criteria) this;
        }

        public Criteria andCarColorNotLike(String value) {
            addCriterion("car_color not like", value, "carColor");
            return (Criteria) this;
        }

        public Criteria andCarColorIn(List<String> values) {
            addCriterion("car_color in", values, "carColor");
            return (Criteria) this;
        }

        public Criteria andCarColorNotIn(List<String> values) {
            addCriterion("car_color not in", values, "carColor");
            return (Criteria) this;
        }

        public Criteria andCarColorBetween(String value1, String value2) {
            addCriterion("car_color between", value1, value2, "carColor");
            return (Criteria) this;
        }

        public Criteria andCarColorNotBetween(String value1, String value2) {
            addCriterion("car_color not between", value1, value2, "carColor");
            return (Criteria) this;
        }

        public Criteria andCarLicenceIsNull() {
            addCriterion("car_licence is null");
            return (Criteria) this;
        }

        public Criteria andCarLicenceIsNotNull() {
            addCriterion("car_licence is not null");
            return (Criteria) this;
        }

        public Criteria andCarLicenceEqualTo(String value) {
            addCriterion("car_licence =", value, "carLicence");
            return (Criteria) this;
        }

        public Criteria andCarLicenceNotEqualTo(String value) {
            addCriterion("car_licence <>", value, "carLicence");
            return (Criteria) this;
        }

        public Criteria andCarLicenceGreaterThan(String value) {
            addCriterion("car_licence >", value, "carLicence");
            return (Criteria) this;
        }

        public Criteria andCarLicenceGreaterThanOrEqualTo(String value) {
            addCriterion("car_licence >=", value, "carLicence");
            return (Criteria) this;
        }

        public Criteria andCarLicenceLessThan(String value) {
            addCriterion("car_licence <", value, "carLicence");
            return (Criteria) this;
        }

        public Criteria andCarLicenceLessThanOrEqualTo(String value) {
            addCriterion("car_licence <=", value, "carLicence");
            return (Criteria) this;
        }

        public Criteria andCarLicenceLike(String value) {
            addCriterion("car_licence like", value, "carLicence");
            return (Criteria) this;
        }

        public Criteria andCarLicenceNotLike(String value) {
            addCriterion("car_licence not like", value, "carLicence");
            return (Criteria) this;
        }

        public Criteria andCarLicenceIn(List<String> values) {
            addCriterion("car_licence in", values, "carLicence");
            return (Criteria) this;
        }

        public Criteria andCarLicenceNotIn(List<String> values) {
            addCriterion("car_licence not in", values, "carLicence");
            return (Criteria) this;
        }

        public Criteria andCarLicenceBetween(String value1, String value2) {
            addCriterion("car_licence between", value1, value2, "carLicence");
            return (Criteria) this;
        }

        public Criteria andCarLicenceNotBetween(String value1, String value2) {
            addCriterion("car_licence not between", value1, value2, "carLicence");
            return (Criteria) this;
        }

        public Criteria andCustomerNameIsNull() {
            addCriterion("customer_name is null");
            return (Criteria) this;
        }

        public Criteria andCustomerNameIsNotNull() {
            addCriterion("customer_name is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerNameEqualTo(String value) {
            addCriterion("customer_name =", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotEqualTo(String value) {
            addCriterion("customer_name <>", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameGreaterThan(String value) {
            addCriterion("customer_name >", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameGreaterThanOrEqualTo(String value) {
            addCriterion("customer_name >=", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameLessThan(String value) {
            addCriterion("customer_name <", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameLessThanOrEqualTo(String value) {
            addCriterion("customer_name <=", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameLike(String value) {
            addCriterion("customer_name like", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotLike(String value) {
            addCriterion("customer_name not like", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameIn(List<String> values) {
            addCriterion("customer_name in", values, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotIn(List<String> values) {
            addCriterion("customer_name not in", values, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameBetween(String value1, String value2) {
            addCriterion("customer_name between", value1, value2, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotBetween(String value1, String value2) {
            addCriterion("customer_name not between", value1, value2, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerTelIsNull() {
            addCriterion("customer_tel is null");
            return (Criteria) this;
        }

        public Criteria andCustomerTelIsNotNull() {
            addCriterion("customer_tel is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerTelEqualTo(String value) {
            addCriterion("customer_tel =", value, "customerTel");
            return (Criteria) this;
        }

        public Criteria andCustomerTelNotEqualTo(String value) {
            addCriterion("customer_tel <>", value, "customerTel");
            return (Criteria) this;
        }

        public Criteria andCustomerTelGreaterThan(String value) {
            addCriterion("customer_tel >", value, "customerTel");
            return (Criteria) this;
        }

        public Criteria andCustomerTelGreaterThanOrEqualTo(String value) {
            addCriterion("customer_tel >=", value, "customerTel");
            return (Criteria) this;
        }

        public Criteria andCustomerTelLessThan(String value) {
            addCriterion("customer_tel <", value, "customerTel");
            return (Criteria) this;
        }

        public Criteria andCustomerTelLessThanOrEqualTo(String value) {
            addCriterion("customer_tel <=", value, "customerTel");
            return (Criteria) this;
        }

        public Criteria andCustomerTelLike(String value) {
            addCriterion("customer_tel like", value, "customerTel");
            return (Criteria) this;
        }

        public Criteria andCustomerTelNotLike(String value) {
            addCriterion("customer_tel not like", value, "customerTel");
            return (Criteria) this;
        }

        public Criteria andCustomerTelIn(List<String> values) {
            addCriterion("customer_tel in", values, "customerTel");
            return (Criteria) this;
        }

        public Criteria andCustomerTelNotIn(List<String> values) {
            addCriterion("customer_tel not in", values, "customerTel");
            return (Criteria) this;
        }

        public Criteria andCustomerTelBetween(String value1, String value2) {
            addCriterion("customer_tel between", value1, value2, "customerTel");
            return (Criteria) this;
        }

        public Criteria andCustomerTelNotBetween(String value1, String value2) {
            addCriterion("customer_tel not between", value1, value2, "customerTel");
            return (Criteria) this;
        }

        public Criteria andFixEmployeeIdIsNull() {
            addCriterion("fix_employee_id is null");
            return (Criteria) this;
        }

        public Criteria andFixEmployeeIdIsNotNull() {
            addCriterion("fix_employee_id is not null");
            return (Criteria) this;
        }

        public Criteria andFixEmployeeIdEqualTo(Integer value) {
            addCriterion("fix_employee_id =", value, "fixEmployeeId");
            return (Criteria) this;
        }

        public Criteria andFixEmployeeIdNotEqualTo(Integer value) {
            addCriterion("fix_employee_id <>", value, "fixEmployeeId");
            return (Criteria) this;
        }

        public Criteria andFixEmployeeIdGreaterThan(Integer value) {
            addCriterion("fix_employee_id >", value, "fixEmployeeId");
            return (Criteria) this;
        }

        public Criteria andFixEmployeeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("fix_employee_id >=", value, "fixEmployeeId");
            return (Criteria) this;
        }

        public Criteria andFixEmployeeIdLessThan(Integer value) {
            addCriterion("fix_employee_id <", value, "fixEmployeeId");
            return (Criteria) this;
        }

        public Criteria andFixEmployeeIdLessThanOrEqualTo(Integer value) {
            addCriterion("fix_employee_id <=", value, "fixEmployeeId");
            return (Criteria) this;
        }

        public Criteria andFixEmployeeIdIn(List<Integer> values) {
            addCriterion("fix_employee_id in", values, "fixEmployeeId");
            return (Criteria) this;
        }

        public Criteria andFixEmployeeIdNotIn(List<Integer> values) {
            addCriterion("fix_employee_id not in", values, "fixEmployeeId");
            return (Criteria) this;
        }

        public Criteria andFixEmployeeIdBetween(Integer value1, Integer value2) {
            addCriterion("fix_employee_id between", value1, value2, "fixEmployeeId");
            return (Criteria) this;
        }

        public Criteria andFixEmployeeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("fix_employee_id not between", value1, value2, "fixEmployeeId");
            return (Criteria) this;
        }

        public Criteria andFixEmployeeNameIsNull() {
            addCriterion("fix_employee_name is null");
            return (Criteria) this;
        }

        public Criteria andFixEmployeeNameIsNotNull() {
            addCriterion("fix_employee_name is not null");
            return (Criteria) this;
        }

        public Criteria andFixEmployeeNameEqualTo(String value) {
            addCriterion("fix_employee_name =", value, "fixEmployeeName");
            return (Criteria) this;
        }

        public Criteria andFixEmployeeNameNotEqualTo(String value) {
            addCriterion("fix_employee_name <>", value, "fixEmployeeName");
            return (Criteria) this;
        }

        public Criteria andFixEmployeeNameGreaterThan(String value) {
            addCriterion("fix_employee_name >", value, "fixEmployeeName");
            return (Criteria) this;
        }

        public Criteria andFixEmployeeNameGreaterThanOrEqualTo(String value) {
            addCriterion("fix_employee_name >=", value, "fixEmployeeName");
            return (Criteria) this;
        }

        public Criteria andFixEmployeeNameLessThan(String value) {
            addCriterion("fix_employee_name <", value, "fixEmployeeName");
            return (Criteria) this;
        }

        public Criteria andFixEmployeeNameLessThanOrEqualTo(String value) {
            addCriterion("fix_employee_name <=", value, "fixEmployeeName");
            return (Criteria) this;
        }

        public Criteria andFixEmployeeNameLike(String value) {
            addCriterion("fix_employee_name like", value, "fixEmployeeName");
            return (Criteria) this;
        }

        public Criteria andFixEmployeeNameNotLike(String value) {
            addCriterion("fix_employee_name not like", value, "fixEmployeeName");
            return (Criteria) this;
        }

        public Criteria andFixEmployeeNameIn(List<String> values) {
            addCriterion("fix_employee_name in", values, "fixEmployeeName");
            return (Criteria) this;
        }

        public Criteria andFixEmployeeNameNotIn(List<String> values) {
            addCriterion("fix_employee_name not in", values, "fixEmployeeName");
            return (Criteria) this;
        }

        public Criteria andFixEmployeeNameBetween(String value1, String value2) {
            addCriterion("fix_employee_name between", value1, value2, "fixEmployeeName");
            return (Criteria) this;
        }

        public Criteria andFixEmployeeNameNotBetween(String value1, String value2) {
            addCriterion("fix_employee_name not between", value1, value2, "fixEmployeeName");
            return (Criteria) this;
        }

        public Criteria andCheckEmployeeIdIsNull() {
            addCriterion("check_employee_id is null");
            return (Criteria) this;
        }

        public Criteria andCheckEmployeeIdIsNotNull() {
            addCriterion("check_employee_id is not null");
            return (Criteria) this;
        }

        public Criteria andCheckEmployeeIdEqualTo(Integer value) {
            addCriterion("check_employee_id =", value, "checkEmployeeId");
            return (Criteria) this;
        }

        public Criteria andCheckEmployeeIdNotEqualTo(Integer value) {
            addCriterion("check_employee_id <>", value, "checkEmployeeId");
            return (Criteria) this;
        }

        public Criteria andCheckEmployeeIdGreaterThan(Integer value) {
            addCriterion("check_employee_id >", value, "checkEmployeeId");
            return (Criteria) this;
        }

        public Criteria andCheckEmployeeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("check_employee_id >=", value, "checkEmployeeId");
            return (Criteria) this;
        }

        public Criteria andCheckEmployeeIdLessThan(Integer value) {
            addCriterion("check_employee_id <", value, "checkEmployeeId");
            return (Criteria) this;
        }

        public Criteria andCheckEmployeeIdLessThanOrEqualTo(Integer value) {
            addCriterion("check_employee_id <=", value, "checkEmployeeId");
            return (Criteria) this;
        }

        public Criteria andCheckEmployeeIdIn(List<Integer> values) {
            addCriterion("check_employee_id in", values, "checkEmployeeId");
            return (Criteria) this;
        }

        public Criteria andCheckEmployeeIdNotIn(List<Integer> values) {
            addCriterion("check_employee_id not in", values, "checkEmployeeId");
            return (Criteria) this;
        }

        public Criteria andCheckEmployeeIdBetween(Integer value1, Integer value2) {
            addCriterion("check_employee_id between", value1, value2, "checkEmployeeId");
            return (Criteria) this;
        }

        public Criteria andCheckEmployeeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("check_employee_id not between", value1, value2, "checkEmployeeId");
            return (Criteria) this;
        }

        public Criteria andCheckEmployeeNameIsNull() {
            addCriterion("check_employee_name is null");
            return (Criteria) this;
        }

        public Criteria andCheckEmployeeNameIsNotNull() {
            addCriterion("check_employee_name is not null");
            return (Criteria) this;
        }

        public Criteria andCheckEmployeeNameEqualTo(String value) {
            addCriterion("check_employee_name =", value, "checkEmployeeName");
            return (Criteria) this;
        }

        public Criteria andCheckEmployeeNameNotEqualTo(String value) {
            addCriterion("check_employee_name <>", value, "checkEmployeeName");
            return (Criteria) this;
        }

        public Criteria andCheckEmployeeNameGreaterThan(String value) {
            addCriterion("check_employee_name >", value, "checkEmployeeName");
            return (Criteria) this;
        }

        public Criteria andCheckEmployeeNameGreaterThanOrEqualTo(String value) {
            addCriterion("check_employee_name >=", value, "checkEmployeeName");
            return (Criteria) this;
        }

        public Criteria andCheckEmployeeNameLessThan(String value) {
            addCriterion("check_employee_name <", value, "checkEmployeeName");
            return (Criteria) this;
        }

        public Criteria andCheckEmployeeNameLessThanOrEqualTo(String value) {
            addCriterion("check_employee_name <=", value, "checkEmployeeName");
            return (Criteria) this;
        }

        public Criteria andCheckEmployeeNameLike(String value) {
            addCriterion("check_employee_name like", value, "checkEmployeeName");
            return (Criteria) this;
        }

        public Criteria andCheckEmployeeNameNotLike(String value) {
            addCriterion("check_employee_name not like", value, "checkEmployeeName");
            return (Criteria) this;
        }

        public Criteria andCheckEmployeeNameIn(List<String> values) {
            addCriterion("check_employee_name in", values, "checkEmployeeName");
            return (Criteria) this;
        }

        public Criteria andCheckEmployeeNameNotIn(List<String> values) {
            addCriterion("check_employee_name not in", values, "checkEmployeeName");
            return (Criteria) this;
        }

        public Criteria andCheckEmployeeNameBetween(String value1, String value2) {
            addCriterion("check_employee_name between", value1, value2, "checkEmployeeName");
            return (Criteria) this;
        }

        public Criteria andCheckEmployeeNameNotBetween(String value1, String value2) {
            addCriterion("check_employee_name not between", value1, value2, "checkEmployeeName");
            return (Criteria) this;
        }
    }

    /**
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}