package com.order.machine.query;


import com.order.machine.base.Page;

/**
 * @author miou
 * @date 2019-04-18
 */
public class ActivateMachineQuery extends Page {

    private String orderId;
    private String beginDate;
    private String endDate;
    private Integer activateTimes;
//    private Integer activateTimesCount;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

//    public Integer getActivateTimesCount() {
//        return activateTimesCount;
//    }
//
//    public void setActivateTimesCount(Integer activateTimesCount) {
//        this.activateTimesCount = activateTimesCount;
//    }

    public Integer getActivateTimes() {
        return activateTimes;
    }

    public void setActivateTimes(Integer activateTimes) {
        this.activateTimes = activateTimes;
    }
}
