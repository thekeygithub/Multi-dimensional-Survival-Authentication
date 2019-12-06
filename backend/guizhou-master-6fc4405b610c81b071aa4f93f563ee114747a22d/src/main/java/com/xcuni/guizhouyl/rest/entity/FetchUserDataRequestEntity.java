package com.xcuni.guizhouyl.rest.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class FetchUserDataRequestEntity {
    @JSONField(name = "UserInfo")
    @JsonProperty("UserInfo")
    private FetchDataUserInfoEntity UserInfo; //id  name
    @JSONField(name = "TargetDataList")
    @JsonProperty("TargetDataList")
    private List<TargetDataEntity> TargetDataList; //dateSrc
    @JSONField(name = "Date")
    @JsonProperty("Date")
    private String Date;
}
