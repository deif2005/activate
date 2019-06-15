package com.order.machine.dto;

/**
 * @author miou
 * @date 2019-06-11
 */
public class ActivateCountTotal {

    private String companyId;
    private String companyName;
    private String orderId;
    private String licenceCount;
    private String activateCount;
    private String orderDate;
    private String oneTimes;
    private String twoTimes;
    private String threeTimes;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getLicenceCount() {
        return licenceCount;
    }

    public void setLicenceCount(String licenceCount) {
        this.licenceCount = licenceCount;
    }

    public String getOneTimes() {
        return oneTimes;
    }

    public void setOneTimes(String oneTimes) {
        this.oneTimes = oneTimes;
    }

    public String getTwoTimes() {
        return twoTimes;
    }

    public void setTwoTimes(String twoTimes) {
        this.twoTimes = twoTimes;
    }

    public String getThreeTimes() {
        return threeTimes;
    }

    public void setThreeTimes(String threeTimes) {
        this.threeTimes = threeTimes;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getActivateCount() {
        return activateCount;
    }

    public void setActivateCount(String activateCount) {
        this.activateCount = activateCount;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
