package com.order.machine.dto;

/**
 * @author miou
 * @date 2019-06-05
 */
public class OrderTimesCount {
    private String orderId;
    private String activateTimes;
    private String activateTimesCount; //激活次数统计

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getActivateTimes() {
        return activateTimes;
    }

    public void setActivateTimes(String activateTimes) {
        this.activateTimes = activateTimes;
    }

    public String getActivateTimesCount() {
        return activateTimesCount;
    }

    public void setActivateTimesCount(String activateTimesCount) {
        this.activateTimesCount = activateTimesCount;
    }
}
