package com.xcuni.guizhouyl.rest.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TargetDataEntity {
    @JSONField(name = "DataSrc")
    @JsonProperty("DataSrc")
    private String DataSrc;
    //private String DataSrcName;
    @JSONField(name = "DataList")
    @JsonProperty("DataList")
    private List<TargetDataItemEntity> DataList = new ArrayList<>();
}
