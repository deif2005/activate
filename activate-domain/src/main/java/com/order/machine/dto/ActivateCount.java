package com.order.machine.dto;

/**
 * @author miou
 * @date 2019-06-11
 */
public class ActivateCount {
    private String orderId;
    private String chipSn;
    private String activateTimes;
    private String updateTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getChipSn() {
        return chipSn;
    }

    public void setChipSn(String chipSn) {
        this.chipSn = chipSn;
    }

    public String getActivateTimes() {
        return activateTimes;
    }

    public void setActivateTimes(String activateTimes) {
        this.activateTimes = activateTimes;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
