package com.order.machine.po;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author miou
 * @date 2019-04-13
 */
@Table(name="tb_user")
public class UserPo {

    @Id
    private String id;
    private String userId;
    private String userName;
    private String password;
    private String salt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
