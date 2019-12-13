package com.xcuni.guizhouyl.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Data
//@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "fabric")
@Configuration
public class ChaincodeData {
    private String name;
    private String path;
    private String version;
}
