package com.order.machine.po;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author miou
 * @date 2019-04-17
 */
@Table(name = "tb_order_config")
public class OrderConfigPo {

    @Id
    private String id;
    private String companyId;
    private String orderId;
    private Integer licenceCount;
    private String key1;
    private Integer activateCount;
    private String isClose;
    private String status;
    private String orderDate;
    private String createTime;

    public OrderConfigPo(){
    }

    public OrderConfigPo(String orderId, Integer licenceCount, String key1, String key2){
        this.orderId=orderId;
        this.licenceCount=licenceCount;
        this.key1=key1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getLicenceCount() {
        return licenceCount;
    }

    public void setLicenceCount(Integer licenceCount) {
        this.licenceCount = licenceCount;
    }

    public Integer getActivateCount() {
        return activateCount;
    }

    public void setActivateCount(Integer activateCount) {
        this.activateCount = activateCount;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public String getIsClose() {
        return isClose;
    }

    public void setIsClose(String isClose) {
        this.isClose = isClose;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
