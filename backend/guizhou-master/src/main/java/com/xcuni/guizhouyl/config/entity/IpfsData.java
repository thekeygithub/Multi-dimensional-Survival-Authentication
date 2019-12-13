package com.xcuni.guizhouyl.config.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "ipfs")
@Configuration
public class IpfsData {
    private String address;
}
