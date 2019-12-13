package com.xcuni.guizhouyl.rest.thread;

import com.alibaba.fastjson.JSONObject;
import com.xcuni.guizhouyl.data.service.DataDictService;
import com.xcuni.guizhouyl.data.RedisClient;
import com.xcuni.guizhouyl.data.entity.YanglaoUserInfoEntity;
import com.xcuni.guizhouyl.rest.controller.YanglaoAppController;
import com.xcuni.guizhouyl.rest.entity.FetchDataUserInfoEntity;
import com.xcuni.guizhouyl.rest.entity.FetchUserDataRequestEntity;
import com.xcuni.guizhouyl.rest.entity.FetchUserDataResultEntity;
import com.xcuni.guizhouyl.rest.entity.TargetDataEntity;
import com.xcuni.guizhouyl.data.service.DataPlatformService;
import com.xcuni.guizhouyl.data.service.RedisKeyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FetchUserDataRunner implements Runnable {
    final static private Logger LOGGER = LoggerFactory.getLogger(FetchUserDataRunner.class);
    final static private String SUCCESS_CODE = "200";
    private DataPlatformService dataPlatformService;// = new DataPlatformService();
    private RedisKeyService redisKeyService;
    private DataDictService dataDictService;
    private String userInfo;

    public FetchUserDataRunner(String userInfo, DataDictService dataDictService,
                               DataPlatformService dataPlatformService, RedisKeyService redisKeyService) {
        this.userInfo = userInfo;
        this.dataDictService = dataDictService;
        this.dataPlatformService = dataPlatformService;
        this.redisKeyService = redisKeyService;
    }

    private FetchUserDataRequestEntity buildFetchUserDataRequest(String userInfoStr) {
        YanglaoUserInfoEntity userInfoEntity = JSONObject.parseObject(userInfoStr, YanglaoUserInfoEntity.class);
        FetchUserDataRequestEntity fetchReq = new FetchUserDataRequestEntity();
        fetchReq.setDate(YanglaoAppController.getVerificationDate());
        List<TargetDataEntity> targetData = dataDictService.getTargetDataEntityList();
        fetchReq.setTargetDataList(targetData);
        FetchDataUserInfoEntity fetchUser = new FetchDataUserInfoEntity();
        fetchUser.setName(userInfoEntity.getName());
        fetchUser.setId(userInfoEntity.getIdNo());
        fetchReq.setUserInfo(fetchUser);

        return fetchReq;
    }

    @Override
    public void run() {
        LOGGER.debug("Fecth user data thread...");
        FetchUserDataRequestEntity requestEntity = buildFetchUserDataRequest(userInfo);
        FetchUserDataResultEntity resultEntity = dataPlatformService.fetchUserData(requestEntity);
        if (resultEntity == null) {
            LOGGER.info("未能获取到用户{}的数据！", requestEntity.getUserInfo().getId());
            return;
        }
        if (!resultEntity.getCode().equals(SUCCESS_CODE)) {
            LOGGER.info("获取到用户{}的数据错误,{}", requestEntity.getUserInfo().getId(), resultEntity.getMessage());
            String failedQkey = redisKeyService.getFailedUserInfoQueueKey();
            RedisClient.lpush(failedQkey, userInfo);
            return;
        }
        //记录数据源的使用次数，先记录在redis中，验证结束后上链保存
        try {
            List<TargetDataEntity> dataList = requestEntity.getTargetDataList();
            String dataSrcSetKey = redisKeyService.getDataSrcSetKey();
            for (TargetDataEntity dataEntity : dataList) {
                String dataSrc = dataEntity.getDataSrc();
                String key = redisKeyService.getDataSrcQueryNumKey(dataSrc);
                RedisClient.incr(key);
                RedisClient.sadd(dataSrcSetKey, dataSrc);//将用到的数据源也记录一下
            }
        } catch (Exception e) {
            LOGGER.error("计算调用次数出现异常！");
        }
        try {
            //构造一个JSON对象，并push到队列中，供消费者线程处理
            JSONObject userDataObj = new JSONObject();
            userDataObj.put("UserInfo", userInfo);
            Object dataObj = JSONObject.toJSON(resultEntity.getUserDataList());
            String userDataStr = JSONObject.toJSONString(dataObj);//dataObj.toString();
            userDataObj.put("UserData", userDataStr);

            String userDataQ = redisKeyService.getUserDataQueueKey();
            RedisClient.lpush(userDataQ, userDataObj.toJSONString());
            LOGGER.debug("用户{}数据进入队列", requestEntity.getUserInfo().getId());
        } catch (Exception e) {
            LOGGER.error("处理用户数据返回异常{}", e.getMessage());
        }
    }
}
