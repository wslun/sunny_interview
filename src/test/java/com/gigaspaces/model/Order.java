package com.gigaspaces.model;

import com.gigaspaces.annotation.pojo.SpaceId;

import java.util.Date;

/**
 * Created by kobi on 12/10/14.
 */
public class Order {
    private Integer orderNum;
    private Integer orderAmount;
    private Integer advanceAmount;
    private Date orderDate;
    private String customerCode;
    private String agentCode;
    private String orderDescription;

    @SpaceId
    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getAdvanceAmount() {
        return advanceAmount;
    }

    public void setAdvanceAmount(Integer advanceAmount) {
        this.advanceAmount = advanceAmount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }
}
