package com.order.machine.dto;

/**
 * @author miou
 * @date 2019-06-11
 */
public class ActivateCountTotal {

    private String orderId;
    private String licenceCount;
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
}
