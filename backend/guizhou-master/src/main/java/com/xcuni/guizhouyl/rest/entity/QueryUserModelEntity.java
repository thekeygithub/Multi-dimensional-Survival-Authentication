package com.xcuni.guizhouyl.rest.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class QueryUserModelEntity {
    //    private String channelName;
//    private String chaincodeName;
    //@NotBlank(message = "请输入手机号")
    private String name;
    @NotBlank(message = "请输入身份证号")
    private String id;
}
