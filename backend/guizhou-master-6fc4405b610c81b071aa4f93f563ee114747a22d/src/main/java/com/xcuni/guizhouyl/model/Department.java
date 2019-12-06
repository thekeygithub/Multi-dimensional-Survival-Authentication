package com.xcuni.guizhouyl.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Data
//@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "yanglao")
@Configuration
public class Department {
    private String name;
    private String id;
    private String strKey;
}
