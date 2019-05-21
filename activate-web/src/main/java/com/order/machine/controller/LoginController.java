package com.order.machine.controller;

import com.alibaba.fastjson.JSON;
import com.order.machine.common_const.CommonConst;
import com.order.machine.dto.LoginInfo;
import com.order.machine.po.UserPo;
import com.order.machine.redis.RedisConstants;
import com.order.machine.redis.RedisUtil;
import com.order.machine.service.IUserService;
import com.wd.util.DateUtil;
import com.wd.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * @author miou
 * @date 2019-04-13
 */
@RestController
public class LoginController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    IUserService userService;
    @Autowired
    RedisUtil redisUtil;

    /**
     * 登录
     * @param userId
     * @param password
     * @return
     */
    @PostMapping(value = "login")
    public String login(@RequestParam("userId") String userId,
                        @RequestParam("password") String password){
        String result;
        UserPo userPo = new UserPo();
        userPo.setUserId(userId);
        userPo.setPassword(password);
        UserPo rtUser = userService.verifyUser(userPo);
        if (!redisUtil.hasKey(String.format(RedisConstants.LOGIN_TOKEN,userId))){
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setUserId(userId);
            loginInfo.setCompanyId(rtUser.getCompanyId());
            loginInfo.setToken(UUID.randomUUID().toString());
            loginInfo.setLoginTime(DateUtil.getDateTime());
            String loginStr = JSON.toJSONString(loginInfo);
            //记录用户是否登录，解决重复登录问题
            redisUtil.set(String.format(RedisConstants.LOGIN_TOKEN,userId),loginInfo.getToken());
            //记录用户登录信息
            redisUtil.set(String.format(RedisConstants.LOGIN_INFO,loginInfo.getToken()),loginStr);
            result = loginStr;
        }else {//如果已经登录过，直接返回
            String token = String.valueOf(redisUtil.get(String.format(RedisConstants.LOGIN_TOKEN,userId)));
            result = String.valueOf(redisUtil.get(String.format(RedisConstants.LOGIN_INFO,token)));
        }
        return result;
    }

    /**
     * 注册
     * @param userId
     * @param userName
     * @param password
     * @return
     */
    @PostMapping(value = "register")
    public String registerUser(@RequestParam("userId") String userId,
                               @RequestParam("userName") String userName,
                               @RequestParam("password") String password,
                               @RequestParam("companyId") String companyId){
        userService.registerUser(userId,companyId,userName,password);
        return "";
    }

    /**
     * 注销
     * @param request
     * @return
     */
    @PostMapping(value = "logout")
    public String logout(HttpServletRequest request){
        String token = request.getParameter("token");
        LoginInfo loginInfo = JSON.parseObject((String) redisUtil.get(String.format(RedisConstants.LOGIN_INFO,token)),
                LoginInfo.class) ;
        String userId = loginInfo.getUserId();
        redisUtil.del(String.format(RedisConstants.LOGIN_TOKEN,userId));
        redisUtil.del(String.format(RedisConstants.LOGIN_INFO,token));
        return "注销成功";
    }
}
