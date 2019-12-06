package com.xcuni.guizhouyl.model;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class StatisticalInfo {
    private String departmentId;
    private String departmentName;
    private int flag;
    private int Num;

}
