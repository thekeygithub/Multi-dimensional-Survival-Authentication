package com.xcuni.guizhouyl.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class traceInfo {


    private String departmentId;
    private String departmentName;
    private String flag;
    private String eventTime;
    private String dataHash;
    private String dataOwns;
    private String txId;
    private String dataType;

    public traceInfo(){

    }

    public traceInfo(String departmentId,String departmentName,String flag,String eventTime,String dataHash,String dataOwns,String txId,String dataType){
        this.departmentId=departmentId;
        this.departmentName=departmentName;
        this.flag=flag;
        this.eventTime=eventTime;
        this.dataHash=dataHash;
        this.dataOwns=dataOwns;
        this.txId=txId;
        this.dataType=dataType;
    }
}
