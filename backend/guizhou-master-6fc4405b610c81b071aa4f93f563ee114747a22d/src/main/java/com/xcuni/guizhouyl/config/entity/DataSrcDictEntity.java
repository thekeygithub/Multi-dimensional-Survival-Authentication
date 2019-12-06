package com.xcuni.guizhouyl.config.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class DataSrcDictEntity {
    private String dataSrcCode;
    private String dataSrcName;
    private List<DataSrcItemEntity> dataList = new ArrayList<>();

    //build from json config
    private Map<String, DataSrcItemEntity> dataSrcItemMap = new HashMap<>();

    public void addToDataSrcItemMap(String itemId, DataSrcItemEntity entity) {
        dataSrcItemMap.put(itemId, entity);
    }

    public DataSrcItemEntity getDataSrcItemEntityById(String dataItemId) {
        if (dataSrcItemMap.containsKey(dataItemId))
            return dataSrcItemMap.get(dataItemId);
        return null;
    }
}
