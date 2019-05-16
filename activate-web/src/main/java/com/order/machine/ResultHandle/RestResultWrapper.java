package com.order.machine.ResultHandle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.order.machine.model.RestResult;
import com.order.machine.model.ReturnCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @Description 返回Rest风格的数据
 * @Author cjy
 * @Date 2019/2/14
 **/
@ControllerAdvice(basePackages = "com.order.machine.controller")
public class RestResultWrapper implements ResponseBodyAdvice<Object> {

    /**
     * 判断哪些需要拦截
     *
     * @param returnType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        RestResult result = new RestResult(true, ReturnCode.SystemCode.SYS_SUCCESS.getValue(), body, null);
        if (body instanceof String && StringUtils.startsWith(String.valueOf(body), "{")) {
            JSONObject jsonObject = JSONObject.parseObject(String.valueOf(body));
            result.setData(jsonObject);
        }
        return JSON.toJSONString(result);
    }

}