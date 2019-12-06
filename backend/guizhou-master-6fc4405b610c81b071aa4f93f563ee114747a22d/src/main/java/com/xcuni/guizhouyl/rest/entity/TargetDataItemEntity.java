package com.xcuni.guizhouyl.rest.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TargetDataItemEntity {
    @JSONField(name = "DataId")
    @JsonProperty("DataId")
    private String DataId;
}
