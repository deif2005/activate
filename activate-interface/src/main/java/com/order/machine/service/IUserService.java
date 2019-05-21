package com.order.machine.service;


import com.order.machine.po.UserPo;

/**
 * @author miou
 * @date 2019-04-16
 */
public interface IUserService {

    UserPo verifyUser(UserPo userPo);

    void registerUser(String userId, String companyId, String userName, String password);
}
