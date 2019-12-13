package com.xcuni.guizhouyl.rest.entity;

import lombok.Data;

@Data
public class FetchUserInfoPageEntity {
    private int pageNo;//当前页号
    private int totalNum;//总页数
    private int pageItems;//每页个数
}
