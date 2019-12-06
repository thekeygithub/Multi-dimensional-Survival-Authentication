package com.xcuni.guizhouyl.rest.thread;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xcuni.guizhouyl.config.YanglaoAppData;
import com.xcuni.guizhouyl.data.service.DataDictService;
import com.xcuni.guizhouyl.data.RedisClient;
import com.xcuni.guizhouyl.data.database.entity.TblDeathDataSrcEntity;
import com.xcuni.guizhouyl.data.database.entity.TblUserStatusEntity;
import com.xcuni.guizhouyl.data.database.service.YanglaoDbOperation;
import com.xcuni.guizhouyl.data.entity.YanglaoUserInfoEntity;
import com.xcuni.guizhouyl.rest.controller.YanglaoAppController;
import com.xcuni.guizhouyl.rest.entity.*;
import com.xcuni.guizhouyl.data.service.DataPlatformService;
import com.xcuni.guizhouyl.data.service.RedisKeyService;
import com.xcuni.guizhouyl.utils.DateUtils;
import com.xcuni.guizhouyl.utils.HttpUtils;
import com.xcuni.guizhouyl.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static java.lang.Thread.sleep;

public class SingleVerificationRunner implements Runnable {
    final static private Logger LOGGER = LoggerFactory.getLogger(SingleVerificationRunner.class);

    //private FectchUserInfoRequestEntity requestEntity;
    private String reqDate = "";
    private DataDictService dataDictService;
    private DataPlatformService dataPlatformService;
    private RedisKeyService redisKeyService;
    private YanglaoAppData yanglaoAppData;
    private YanglaoDbOperation yanglaoDbOperation;
    private String userInfoStr;
    private String userDataStr;
    /**
     * 执行状态 <li>0 = 执行结束 <li>1 = 正在执行中 <li>2 = 等待执行
     */
    private String execStatus = "2";

    /**
     * 执行状态 <li>0 = 执行结束 <li>1 = 正在执行中 <li>2 = 等待执行
     */
    public String getExecStatus() {
        return execStatus;
    }

    /**
     * 执行状态 <li>0 = 执行结束 <li>1 = 正在执行中 <li>2 = 等待执行
     */
    public void setExecStatus(String execStatus) {
        this.execStatus = execStatus;
    }

    public SingleVerificationRunner(String reqDate, DataDictService dataDictService,
                                    DataPlatformService dataPlatformService, RedisKeyService redisKeyService,
                                    YanglaoAppData yanglaoAppData, YanglaoDbOperation yanglaoDbOperation, String userInfoStr) {
        //this.requestEntity = req;
        this.reqDate = reqDate;
        this.dataDictService = dataDictService;
        this.dataPlatformService = dataPlatformService;
        this.redisKeyService = redisKeyService;
        this.yanglaoAppData = yanglaoAppData;
        this.yanglaoDbOperation = yanglaoDbOperation;
        this.userInfoStr = userInfoStr;
        this.userDataStr = "";
    }

    public SingleVerificationRunner(DataDictService dataDictService, DataPlatformService dataPlatformService,
                                    RedisKeyService redisKeyService, YanglaoAppData yanglaoAppData,
                                    YanglaoDbOperation yanglaoDbOperation, String userDataStr) {
        this.dataDictService = dataDictService;
        this.dataPlatformService = dataPlatformService;
        this.redisKeyService = redisKeyService;
        this.yanglaoAppData = yanglaoAppData;
        this.yanglaoDbOperation = yanglaoDbOperation;
        this.userInfoStr = "";
        this.userDataStr = userDataStr;

    }

    //封装请求参数
    private FetchUserDataRequestEntity buildFetchUserDataRequest(String userInfoStr) {
        YanglaoUserInfoEntity userInfoEntity = JSONObject.parseObject(userInfoStr, YanglaoUserInfoEntity.class);
        FetchUserDataRequestEntity fetchReq = new FetchUserDataRequestEntity();
        fetchReq.setDate(reqDate);
        List<TargetDataEntity> targetData = dataDictService.getTargetDataEntityList();
        fetchReq.setTargetDataList(targetData);
        FetchDataUserInfoEntity fetchUser = new FetchDataUserInfoEntity();
        fetchUser.setName(userInfoEntity.getName());
        fetchUser.setId(userInfoEntity.getIdNo());
        fetchReq.setUserInfo(fetchUser);

        return fetchReq;
    }

    //处理来自HTTP 端单用户请求验证
    private InvokeVerificationEntity buildInvokeVerificationRequest(FetchUserDataRequestEntity fetchReqEntity,
                                                                    FetchUserDataResultEntity fetchResultEntity) {
        InvokeVerificationEntity invokeVerificationEntity = new InvokeVerificationEntity();
        String invokeDate = LocalDateTime.now().format(DateUtils.YEAR_MONTH_DAY);
//        invokeVerificationEntity.setDate(invokeDate);
        invokeVerificationEntity.setId(fetchReqEntity.getUserInfo().getId());
        //构造userDataJson
        JSONObject reqJsonObj = new JSONObject();
        reqJsonObj.put("Date", invokeDate);
        //构造UserInfo
        JSONObject userInfoObj = new JSONObject();
        userInfoObj.put("ID", fetchReqEntity.getUserInfo().getId());
        userInfoObj.put("Name", fetchReqEntity.getUserInfo().getName());
        reqJsonObj.put("UserInfo", userInfoObj);
        //构造userDataList
        Object userDataObj = JSONObject.toJSON(fetchResultEntity.getUserDataList());
        reqJsonObj.put("UserDataList", userDataObj);
        invokeVerificationEntity.setUserDataJson(reqJsonObj.toJSONString());

        //账期，201811
        //String accDate = this.reqDate;
        invokeVerificationEntity.setDate(getAccountDate());

        return invokeVerificationEntity;
    }

    private String getAccountDate() {
        String accDateKey = redisKeyService.getAccountDateKey();
        String accDate = RedisClient.get(accDateKey);
        if (StringUtils.isBlank(accDate))
            accDate = YanglaoAppController.getVerificationDate();
        return accDate;
    }

    //
    private InvokeVerificationEntity buildInvokeVerificationRequest(String userInfoStr, String userDataStr) {
        InvokeVerificationEntity invokeVerificationEntity = new InvokeVerificationEntity();
        String invokeDate = LocalDateTime.now().format(DateUtils.YEAR_MONTH_DAY);
        YanglaoUserInfoEntity userInfoEntity = JSONObject.parseObject(userInfoStr, YanglaoUserInfoEntity.class);

        invokeVerificationEntity.setId(userInfoEntity.getIdNo());
        //构造userDataJson
        JSONObject reqJsonObj = new JSONObject();
        reqJsonObj.put("Date", invokeDate);
        //构造UserInfo
        JSONObject userInfoObj = new JSONObject();
        userInfoObj.put("ID", userInfoEntity.getIdNo());
        userInfoObj.put("Name", userInfoEntity.getName());
        reqJsonObj.put("UserInfo", userInfoObj);
        //构造userDataList
        //Object userDataObj = JSONObject.toJSON(userDataStr);
        //JSONObject userDataObj = JSONObject.parseObject(userDataStr);
        JSONArray datalist = JSONArray.parseArray(userDataStr);
        reqJsonObj.put("UserDataList", datalist);
        invokeVerificationEntity.setUserDataJson(reqJsonObj.toJSONString());
        //账期，201811
        //String accDate = this.reqDate;
        invokeVerificationEntity.setDate(getAccountDate());
        return invokeVerificationEntity;
    }

    private String sendInvokeRequest(InvokeVerificationEntity invokeVerificationEntity) {
        String url = yanglaoAppData.getFabricVerifyUrl();
        return HttpUtils.sendPOST(url, JSONObject.toJSONString(invokeVerificationEntity));
    }

    private void processVerificationResponse(String response, String userInfoStr) {
        try {
            boolean isSucc = false;
            JSONObject respObj = JSONObject.parseObject(response);
            String statusCode = respObj.getString("statusCode");
            LOGGER.debug("processVerificationResponse verfiy return {}", statusCode);
            if (statusCode != null && statusCode.equals("200")) {
                //获取区块链返回的结果
                String invokeRes = respObj.getString("returnObj");
                if (invokeRes != null && !invokeRes.isEmpty()) {
                    InvokeVerificationResultEntity invokeResult = JSONObject.parseObject(invokeRes, InvokeVerificationResultEntity.class);
                    String code = invokeResult.getCode();
                    if (code != null && code.equals("200")) {
                        //区块链chaincode返回成功,将用户验证结果存到db
                        isSucc = true;
                        String succCountKey = redisKeyService.getSuccUserCountKey();
                        Long res = RedisClient.incr(succCountKey);
                        LOGGER.info("成功人数增加到{}:{}", succCountKey, res);
                        //将结果写入数据库
                        pushToDatabase(invokeResult);
                    }
                }
            }
            //如果处理失败，则将用户信息放到错误队列，等待重试
            if (!isSucc) {
                String failedQkey = redisKeyService.getFailedUserInfoQueueKey();
                RedisClient.lpush(failedQkey, userInfoStr);//入队列
                pushFailedUserDataToDataBase(userInfoStr);
            }

        } catch (Exception e) {
            LOGGER.error("处理验证结果发生异常,{}", e.getMessage());
        }
    }

    private void pushFailedUserDataToDataBase(String userInfoStr) {
        YanglaoUserInfoEntity userInfoEntity = JSONObject.parseObject(userInfoStr, YanglaoUserInfoEntity.class);
        String userId = userInfoEntity.getIdNo();
        LOGGER.info("用户{}验证失败，更新数据库记录为状态不明", userId);
        TblDeathDataSrcEntity deathDataEntityDb = yanglaoDbOperation.getDeathDataSrcService().getDeathDataEntityById(userId);
        if (deathDataEntityDb != null) {
            LOGGER.info("验证失败用户在死亡表中，不做处理。");
            return;
        }
        TblUserStatusEntity userStatusEntityDb = yanglaoDbOperation.getUserStatusService().getUserStatusEntityById(userId);
        TblUserStatusEntity userEntity = new TblUserStatusEntity();
        userEntity.setUserId(userId);
        userEntity.setUserStatus(9);//9代表状态不明
        userEntity.setStatusDesc(9);//9代表状态不明
        userEntity.setNote("验证系统异常！");//系统异常导致失败
        userEntity.setPointS(0);
        userEntity.setPointT(0);
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String today = df.format(day);
        try {
            String stamp = DateUtils.dateStringToDate(today);
            userEntity.setUpdateTime(stamp);
        } catch (Exception e) {
            LOGGER.error("请检查userStatus表update日期格式:{}", today);
        }

        //数据库里面没有这个人的状态信息，需要insert
        if (userStatusEntityDb == null) {
            yanglaoDbOperation.getUserStatusService().insertUserStatus(userEntity);
            LOGGER.debug("为验证失败用户{}创建状态记录", userId);
        } else {
            //如果有数据，那么做update
            yanglaoDbOperation.getUserStatusService().updateUserStatus(userEntity);
            LOGGER.debug("为验证失败用户{}更新状态记录", userId);
        }
    }

    private void pushToDatabase(InvokeVerificationResultEntity invokeResult) {

        String userId = invokeResult.getData().getUserInfo().getId();
        LOGGER.info("将用户{}状态写入数据库.", userId);
        int isAlive = invokeResult.getData().getAliveStatus().getIsAlive();
        TblUserStatusEntity userStatusEntityDb = yanglaoDbOperation.getUserStatusService().getUserStatusEntityById(userId);
        TblDeathDataSrcEntity deathDataEntityDb = yanglaoDbOperation.getDeathDataSrcService().getDeathDataEntityById(userId);

        TblUserStatusEntity userEntity = new TblUserStatusEntity();
        userEntity.setUserId(userId);
        String pointS = invokeResult.getData().getVerificationInfo().getPointS();
        if (pointS != null && !pointS.isEmpty())
            userEntity.setPointS(Integer.parseInt(pointS));
        String pointT = invokeResult.getData().getVerificationInfo().getPointT();
        if (pointT != null && !pointT.isEmpty())
            userEntity.setPointT(Integer.parseInt(pointT));
        String userStatus = invokeResult.getData().getVerificationInfo().getModelOutput();
        if (userStatus != null && !userStatus.isEmpty())
            userEntity.setUserStatus(Integer.parseInt(userStatus));
        String outDesc = invokeResult.getData().getVerificationInfo().getOutputDesc();
        if (outDesc != null && !outDesc.isEmpty())
            userEntity.setStatusDesc(Integer.parseInt(outDesc));
        //userEntity.setNote(invokeResult.getData().getVerificationInfo().get);
        if (isAlive == 0)
            userEntity.setNote(invokeResult.getData().getAliveStatus().getCauseOfDeath());
        else
            userEntity.setNote("");

        String updTime = invokeResult.getData().getVerificationInfo().getUpdateTime();
        try {
            String stamp = DateUtils.dateStringToDate(updTime);
            userEntity.setUpdateTime(stamp);
        } catch (Exception e) {
            LOGGER.error("请检查userStatus表update日期格式:{}", updTime);
        }
        String aliveDataSrc = invokeResult.getData().getAliveStatus().getDataSrc();
        String aliveDataId = invokeResult.getData().getAliveStatus().getDataID();
        userEntity.setAliveDataSrc(aliveDataSrc + "-" + aliveDataId);


        //数据库里面没有这个人的状态信息，需要insert
        if (userStatusEntityDb == null) {
            yanglaoDbOperation.getUserStatusService().insertUserStatus(userEntity);
            LOGGER.info("为用户{}创建状态记录", userId);
        } else {
            //如果有数据，那么做update
            yanglaoDbOperation.getUserStatusService().updateUserStatus(userEntity);
            LOGGER.info("为用户{}更新状态记录", userId);
        }

        //如果死亡，还需要写入死亡数据
        if (isAlive == 0) {
            TblDeathDataSrcEntity deathDataEntity = new TblDeathDataSrcEntity();
            deathDataEntity.setUserId(userId);
            String deathTime = invokeResult.getData().getAliveStatus().getSurvivalStatusDate();
            try {
                String stamp = DateUtils.dateStringToDate(deathTime);
                deathDataEntity.setDeathTime(stamp);
            } catch (Exception e) {
                LOGGER.error("请检查death date日期格式:{}", updTime);
            }
            deathDataEntity.setDataSrc(invokeResult.getData().getAliveStatus().getDataSrc());
            deathDataEntity.setDataId(invokeResult.getData().getAliveStatus().getDataID());
            deathDataEntity.setNote(invokeResult.getData().getAliveStatus().getCauseOfDeath());
            String uTime = invokeResult.getData().getAliveStatus().getUpdateTime();
            try {
                String stamp = DateUtils.dateStringToDate(uTime);
                deathDataEntity.setUpdateTime(stamp);
            } catch (Exception e) {
                LOGGER.error("请检查death表updateTime日期格式:{}", updTime);
            }

            if (deathDataEntityDb == null) {
                yanglaoDbOperation.getDeathDataSrcService().insertDeathData(deathDataEntity);
                LOGGER.info("为用户{}创建死亡信息记录", userId);
            } else {
                yanglaoDbOperation.getDeathDataSrcService().updateDeathData(deathDataEntity);
                LOGGER.info("为用户{}更新死亡信息记录", userId);
            }
        }

        //如果新的生存状态是1，但是又有死亡数据，那么需要删除
        if (isAlive == 1 && deathDataEntityDb != null) {
            yanglaoDbOperation.getDeathDataSrcService().deleteDeathData(userId);
            LOGGER.info("用户{}的生存状态为1，删除死亡数据！", userId);
        }
    }

    @Override
    public void run() {
        try {
            LOGGER.info("开始为用户{}进行验证", userDataStr);
            InvokeVerificationEntity invokeEntity = null;
            if (userDataStr.equals("")) {
                //来自http客户端的单个用户验证
                FetchUserDataRequestEntity requestEntity = buildFetchUserDataRequest(userInfoStr);
                FetchUserDataResultEntity resultEntity = dataPlatformService.fetchUserData(requestEntity);
                if (resultEntity != null) {
                    invokeEntity = buildInvokeVerificationRequest(requestEntity, resultEntity);
                } else {
                    LOGGER.info("未能获取到用户{}的数据！", requestEntity.getUserInfo().getId());
                }
            } else {//来自生产者的批量验证，队列中pop出来的json串包含用户信息和用户数据两部分
                JSONObject queueDataObj = JSONObject.parseObject(userDataStr);
                userInfoStr = queueDataObj.getString("UserInfo");
                String userDataListStr = queueDataObj.getString("UserData");
                invokeEntity = buildInvokeVerificationRequest(userInfoStr, userDataListStr);
            }

            if (invokeEntity != null) {
                String response = sendInvokeRequest(invokeEntity);
                LOGGER.info("chaincode验证服务返回,用户id:{}，响应:{}", invokeEntity.getId(), response);
                processVerificationResponse(response, userInfoStr);
            }
        } catch (Exception e) {
            LOGGER.error("VerificationRunner接口异常:{}", e.getMessage());
        }
    }
}
