package com.xcuni.guizhouyl.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
//@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "yanglao")
@Configuration
public class DepartmentData {

    private List<Department> departments;
    public Department getDepartmentData(String departmentName){ //}, String version) {
        if(departments == null || departments.size() < 1)
            return null;
        for(Department data : departments){
            System.out.println(data.getName());
            if(data.getName().equals(departmentName)){
                // && data.getVersion().equals(version)){
                return data;
            }
        }
        return null;
    }
}
