package com.xcuni.guizhouyl.data.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class YanglaoUserDataSrcEntity {
    private String DataSrc;
    private List<YanglaoDataSrcItemEntity> DataList = new ArrayList<>();
}
