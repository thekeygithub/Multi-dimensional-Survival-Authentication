package com.xcuni.guizhouyl.config.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LocationDictEntity {
    private String city;
    private String code;
    private List<CountyDictEntity> countyList = new ArrayList<>();

    public void addCounty(CountyDictEntity entity) {
        countyList.add(entity);
    }
}
