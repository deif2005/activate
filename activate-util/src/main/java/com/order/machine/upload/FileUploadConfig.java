package com.order.machine.upload;


import com.order.machine.ImportInfoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author miou
 * @date 2019-04-12
 */
@Configuration
@AutoConfigureAfter(ImportInfoConfig.class)
public class FileUploadConfig {

    @Autowired
    private ImportInfoConfig importInfoConfig;

    @Bean
    public MultipartResolver multipartResolver() {
        long maxUploadSize = importInfoConfig.getMaxUploadSize();
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        //上传文件大小 50M=50*1024*1024
        resolver.setMaxUploadSize(maxUploadSize);
        //低于此值，只保留在内存里，超过此阈值，生成硬盘上的临时文件，默认是1024
        resolver.setMaxInMemorySize(4096);
        resolver.setDefaultEncoding("UTF-8");
        //resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
        resolver.setResolveLazily(true);
        return resolver;
    }
}
