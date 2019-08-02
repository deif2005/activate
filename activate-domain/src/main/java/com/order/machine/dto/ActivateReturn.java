package com.order.machine.dto;

/**
 * @author miou
 * @date 2019-08-02
 * 激活返回数据
 */
public class ActivateReturn {
    private String key;
    private Integer licenseCount;
    private Integer activateCount;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getLicenseCount() {
        return licenseCount;
    }

    public void setLicenseCount(Integer licenseCount) {
        this.licenseCount = licenseCount;
    }

    public Integer getActivateCount() {
        return activateCount;
    }

    public void setActivateCount(Integer activateCount) {
        this.activateCount = activateCount;
    }
}
