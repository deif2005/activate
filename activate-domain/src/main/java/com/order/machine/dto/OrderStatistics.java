package com.order.machine.dto;

/**
 * @author miou
 * @date 2019-05-21
 */
public class OrderStatistics {
    private String companyId;
    private String companyName;
    private String orderCount;    //订单笔数
    private String licenceCount;  //订单预计激活数
    private String activateCount; //已激活数


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

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public String getLicenceCount() {
        return licenceCount;
    }

    public void setLicenceCount(String licenceCount) {
        this.licenceCount = licenceCount;
    }

    public String getActivateCount() {
        return activateCount;
    }

    public void setActivateCount(String activateCount) {
        this.activateCount = activateCount;
    }
}
