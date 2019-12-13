package com.xcuni.guizhouyl.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class UserInfo {
    private int id;
    private String name;
    private int age;

    public UserInfo(){

    }
    public UserInfo(int id ,String name ,int age){
        this.id=id;
        this.name=name;
        this.age=age;
    }
}
