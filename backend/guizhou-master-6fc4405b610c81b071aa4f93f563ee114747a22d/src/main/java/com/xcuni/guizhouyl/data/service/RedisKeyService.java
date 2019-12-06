package com.xcuni.guizhouyl.data.service;

import com.xcuni.guizhouyl.rest.controller.YanglaoAppController;
import com.xcuni.guizhouyl.utils.CommonUtils;
import org.springframework.stereotype.Service;

@Service
public class RedisKeyService {

    private String delimiter = "-";
    private String accountDate = "ACCOUNT-DATE";
    private String userInfoQueuePrefix = "USER-INFO";
    private String userDataQueuePrefix = "USER-DATA";
    private String userCountPrefix = "USER-COUNT";
    private String totalUserCountPrefix = "TOTAL-USER-COUNT";
    private String succUserCountPrefix = "SUCC-USER-COUNT";
    private String failedUserInfoQueuePrefix = "FAILED-USER-INFO";
    private String dataSrcQueryNumPrefix = "DATA-QUERY-NUM";
    private String dataSrcSetPrefix = "DATA-SRC-SET";
    private String executeDateKey = "EXEC-DATE";

    public String getExecuteDateKey() {
        return executeDateKey;
    }

    public String getAccountDateKey() {
        return CommonUtils.KeyGenerator(delimiter, accountDate);
    }

    public String getUserInfoQueueKey() {
        //String date = YanglaoAppController.getVerificationDate();
        return CommonUtils.KeyGenerator(delimiter, userInfoQueuePrefix);//, date);
    }

    public String getUserDataQueueKey() {
        return CommonUtils.KeyGenerator(delimiter, userDataQueuePrefix);
    }

    public String getCityUserCountKey(String location) {
        String date = YanglaoAppController.getVerificationDate();
        return CommonUtils.KeyGenerator(delimiter, userCountPrefix, location, date);
    }

    public String getTotalUserCountKey() {
        String date = YanglaoAppController.getVerificationDate();
        return CommonUtils.KeyGenerator(delimiter, totalUserCountPrefix, date);
    }

    public String getFailedUserInfoQueueKey() {
        String date = YanglaoAppController.getVerificationDate();
        return CommonUtils.KeyGenerator(delimiter, failedUserInfoQueuePrefix, date);
    }

    public String getSuccUserCountKey() {
        String date = YanglaoAppController.getVerificationDate();
        return CommonUtils.KeyGenerator(delimiter, succUserCountPrefix, date);
    }

    public String getDataSrcQueryNumKey(String dataSrc) {
        return CommonUtils.KeyGenerator(delimiter, dataSrcQueryNumPrefix, dataSrc);
    }

    public String getDataSrcSetKey() {
        String date = YanglaoAppController.getVerificationDate();
        return CommonUtils.KeyGenerator(delimiter, dataSrcSetPrefix, date);
    }
}
