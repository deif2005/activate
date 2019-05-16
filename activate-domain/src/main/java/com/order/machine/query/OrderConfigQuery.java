package com.order.machine.query;


import com.order.machine.base.Page;

/**
 * @author miou
 * @date 2019-04-17
 */
public class OrderConfigQuery extends Page {

    private String beginDate;
    private String endDate;
    private String isClose;

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

    public String getIsClose() {
        return isClose;
    }

    public void setIsClose(String isClose) {
        this.isClose = isClose;
    }
}
