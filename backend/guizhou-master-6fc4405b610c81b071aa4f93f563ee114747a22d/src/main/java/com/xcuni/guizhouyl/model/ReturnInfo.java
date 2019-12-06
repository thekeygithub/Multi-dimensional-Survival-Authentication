package com.xcuni.guizhouyl.model;

import org.springframework.stereotype.Component;
import lombok.Data;
@Data
@Component
public class ReturnInfo {

    private String name;
    private int uploadNum;
    private int donwloadNum;
    private String proportion;//交易比例
}
