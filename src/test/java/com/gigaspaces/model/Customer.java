package com.gigaspaces.model;

import com.gigaspaces.annotation.pojo.SpaceId;

/**
 * Created by kobi on 12/10/14.
 */
public class Customer {
    private String customerCode;
    private String customerName;
    private String customerCity;
    private String workingArea;
    private String customerCountry;
    private Integer grade;
    private Integer openingAmt;
    private Integer receiveAmt;
    private Integer paymentAmt;
    private Integer outstandingAmt;
    private String phoneNo;
    private String agentCode;

    @SpaceId
    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getWorkingArea() {
        return workingArea;
    }

    public void setWorkingArea(String workingArea) {
        this.workingArea = workingArea;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getOpeningAmt() {
        return openingAmt;
    }

    public void setOpeningAmt(Integer openingAmt) {
        this.openingAmt = openingAmt;
    }

    public Integer getReceiveAmt() {
        return receiveAmt;
    }

    public void setReceiveAmt(Integer receiveAmt) {
        this.receiveAmt = receiveAmt;
    }

    public Integer getPaymentAmt() {
        return paymentAmt;
    }

    public void setPaymentAmt(Integer paymentAmt) {
        this.paymentAmt = paymentAmt;
    }

    public Integer getOutstandingAmt() {
        return outstandingAmt;
    }

    public void setOutstandingAmt(Integer outstandingAmt) {
        this.outstandingAmt = outstandingAmt;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }
}
