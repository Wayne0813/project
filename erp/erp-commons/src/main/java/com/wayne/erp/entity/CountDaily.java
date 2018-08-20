package com.wayne.erp.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class CountDaily implements Serializable {
    private Integer id;

    private Integer sumNum;

    private Long sumMoney;

    private String dateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSumNum() {
        return sumNum;
    }

    public void setSumNum(Integer sumNum) {
        this.sumNum = sumNum;
    }

    public Long getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(Long sumMoney) {
        this.sumMoney = sumMoney;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        CountDaily other = (CountDaily) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSumNum() == null ? other.getSumNum() == null : this.getSumNum().equals(other.getSumNum()))
            && (this.getSumMoney() == null ? other.getSumMoney() == null : this.getSumMoney().equals(other.getSumMoney()))
            && (this.getDateTime() == null ? other.getDateTime() == null : this.getDateTime().equals(other.getDateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSumNum() == null) ? 0 : getSumNum().hashCode());
        result = prime * result + ((getSumMoney() == null) ? 0 : getSumMoney().hashCode());
        result = prime * result + ((getDateTime() == null) ? 0 : getDateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", sumNum=").append(sumNum);
        sb.append(", sumMoney=").append(sumMoney);
        sb.append(", dateTime=").append(dateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}