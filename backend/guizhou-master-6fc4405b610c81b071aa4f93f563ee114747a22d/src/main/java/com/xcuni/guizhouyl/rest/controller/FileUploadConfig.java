package com.xcuni.guizhouyl.rest.controller;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

@Configuration
public class FileUploadConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement(
            ) {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 单个文件最大
        factory.setMaxFileSize("40000MB");
        // 设置总上传数据总大小
        factory.setMaxRequestSize("40000MB");
        return factory.createMultipartConfig();
    }

}
