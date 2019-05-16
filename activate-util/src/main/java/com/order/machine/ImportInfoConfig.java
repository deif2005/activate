package com.order.machine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ImportInfoConfig {

    @Autowired
    private Environment environment;

//    @Value("${import.package.path}")
    private String ResourceImportPath;

    private long MaxUploadSize;


    public String getResourceImportPath() {
        ResourceImportPath = environment.getProperty("upload.path");
        return ResourceImportPath;
    }

    public long getMaxUploadSize(){
        MaxUploadSize = Long.valueOf(environment.getProperty("upload.maxuploadsize"));
        return MaxUploadSize;
    }
}
