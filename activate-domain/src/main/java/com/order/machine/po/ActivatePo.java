package com.order.machine.po;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author miou
 * @date 2019-04-17
 */
@Table(name="tb_activate")
public class ActivatePo {

    @Id
    private String id;
    private String orderId;
    private String chipSn;
    private String updateTime;
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

    public String getChipSn() {
        return chipSn;
    }

    public void setChipSn(String chipSn) {
        this.chipSn = chipSn;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
