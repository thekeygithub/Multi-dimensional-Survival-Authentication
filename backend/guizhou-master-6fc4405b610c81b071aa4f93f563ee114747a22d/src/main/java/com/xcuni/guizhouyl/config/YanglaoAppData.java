package com.xcuni.guizhouyl.config;

import com.xcuni.guizhouyl.model.Department;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
//@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "yanglao")
@Configuration
public class YanglaoAppData {
    private String getUserListUrl;
    private String getUserDataUrl;
    private int producerThreadpoolSize;
    private int verificationThreadNum;
    private String yanglaoChannelName;
    private String dataRecordChaincode;
    private String recordFuncName;
    private String queryChaincode;
    private String queryFuncName;
    private String yanglaoConfigRootPath;
    private String raceDictPath;
    private String execDatePath;
    private boolean enableScheduleJob;
    private String locationDictPath;
    private String dataSrcDictPath;
    private String outputStatusDictPath;
    private String statusDescDictPath;
    private String targetDataListPath;
    private int yanglaoAppType;
    private String yanglaoVerifyUrl;
    private String fabricVerifyUrl;
    private String fabricQueryStatusUrl;
    private String fabricCommonInvokeUrl;
    private String fabricCommonQueryUrl;
    private String fabricRecordCallnumUrl;
    private String dataChaincode;
    private List<Department> departments;
    public Department getDepartmentData(String departmentName){ //}, String version) {
        if(departments == null || departments.size() < 1)
            return null;
        for(Department data : departments){

            if(data.getName().equals(departmentName)){
                // && data.getVersion().equals(version)){
                return data;
            }
        }
        return null;
    }
}
