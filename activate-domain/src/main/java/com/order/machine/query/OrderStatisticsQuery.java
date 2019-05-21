package com.order.machine.query;

import com.order.machine.base.Page;

/**
 * @author miou
 * @date 2019-05-21
 */
public class OrderStatisticsQuery extends Page {
    private String companyId;
    private String orderDateBegin;
    private String orderDateEnd;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getOrderDateBegin() {
        return orderDateBegin;
    }

    public void setOrderDateBegin(String orderDateBegin) {
        this.orderDateBegin = orderDateBegin;
    }

    public String getOrderDateEnd() {
        return orderDateEnd;
    }

    public void setOrderDateEnd(String orderDateEnd) {
        this.orderDateEnd = orderDateEnd;
    }
}
