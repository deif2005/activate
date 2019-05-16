package com.order.machine.Interceptor;

import com.google.common.base.Strings;
import com.order.machine.common_const.CommonEnum;
import com.order.machine.exception.LogicException;
import com.order.machine.po.UserPo;
import com.order.machine.redis.RedisCommons;
import com.order.machine.redis.RedisConstants;
import com.order.machine.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author miou
 * @date 2019-04-13
 * Spring拦截器
 * HandlerInterceptorAdapter需要继承，HandlerInterceptor需要实现
 * 可以作为日志记录和登录校验来使用
 * 建议使用HandlerInterceptorAdapter，因为可以按需进行方法的覆盖。
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    RedisUtil redisUtil;

    /**
     * 拦截于请求刚进入时，进行判断，需要boolean返回值，如果返回true将继续执行，
     * 如果返回false，将不进行执行。一般用于登录校验。
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
//        request.getCookies();
        if (null == token)
            throw LogicException.le(CommonEnum.ReturnCode.SystemCode.sys_err_tokeninvalid.getValue(),
                    "非法请求");
        if (null == redisUtil.get(String.format(RedisConstants.LOGIN_INFO, request.getHeader("token")))){
            throw LogicException.le(CommonEnum.ReturnCode.UserLoginCode.user_login_overdue_error.getValue(),
                    "登录信息已过期");
        }
        if (request.getHeader("token") == null){
            throw LogicException.le(CommonEnum.ReturnCode.SystemCode.sys_err_tokeninvalid.getValue(),
                    CommonEnum.ReturnMsg.SystemMsg.sys_err_tokeninvalid.getValue());
        }
        super.preHandle(request,response,handler);
        return true;
    }

    /**
     * 请求结束执行
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 视图渲染完成后执行
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
