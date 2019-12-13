package com.xcuni.guizhouyl.fabric;

import com.alibaba.fastjson.JSONObject;
import com.fuliang.hyperledger.fabric.sdk.entity.TransactionEntity;
import com.xcuni.guizhouyl.config.ChaincodeData;
import com.xcuni.guizhouyl.config.ChannelData;
import com.xcuni.guizhouyl.config.FabricAppData;
import com.xcuni.guizhouyl.config.YanglaoAppData;
import lombok.Data;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.TransactionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
public class FabricOperation {
    private final Logger LOGGER = LoggerFactory.getLogger(FabricOperation.class);
    @Autowired
    FabricAppData fabricAppData;
    @Autowired
    YanglaoAppData yanglaoAppData;
    @Autowired
    FabricManager fabricManager;
    private final String QUERY_FUNC_NAME = "queryCC";
    private final String RECORD_FUNC_NAME = "verify";
    private final String CALL_NUM_FUNC_NAME = "callTimes";

    public JSONObject invokeOnFabric(String channelName, ChaincodeID ccId, String func, String[] args) {
        TransactionEntity transaction = new TransactionEntity(ccId, TransactionRequest.Type.GO_LANG);
        transaction.setArgs(args);
        transaction.setFunc(func);
        JSONObject resObj = fabricManager.invokeChaincode(channelName, transaction);
        LOGGER.info("invokeVerficationOnFabric result: {}", resObj.toString());
        return resObj;
    }

    public JSONObject invokeOnFabric(String channelName, String chaincodeName, String func, String[] args) {
        ChaincodeID ccId = ChaincodeID.newBuilder().setName(chaincodeName).setPath("").setVersion("").build();
        return invokeOnFabric(channelName, ccId, func, args);
    }

    public JSONObject invokeOnFabric(String func, String[] args) {
        String channelName = yanglaoAppData.getYanglaoChannelName();
        if (channelName == null || channelName.isEmpty())
            throw new RuntimeException("application.yaml中缺少yanglao-channel-name");

        ChannelData chData = fabricAppData.getChannelData(channelName);
        if (chData == null)
            throw new RuntimeException("application.yaml的fabric-channels缺少" + channelName);

        String chaincodeName = yanglaoAppData.getDataChaincode();// 指定不同链码名称
        if (chaincodeName == null || chaincodeName.isEmpty())
            throw new RuntimeException("application.yaml中缺少data-record-chaincod");

        ChaincodeData chaincodeData = chData.getChaincodeData(chaincodeName);
        if (chaincodeData == null)
            throw new RuntimeException("application.yamld的channel:" + channelName + "缺少" + chaincodeName);

        ChaincodeID ccId = ChaincodeID.newBuilder().setName(chaincodeData.getName()).setPath(chaincodeData.getPath()).setVersion(chaincodeData.getVersion()).build();
        LOGGER.debug("invokeVerficationOnFabric>> channel:{}, chaincode:{},func:{},args:{}", chData.getChannelName(), chaincodeData.getName(), func, args);
        return invokeOnFabric(channelName, ccId, func, args);
    }

    public JSONObject invokeVerficationOnFabric(String id, String date, String userDataJson) throws RuntimeException {
        if (id == null || id.isEmpty())
            throw new RuntimeException("传入的身份证id为空");

        if (date == null || date.isEmpty())
            throw new RuntimeException("传入的时间为空");

        //获取func名字
        String func = yanglaoAppData.getRecordFuncName();
        if (func == null || func.isEmpty())
            func = RECORD_FUNC_NAME;
        String[] args = new String[3];
        args[0] = id;
        args[1] = date;
        args[2] = userDataJson;

        LOGGER.debug("invokeVerficationOnFabric>> func:{},args:{}", func, args);
        return invokeOnFabric(func, args);
    }

    public JSONObject invokeCallNumOnFabric(String dataSrcKey, String date, String num) throws RuntimeException {
        if (num == null || num.isEmpty())
            throw new RuntimeException("传入的调用次数为空");

        if (date == null || date.isEmpty())
            throw new RuntimeException("传入的时间为空");

        //获取func名字
//        String func = yanglaoAppData.getRecordFuncName();
//        if(func == null || func.isEmpty())
//            func = RECORD_FUNC_NAME;
        String[] args = new String[3];
        args[0] = dataSrcKey;
        args[1] = date;
        args[2] = num;
        LOGGER.debug("invokeCallNumOnFabric>> func:{},args:{}", CALL_NUM_FUNC_NAME, args);
        return invokeOnFabric(CALL_NUM_FUNC_NAME, args);
    }

    //{"Args":["queryCC", "queryResult","987654321123456588"]}'
    public JSONObject queryUserModelStatus(String id, String name) throws RuntimeException {
        if (id == null || id.isEmpty())
            throw new RuntimeException("传入的身份证id为空");

        //获取func名字
        String func = yanglaoAppData.getQueryFuncName();
        if (func == null || func.isEmpty())
            func = QUERY_FUNC_NAME;
        String[] args = new String[2];
        args[0] = "queryResult";
        args[1] = id;
        LOGGER.debug("queryUserModelStatus>> func:{},args:{}", func, args);
        return queryOnFabric(func, args);
    }

    public JSONObject queryOnFabric(String func, String[] args) {
        String channelName = yanglaoAppData.getYanglaoChannelName();
        if (channelName == null || channelName.isEmpty())
            throw new RuntimeException("application.yaml中缺少yanglao-channel-name");

        ChannelData chData = fabricAppData.getChannelData(channelName);
        if (chData == null)
            throw new RuntimeException("application.yamld的fabric-channels缺少" + channelName);

        String chaincodeName = yanglaoAppData.getDataChaincode();
        if (chaincodeName == null || chaincodeName.isEmpty())
            throw new RuntimeException("application.yaml中缺少data-query-chaincode");

        ChaincodeData chaincodeData = chData.getChaincodeData(chaincodeName);
        if (chaincodeData == null)
            throw new RuntimeException("application.yamld的channel:" + channelName + "缺少" + chaincodeName);

        ChaincodeID ccId = ChaincodeID.newBuilder().setName(chaincodeData.getName()).setPath(chaincodeData.getPath()).setVersion(chaincodeData.getVersion()).build();
        LOGGER.debug("queryOnFabric>> channel:{}, chaincode:{},func:{},args:{}", chData.getChannelName(), chaincodeData.getName(), func, args);
        return queryOnFabric(channelName, ccId, func, args);
    }

    //query   2222222
    private JSONObject queryOnFabric(String channelName, ChaincodeID ccId, String func, String[] args) {
        TransactionEntity transaction = new TransactionEntity(ccId, TransactionRequest.Type.GO_LANG);
        transaction.setArgs(args);
        transaction.setFunc(func);
        JSONObject resObj = fabricManager.queryChaincode(channelName, transaction);
        LOGGER.info("queryOnFabric result: {}", resObj.toString());
        return resObj;
    }

    //query   11111111
    public JSONObject queryOnFabric(String channelName, String chaincodeName, String func, String[] args) {
        ChaincodeID ccId = ChaincodeID.newBuilder().setName(chaincodeName).setPath("").setVersion("").build();
        return queryOnFabric(channelName, ccId, func, args);
    }
}
