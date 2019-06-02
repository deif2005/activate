package com.order.machine.controller;

import com.alibaba.fastjson.JSON;
import com.order.machine.ResultHandle.NoRestReturn;
import com.order.machine.common_const.CommonConst;
import com.order.machine.dto.LoginInfo;
import com.order.machine.mapper.UserMapper;
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
import java.util.HashSet;
import java.util.Iterator;
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
     * @param userName
     * @param password
     * @return
     */
    @PostMapping(value = "userLogin")
//    @NoRestReturn
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password){
        LoginInfo result=null;
        HashSet<String> keySet;
        String key=null;
        UserPo userPo = new UserPo();
        userPo.setUserName(userName);
        userPo.setPassword(password);
        UserPo rtUser = userService.verifyUser(userPo);
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserName(userName);
        loginInfo.setLoginTime(DateUtil.getDateTime());
        keySet = redisUtil.getKeys(String.format(RedisConstants.LOGIN_INFO,userName,"*"));
        Iterator iterator = keySet.iterator();
        while (iterator.hasNext()) {
            key = String.valueOf(iterator.next());
        }
//        redisUtil.hasKey(key);
        if (rtUser.getIsLogin().equals("0")){
            loginInfo.setToken(UUID.randomUUID().toString());
            rtUser.setIsLogin("1");
            userService.updateUser(rtUser);
            //记录用户是否登录，解决重复登录问题
//            redisUtil.set(String.format(RedisConstants.LOGIN_TOKEN,userName),loginInfo.getToken(),
//                    CommonConst.LOGININFO_EXPIRED);
            //记录用户登录信息
            redisUtil.set(String.format(RedisConstants.LOGIN_INFO,userName,loginInfo.getToken()),JSON.toJSONString(loginInfo),
                    CommonConst.LOGININFO_EXPIRED);
            result = loginInfo;
        }else if (rtUser.getIsLogin().equals("1")){//如果已经登录过
            if (key != null && redisUtil.hasKey(key)){
                redisUtil.expire(key,CommonConst.LOGININFO_EXPIRED);
                result = JSON.parseObject((String) redisUtil.get(key),LoginInfo.class);
            }else {
                loginInfo.setToken(UUID.randomUUID().toString());
                userService.updateUser(rtUser);
                redisUtil.set(String.format(RedisConstants.LOGIN_INFO,userName,loginInfo.getToken()),JSON.toJSONString(loginInfo),
                        CommonConst.LOGININFO_EXPIRED);
                result = loginInfo;
            }
        }
        return JSON.toJSONString(result);
    }

    /**
     * 注册
     * @param userName
     * @param password
     * @return
     */
    @PostMapping(value = "register")
    public String registerUser(@RequestParam("userName") String userName,
                               @RequestParam("password") String password){
        userService.registerUser(userName,password);
        return "";
    }

    /**
     * 注销
     * @param request
     * @return
     */
    @PostMapping(value = "logout")
    public String logout(HttpServletRequest request){
        String token = request.getHeader("token");
        String userName = request.getHeader("userName");
//        LoginInfo loginInfo = JSON.parseObject((String) redisUtil.get(String.format(RedisConstants.LOGIN_INFO,userName,token)),
//                LoginInfo.class) ;
        UserPo userPo = new UserPo();
        userPo.setUserName(userName);
        userPo.setIsLogin("0");
        userService.updateUser(userPo);
//        redisUtil.del(String.format(RedisConstants.LOGIN_TOKEN,userName));
        redisUtil.del(String.format(RedisConstants.LOGIN_INFO,userName,token));
        return "注销成功";
    }
}
