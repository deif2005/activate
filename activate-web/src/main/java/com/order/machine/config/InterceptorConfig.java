package com.order.machine.config;

import com.order.machine.Interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author miou
 * @date 2019-04-13
 */
//WebMvcConfigurerAdapter已经过时
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    LoginInterceptor loginInterceptor;

    final String[] notLoginInterceptPaths = {
            "/login/**",
            "/index/**",
            "/register/**",
            "/order/v1/activateMachine"
//            "/order/**"
    };

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(responseBodyConverter());
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns(notLoginInterceptPaths);
        super.addInterceptors(registry);
    }
}
