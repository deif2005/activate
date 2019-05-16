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
    private String orderId;
    private String licenceCount;
    private String licenceKey;
    private String activateCount;
    private String isClose;
    private String status;
    private String salt;
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getActivateCount() {
        return activateCount;
    }

    public void setActivateCount(String activateCount) {
        this.activateCount = activateCount;
    }

    public String getLicenceKey() {
        return licenceKey;
    }

    public void setLicenceKey(String licenceKey) {
        this.licenceKey = licenceKey;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
