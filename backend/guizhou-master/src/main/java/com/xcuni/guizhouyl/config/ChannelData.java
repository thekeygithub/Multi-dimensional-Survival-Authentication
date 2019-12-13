package com.xcuni.guizhouyl.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
//@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "fabric")
@Configuration
public class ChannelData {
    private String channelName;
    private List<ChaincodeData> chaincodes;

    //在一个channel中，一个chaincode不允许同时存在两个版本
    public ChaincodeData getChaincodeData(String chaincodeName) { //}, String version) {
        if (chaincodes == null || chaincodes.size() < 1)
            return null;
        for (ChaincodeData data : chaincodes) {
            if (data.getName().equals(chaincodeName)) {
                // && data.getVersion().equals(version)){
                return data;
            }
        }
        return null;
    }
}

