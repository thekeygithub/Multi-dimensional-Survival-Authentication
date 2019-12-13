package com.xcuni.guizhouyl.data.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xcuni.guizhouyl.config.*;
import com.xcuni.guizhouyl.config.entity.*;
import com.xcuni.guizhouyl.rest.entity.TargetDataEntity;
import com.xcuni.guizhouyl.utils.JsonUtils;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.URL;
import java.util.*;

@Component
@Data
public class DataDictService {
    final static Logger LOGGER = LoggerFactory.getLogger(DataDictService.class);
    @Autowired
    private YanglaoAppData yanglaoAppData;

    final private static String GUIZHOU_CODE = "5200";
    final private static String GUIZHOU_CODE_SIMP = "52";
    final private static String GUIZHOU_CODE_LONG = "520000";

    //民族字典map
    private HashMap<String, String> raceIdToNameMap = new HashMap<>();
    private HashMap<String, String> raceNameToIdMap = new HashMap<>();
    //地址字典
    private List<LocationDictEntity> locationList = new ArrayList<>();
    private List<String> cityLocationIdList = new ArrayList<>();
    private HashMap<String, LocationDictEntity> cityCodeToEntity = new HashMap<>();//<"520100",{贵阳:[countyList]}>
    private HashMap<String, String> locCodeToName = new HashMap<>(); //<"520102",南明区>
    //数据源字典
    private List<DataSrcDictEntity> dataSrcList = new ArrayList<>();
    private HashMap<String, DataSrcDictEntity> dataCodeToEntity = new HashMap<>();
    private HashMap<String, String> dataCodeToName = new HashMap<>();
    //输出状态字典
    private Map<String, String> outputStatusMap = new HashMap<>();
    //输出状态说明字典
    private Map<String, String> statusDescriptionMap = new HashMap<>();
    //目的数据源
    private List<TargetDataEntity> targetDataEntityList = new ArrayList<>();

    //初始化民族词典
    public void readRaceDictConfig() throws RuntimeException {
        String path = yanglaoAppData.getYanglaoConfigRootPath() + yanglaoAppData.getRaceDictPath();
        if (path.isEmpty())
            throw new RuntimeException("请检查民族字典配置！");
        readRaceDictConfig(path);
    }

    public void readRaceDictConfig(String path) throws RuntimeException {
        try {
            String jsStr = readStrFromFile(path);
            if (!jsStr.equals("")) {
                JSONObject jsObj = JSONObject.parseObject(jsStr);
                JSONArray accList = jsObj.getJSONArray("raceList");
                List<RaceDictEntity> dataList = JsonUtils.fromJsonToList(accList.toJSONString(), RaceDictEntity.class);
                for (RaceDictEntity entity : dataList) {
                    raceIdToNameMap.put(entity.getId(), entity.getName());
                    raceNameToIdMap.put(entity.getName(), entity.getId());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("初始化民族字典失败！" + e.getMessage());
        }
    }

    public String getRaceNameById(String id) {
        if (raceIdToNameMap.containsKey(id))
            return raceIdToNameMap.get(id);
        return null;
    }

    public String getRaceIdbyName(String name) {
        if (raceNameToIdMap.containsKey(name))
            return raceNameToIdMap.get(name);
        return null;
    }

    //初始化地址词典
    public void readLocationDictConfig() {
        String path = yanglaoAppData.getYanglaoConfigRootPath() + yanglaoAppData.getLocationDictPath();
        if (path.isEmpty())
            throw new RuntimeException("请检查地址字典配置！");
        readLocationDictConfig(path);
    }

    public void readLocationDictConfig(String path) {
        try {
            String jsStr = readStrFromFile(path);
            if (!jsStr.equals("")) {
                //JSONArray jsonArray = JSONObject.parseArray(jsStr);
                locationList = JsonUtils.fromJsonToList(jsStr, LocationDictEntity.class);
                for (LocationDictEntity entity : locationList) {
                    String city = entity.getCity();
                    String code = entity.getCode();
                    cityCodeToEntity.put(code, entity);
                    locCodeToName.put(code, city);
                    cityLocationIdList.add(code);
                    List<CountyDictEntity> countyList = entity.getCountyList();
                    for (CountyDictEntity countyDictEntity : countyList) {
                        locCodeToName.put(countyDictEntity.getCode(), countyDictEntity.getCounty());
                    }
                }
                //最后将贵州省存入map
                locCodeToName.put(GUIZHOU_CODE, "贵州省");
                locCodeToName.put(GUIZHOU_CODE_SIMP, "贵州省");
                locCodeToName.put(GUIZHOU_CODE_LONG, "贵州省");
            }
        } catch (Exception e) {
            throw new RuntimeException("初始化地址字典失败！" + e.getMessage());
        }
    }

    public boolean isCity(String locationId) {
        return cityCodeToEntity.containsKey(locationId);
    }

    public boolean isValidLocationId(String locationId) {
        return locCodeToName.containsKey(locationId);
    }

    public List<String> getLocationIdList(String locationId) {

        //如果是贵州省id，则返回所有城市的id
        if (locationId.equals(GUIZHOU_CODE) || locationId.equals(GUIZHOU_CODE_SIMP) || locationId.equals(GUIZHOU_CODE_LONG)) {
            return cityLocationIdList;
        }

        if (isCity(locationId)) {
            List<String> locationList = new ArrayList<>();
            locationList.add(locationId);
            return locationList;
        }

        return null;

        //数据平台只能以市为单位来取数据
//        if(!isCity(locationId)) {
//            locationList.add(locationId);
//        }else{
//            LocationDictEntity cityEntity = cityCodeToEntity.get(locationId);
//            List<CountyDictEntity> countyList = cityEntity.getCountyList();
//            for(CountyDictEntity item : countyList){
//                locationList.add(item.getCode());
//            }
//        }
//        return locationList;
    }

    public String getLoctionNameById(String id) {
        if (locCodeToName.containsKey(id))
            return locCodeToName.get(id);
        return null;
    }

    public LocationDictEntity getLocEntityById(String cityCode) {
        if (cityCodeToEntity.containsKey(cityCode))
            return cityCodeToEntity.get(cityCode);
        return null;
    }

    //For testing, convert raw dict to json file
    public void readRawLocationDictConfig() {
        String path = yanglaoAppData.getLocationDictPath();
        if (path == null || path.isEmpty())
            throw new RuntimeException("请检查地址字典配置！");

        try {
            String jsStr = readStrFromFile(path);
            if (!jsStr.equals("")) {
                List<LocationDictEntity> cityList = new ArrayList<>();
                Map<String, LocationDictEntity> cityMap = new HashMap<>();
                Map<String, Object> locMap = JsonUtils.parseMap(jsStr);
                for (Map.Entry<String, Object> entry : locMap.entrySet()) {
                    String locCode = entry.getKey();
                    if (locCode.endsWith("00")) {
                        //this is a city
                        ArrayList<String> array = (ArrayList<String>) entry.getValue();
                        LocationDictEntity locEntity = new LocationDictEntity();
                        locEntity.setCode(locCode);
                        locEntity.setCity(array.get(0));
                        cityMap.put(locCode, locEntity);
                        cityList.add(locEntity);
                    } else {
                        Object val = entry.getValue();
                        ArrayList<String> array = (ArrayList<String>) val;
                        String parentLocCode = array.get(1);
                        if (cityMap.containsKey(parentLocCode)) {
                            CountyDictEntity countyEntity = new CountyDictEntity();
                            countyEntity.setCode(entry.getKey());
                            countyEntity.setCounty(array.get(0));
                            cityMap.get(parentLocCode).addCounty(countyEntity);
                        }
                    }
                }
                String output = JsonUtils.toJson(cityList, true);
                LOGGER.info("Complete...");
                File file = new File("loc.json");
                FileWriter fw = new FileWriter(file);
                BufferedWriter out = new BufferedWriter(fw);
                out.write(output);
                out.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("初始化地址字典失败！" + e.getMessage());
        }
    }


    //初始化数据源词典
    public void readDataSrcDictConfig() {
        String path = yanglaoAppData.getYanglaoConfigRootPath() + yanglaoAppData.getDataSrcDictPath();
        if (path.isEmpty())
            throw new RuntimeException("请检查数据源字典配置！");
        readDataSrcDictConfig(path);
    }

    public void readDataSrcDictConfig(String path) {
        try {
            String jsStr = readStrFromFile(path);
            if (!jsStr.equals("")) {
                dataSrcList = JsonUtils.fromJsonToList(jsStr, DataSrcDictEntity.class);
                for (DataSrcDictEntity dataSrcDictEntity : dataSrcList) {
                    String code = dataSrcDictEntity.getDataSrcCode();
                    String name = dataSrcDictEntity.getDataSrcName();
                    dataCodeToEntity.put(code, dataSrcDictEntity);
                    dataCodeToName.put(code, name);
                    //为每个DataSrcItem构建resultId到resultDesc的map
                    List<DataSrcItemEntity> dataList = dataSrcDictEntity.getDataList();
                    for (DataSrcItemEntity itemEntity : dataList) {
                        dataSrcDictEntity.addToDataSrcItemMap(itemEntity.getDataId(), itemEntity);
                        List<DataSrcItemResultEntity> resultList = itemEntity.getResultEnum();
                        for (DataSrcItemResultEntity resEntiy : resultList) {
                            itemEntity.addToResultMap(resEntiy.getResultId(), resEntiy.getResultDesc());
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("初始化数据源字典失败！" + e.getMessage());
        }
    }

    public DataSrcDictEntity getDataSrcEntiyByCode(String code) {
        if (dataCodeToEntity.containsKey(code))
            return dataCodeToEntity.get(code);
        return null;
    }

    public String getDataSrcNameByCode(String code) {
        if (dataCodeToName.containsKey(code))
            return dataCodeToName.get(code);
        return null;
    }

    public String getDataSrcItemResultDesc(String dataSrcCode, String dataItemId, String resultId) {
        DataSrcDictEntity dataSrcDictEntity = getDataSrcEntiyByCode(dataSrcCode);
        if (dataSrcDictEntity != null) {
            DataSrcItemEntity itemEntity = dataSrcDictEntity.getDataSrcItemEntityById(dataItemId);
            if (itemEntity != null)
                return itemEntity.getResultDescById(resultId);
        }
        return null;
    }

    //初始化输出状态字典
    public void readOutputStatusDictConfig() {
        String path = yanglaoAppData.getYanglaoConfigRootPath() + yanglaoAppData.getOutputStatusDictPath();
        if (path.isEmpty())
            throw new RuntimeException("请检查输出状态字典配置！");
        readOutputStatusDictConfig(path);
    }

    public void readOutputStatusDictConfig(String path) {
        try {
            String jsStr = readStrFromFile(path);
            if (!jsStr.equals("")) {
                Map<String, Object> objectMap = JsonUtils.parseMap(jsStr);
                for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                    outputStatusMap.put(entry.getKey(), entry.getValue().toString());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("初始化输出状态字典失败！" + e.getMessage());
        }
    }

    public String getOutputStatusById(String id) {
        if (outputStatusMap.containsKey(id))
            return outputStatusMap.get(id);
        return null;
    }

    //初始化状态说明字典
    public void readStatusDescriptionDictConfig() {
        String path = yanglaoAppData.getYanglaoConfigRootPath() + yanglaoAppData.getStatusDescDictPath();
        if (path.isEmpty())
            throw new RuntimeException("请检查状态说明字典配置！");
        readStatusDescriptionDictConfig(path);
    }

    public void readStatusDescriptionDictConfig(String path) {
        try {
            String jsStr = readStrFromFile(path);
            if (!jsStr.equals("")) {
                Map<String, Object> objectMap = JsonUtils.parseMap(jsStr);
                for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                    statusDescriptionMap.put(entry.getKey(), entry.getValue().toString());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("初始化状态说明字典失败！" + e.getMessage());
        }
    }

    public String getStatusDescById(String id) {
        if (statusDescriptionMap.containsKey(id))
            return statusDescriptionMap.get(id);
        return null;
    }

    //读取养老认证应用所需的数据源配置
    public void readTargetDataListConfig() {
        String path = yanglaoAppData.getYanglaoConfigRootPath() + yanglaoAppData.getTargetDataListPath();
        if (path.isEmpty())
            throw new RuntimeException("请检查目的数据源配置！");
        readTargetDataListConfig(path);
    }

    public void readTargetDataListConfig(String path) {
        try {
            String jsStr = readStrFromFile(path);
            if (!jsStr.equals("")) {
                targetDataEntityList = JsonUtils.fromJsonToList(jsStr, TargetDataEntity.class);
            }
        } catch (Exception e) {
            throw new RuntimeException("初始化目的数据源失败！" + e.getMessage());
        }
    }

    public List<TargetDataEntity> getTargetDataEntityList() {
        return targetDataEntityList;
    }

    public String readStrFromFile(String path) throws RuntimeException {
        try {
            URL url = ResourceUtils.getURL(path);
            LOGGER.debug("配置文件路径:{}", url.toString());
            if (ResourceUtils.isFileURL(url)) {
                File file = ResourceUtils.getFile(url);
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String jsStr = "";
                String lineStr = null;
                while ((lineStr = reader.readLine()) != null) {
                    jsStr += lineStr;
                }
                reader.close();
                return jsStr;
            }
        } catch (Exception e) {
            LOGGER.error("读取配置文件异常,路径:" + path, e);
            throw new RuntimeException(e.getMessage());
        }
        LOGGER.error("读取配置文件错误,返回空");
        return "";
    }

    public String readStrFromFile(File file) throws RuntimeException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String jsStr = "";
            String lineStr = null;
            while ((lineStr = reader.readLine()) != null) {
                jsStr += lineStr;
            }
            reader.close();
            return jsStr;
        } catch (Exception e) {
            LOGGER.error("读取配置文件异常");
            throw new RuntimeException(e.getMessage());
        }
    }


}
