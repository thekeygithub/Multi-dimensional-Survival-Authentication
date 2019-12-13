package com.xcuni.guizhouyl.rest.controller;

import com.alibaba.fastjson.JSONObject;
import com.xcuni.guizhouyl.config.YanglaoAppData;
import com.xcuni.guizhouyl.fabric.FabricOperation;
import com.xcuni.guizhouyl.model.traceInfo;
import com.xcuni.guizhouyl.rest.entity.ChaincodeRequestEntity;
import com.xcuni.guizhouyl.rest.entity.InvokeVerificationEntity;
import com.xcuni.guizhouyl.rest.entity.QueryUserModelEntity;
import com.xcuni.guizhouyl.rest.entity.RecordDataSrcCallNumEntity;
import com.xcuni.guizhouyl.rest.result.JsonRESTResult;
import com.xcuni.guizhouyl.service.TraceInfoService;
import com.xcuni.guizhouyl.service.impl.TraceInfoServiceImpl;
import com.xcuni.guizhouyl.utils.AESUtils;
import com.xcuni.guizhouyl.utils.IpfsUtils;
import com.xcuni.guizhouyl.utils.IpfsUtils2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.xcuni.guizhouyl.ApplicationSetup.*;
import static com.xcuni.guizhouyl.utils.AESUtils.AESDncode;
import static com.xcuni.guizhouyl.utils.AESUtils.AESEncode;

@RestController
@RequestMapping(value = "/fabric/chaincode/")
public class FabricChaincodeController {
    final static Logger LOGGER = LoggerFactory.getLogger(FabricChaincodeController.class);
    AESUtils se=new AESUtils();
    @Autowired
    YanglaoAppData yanglaoAppData;
    @Autowired
    FabricOperation fabricOperation;
    @Autowired
    TraceInfoService service;
    @Autowired
    traceInfo tInfo;
    final static private String SUCCESS_CODE = "200";
    final static private int SUCCESS = 200;
    final static private int ERROR = 500;


    @RequestMapping(value = "/queryChaincode", method = RequestMethod.POST)
    public String queryChaincode(@RequestBody ChaincodeRequestEntity queryReq) throws IOException {
        JSONObject resobj;
        try {
            resobj = fabricOperation.queryOnFabric(queryReq.getChannelName(), queryReq.getChaincodeName(), queryReq.getFunc(), queryReq.getArgs());
        } catch (Exception e) {
            LOGGER.info("queryChaincode异常:{}", e.getMessage());
            return new JsonRESTResult(e.getMessage()).encode();
        }
        String code = resobj.getString("code");
        //String date= resobj.getString("date"); 获取返回的数据
        if (code != null && !code.equals(SUCCESS_CODE))
            return new JsonRESTResult(ERROR, resobj.toJSONString()).encode();
        return new JsonRESTResult(resobj).encode();
    }

    @RequestMapping(value = "/queryUserStatus", method = RequestMethod.POST)
    public String queryUserModel(@RequestBody QueryUserModelEntity queryReq) throws IOException {
        JSONObject resobj;
        try {
            resobj = fabricOperation.queryUserModelStatus(queryReq.getId(), queryReq.getName());
        } catch (Exception e) {
            LOGGER.info("queryUserModel异常:{}", e.getMessage());
            return new JsonRESTResult(e.getMessage()).encode();
        }
        String code = resobj.getString("code");
        if (code != null && !code.equals(SUCCESS_CODE))
            return new JsonRESTResult(ERROR, resobj.toJSONString()).encode();
        return new JsonRESTResult(resobj).encode();
    }

    @RequestMapping(value = "/invokeChaincode", method = RequestMethod.POST)
    public String invokeChaincode(@RequestBody ChaincodeRequestEntity req) throws IOException {
        JSONObject resobj;
        try {
            resobj = fabricOperation.invokeOnFabric(req.getChannelName(), req.getChaincodeName(), req.getFunc(), req.getArgs());
        } catch (Exception e) {
            LOGGER.info("invokeChaincode异常:{}", e.getMessage());
            return new JsonRESTResult(e.getMessage()).encode();
        }
        String code = resobj.getString("code");
        if (code != null && !code.equals(SUCCESS_CODE))
            return new JsonRESTResult(ERROR, resobj.toJSONString()).encode();
        //invoke 事件成功结束
        return new JsonRESTResult(resobj).encode();
    }

    @RequestMapping(value = "/invokeVerification", method = RequestMethod.POST)
    public String invokeVerification(@RequestBody InvokeVerificationEntity req) throws IOException {
        JSONObject resobj;
        try {
            LOGGER.info("invokeVerification-id:{},date:{},data:{}", req.getId(), req.getDate(), req.getUserDataJson());
            resobj = fabricOperation.invokeVerficationOnFabric(req.getId(), req.getDate(), req.getUserDataJson());
        } catch (Exception e) {
            LOGGER.info("invokeVerification异常:{}", e.getMessage());
            return new JsonRESTResult(ERROR, e.getMessage()).encode();
        }
        String code = resobj.getString("code");
        if (code != null && !code.equals(SUCCESS_CODE)) {
            return new JsonRESTResult(ERROR, resobj.toJSONString()).encode();
        }
        return new JsonRESTResult(resobj).encode();
    }

    @RequestMapping(value = "/recordDataSrcCallNum", method = RequestMethod.POST)
    public String recordDataSrcCallNum(@RequestBody RecordDataSrcCallNumEntity req) throws IOException {
        JSONObject resobj;
        try {
            LOGGER.info("recordDataSrcCallNum:data: {},date:{},num:{}", req.getDataSrc(), req.getDate(), req.getCallNum());
            resobj = fabricOperation.invokeCallNumOnFabric(req.getDataSrc(), req.getDate(), req.getCallNum());
        } catch (Exception e) {
            LOGGER.info("recordDataSrcCallNum:{}", e.getMessage());
            return new JsonRESTResult(ERROR, e.getMessage()).encode();
        }
        String code = resobj.getString("code");
        if (code != null && !code.equals(SUCCESS_CODE)) {
            return new JsonRESTResult(ERROR, resobj.toJSONString()).encode();
        }
        return new JsonRESTResult(resobj).encode();
    }

    /*
        数据目录上链
     */
    @RequestMapping(value = "/ContentUpload", method = RequestMethod.POST)
    public String ContentUpload(@RequestParam(value = "assetContentInfo", required = true) String assetContentInfo) throws IOException {
        JSONObject resobj;
        String[] args = {assetContentInfo};
        try {
            resobj = fabricOperation.invokeOnFabric("ContentUpload", args);
        } catch (Exception e) {
            LOGGER.info("invokeChaincode异常:{}", e.getMessage());
            return new JsonRESTResult(e.getMessage()).encode();
        }
        String code = resobj.getString("code");
        if (code != null && !code.equals(SUCCESS_CODE)) {
            return new JsonRESTResult(ERROR, resobj.toJSONString()).encode();
        }
        return new JsonRESTResult(resobj).encode();
    }

    /*
         通过机构ID查询对应的数据目录
     */
    @RequestMapping(value = "/ContentSearchById", method = RequestMethod.POST)
    public String ContentSearchById(@RequestParam(value = "assetContentId", required = true) String assetContentId) throws IOException {
        JSONObject resobj;
        String[] args = {assetContentId};
        try {
            resobj = fabricOperation.queryOnFabric("ContentSearchById", args);
        } catch (Exception e) {
            LOGGER.info("invokeChaincode异常:{}", e.getMessage());
            return new JsonRESTResult(e.getMessage()).encode();
        }
        String code = resobj.getString("code");
        if (code != null && !code.equals(SUCCESS_CODE)) {
            return new JsonRESTResult(ERROR, resobj.toJSONString()).encode();
        }
        return new JsonRESTResult(resobj).encode();
    }

    /*
        删除机构的数据目录
     */
    @RequestMapping(value = "/ContentDelete", method = RequestMethod.POST)
    public String ContentDelete(@RequestParam(value = "assetContentId", required = true) String assetContentId) throws IOException {
        JSONObject resobj;
        String[] args = {assetContentId};
        try {
            resobj = fabricOperation.invokeOnFabric("ContentDelete", args);
        } catch (Exception e) {
            LOGGER.info("invokeChaincode异常:{}", e.getMessage());
            return new JsonRESTResult(e.getMessage()).encode();
        }
        String code = resobj.getString("code");
        if (code != null && !code.equals(SUCCESS_CODE)) {
            return new JsonRESTResult(ERROR, resobj.toJSONString()).encode();
        }
        return new JsonRESTResult(resobj).encode();
    }

    /*
        各委办局数据哈希上链、修改
     */
    @RequestMapping(value = "/DataUpload1", method = RequestMethod.POST)
    public String DContentUpload(@RequestParam(value = "demandInfo", required = true) String demandInfo) throws IOException {
        JSONObject resobj;
        String reshash;
        String date;
        try {
            reshash = IpfsUtils.upload(demandInfo);
            System.out.println("IPFS返回hash:" + reshash);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            date=df.format(new Date());
            String[] inputArgs = {YYS_ID, reshash, date,verification_data};
            resobj = fabricOperation.invokeOnFabric("RequirementUpload", inputArgs);
        } catch (Exception e) {
            LOGGER.info("invokeChaincode异常:{}", e.getMessage());
            return new JsonRESTResult(e.getMessage()).encode();
        }
        String code = resobj.getString("code");
        if (code != null && !code.equals(SUCCESS_CODE)) {
            return new JsonRESTResult(ERROR, resobj.toJSONString()).encode();
        }
        String txId=resobj.getString("txId");
        System.out.println("本次交易ID："+txId);
        //数据存数据库
        tInfo.setDepartmentId(YYS_ID);
        tInfo.setEventTime(date);
        tInfo.setFlag(upload_flag);
        tInfo.setTxId(txId);
        tInfo.setDataHash(reshash);
        tInfo.setDataOwns(YYS_ID);
        tInfo.setDataType(verification_data);
        tInfo.setDepartmentName("运营商");
        boolean b1=service.insertInfo(tInfo);
        if (b1) {
            return new JsonRESTResult(resobj).encode();
        }
        return null;
    }

    /*
        上链查询数据哈希------->IPFS取数据        各个委办局id在项目启动时进行初始化
     */
    @RequestMapping(value = "/DContentSearchById1", method = RequestMethod.POST)
    public String DContentSearchById(@RequestParam(value = "dataownsid", required = true) String dataownsid) throws IOException {
        JSONObject resobj;
        String[] args = {YYS_ID,dataownsid};
        try {
            resobj = fabricOperation.queryOnFabric("RequirementSearchById", args);
        } catch (Exception e) {
            LOGGER.info("invokeChaincode异常:{}", e.getMessage());
            return new JsonRESTResult(e.getMessage()).encode();
        }
        String code = resobj.getString("code");
        if (code != null && !code.equals(SUCCESS_CODE)) {
            return new JsonRESTResult(ERROR, resobj.toJSONString()).encode();
        }
        String data = resobj.getString("data");
        JSONObject jsonObject1 = JSONObject.parseObject(data);
        //取出哈希值
        String hash = jsonObject1.get("hashinfo").toString();
        System.out.println("返回哈希：" + hash);
        String jsonStr = IpfsUtils.download(hash);
        System.out.println("返回数据："+jsonStr);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date=df.format(new Date());
        //数据存数据库
        tInfo.setDepartmentId(YYS_ID);
        tInfo.setEventTime(date);
        tInfo.setFlag(query_flag);
        tInfo.setTxId("");
        tInfo.setDataHash(hash);
        tInfo.setDataOwns(dataownsid);
        tInfo.setDataType(verification_data);
        tInfo.setDepartmentName("运营商");
        boolean b1=service.insertInfo(tInfo);
        if (b1) {
            return jsonStr;
        }
        return null;
    }

    @RequestMapping(value = "/DContentDelete", method = RequestMethod.POST)
    public String DContentDelete(@RequestParam(value = "departmentid", required = true) String departmentid) throws IOException {
        JSONObject resobj;
        String[] args = {departmentid};
        try {
            resobj = fabricOperation.invokeOnFabric("RequirementDelete", args);
        } catch (Exception e) {
            LOGGER.info("invokeChaincode异常:{}", e.getMessage());
            return new JsonRESTResult(e.getMessage()).encode();
        }
        String code = resobj.getString("code");
        if (code != null && !code.equals(SUCCESS_CODE)) {
            return new JsonRESTResult(ERROR, resobj.toJSONString()).encode();
        }
        return new JsonRESTResult(resobj).encode();
    }
    //测试
    @RequestMapping(value = "/TContentSearchById", method = RequestMethod.POST)
    public String TContentSearchById(@RequestParam(value = "dataownsid", required = true) String dataownsid) throws IOException {
        JSONObject resobj;
        String[] args = {YYS_ID,dataownsid};
        try {
            resobj = fabricOperation.queryOnFabric("RequirementSearchById", args);
        } catch (Exception e) {
            LOGGER.info("invokeChaincode异常:{}", e.getMessage());
            return new JsonRESTResult(e.getMessage()).encode();
        }
        String code = resobj.getString("code");
        if (code != null && !code.equals(SUCCESS_CODE)) {
            return new JsonRESTResult(ERROR, resobj.toJSONString()).encode();
        }
        String data = resobj.getString("data");
        JSONObject jsonObject1 = JSONObject.parseObject(data);
        //取出哈希值
        String hash = jsonObject1.get("hashinfo").toString();
        System.out.println("返回哈希：" + hash);
        String jsonStr = IpfsUtils.download(hash);
        System.out.println("返回数据："+jsonStr);
        return jsonStr;
    }
        /*
          数据加密，加密数据哈希上链、修改
       */
    @RequestMapping(value =  "/DataUpload", method = RequestMethod.POST)
    public String EncodeDContentUpload(@RequestParam(value ="demandInfo",required = true)String demandInfo) throws IOException {
        JSONObject resobj;
        String reshash;
        String data;
        try {
            //数据加密
            String strKeyEncode = yanglaoAppData.getDepartmentData(YYS_ID).getStrKey();
            LOGGER.info("委办局信息：{}",yanglaoAppData.getDepartmentData(YYS_ID));
            String content = demandInfo;
            String EncodeData=AESEncode(strKeyEncode, content);
            LOGGER.info("根据输入的委办局信息:{},加密后的密文是:{}",strKeyEncode, EncodeData);
            //上传到IPFS
            reshash = IpfsUtils2.upload(EncodeData);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            data=df.format(new Date());
            String [] inputArgs={YYS_ID,reshash,df.format(new Date()),verification_data};
            //哈希上链
            resobj = fabricOperation.invokeOnFabric("RequirementUpload",inputArgs);
        }catch(Exception e){
            LOGGER.info("invokeChaincode异常:{}",e.getMessage());
            return new JsonRESTResult(e.getMessage()).encode();
        }
        String code = resobj.getString("code");
        if(code!=null && !code.equals(SUCCESS_CODE)){
            return  new JsonRESTResult(ERROR, resobj.toJSONString()).encode();
        }
        String txId=resobj.getString("txId");
        //数据存数据库
        tInfo.setDepartmentId(YYS_ID);
        tInfo.setEventTime(data);
        tInfo.setFlag(upload_flag);
        tInfo.setTxId(txId);
        tInfo.setDataHash(reshash);
        tInfo.setDataOwns(YYS_ID);
        tInfo.setDataType(verification_data);
        tInfo.setDepartmentName("运营商");
        boolean b1=service.insertInfo(tInfo);
        if (b1) {
            return new JsonRESTResult(resobj).encode();
        }
        return new JsonRESTResult(resobj).encode();
    }

    /**
     * 验证结果上链
     * @param demandInfo
     * @return
     * @throws IOException
     */
    @RequestMapping(value =  "/ResultUpload", method = RequestMethod.POST)
    public String ResultUpload(@RequestParam(value ="demandInfo",required = true)String demandInfo) throws IOException {
        JSONObject resobj;
        String reshash;
        String data;
        try {
            //数据加密
            String strKeyEncode = yanglaoAppData.getDepartmentData(YYS_ID).getStrKey();
            LOGGER.info("委办局信息：{}",yanglaoAppData.getDepartmentData(YYS_ID));
            String content = demandInfo;
            String EncodeData=AESEncode(strKeyEncode, content);
            LOGGER.info("根据输入的委办局信息:{},加密后的密文是:{}",strKeyEncode, EncodeData);
            //上传到IPFS
            //EncodeData = EncodeData.replaceAll("\r\n", "");
            reshash = IpfsUtils2.upload(EncodeData);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            data=df.format(new Date());
            String [] inputArgs={YYS_ID,reshash,df.format(new Date()),verification_result};
            //哈希上链
            resobj = fabricOperation.invokeOnFabric("RequirementUpload",inputArgs);
        }catch(Exception e){
            LOGGER.info("invokeChaincode异常:{}",e.getMessage());
            return new JsonRESTResult(e.getMessage()).encode();
        }
        String code = resobj.getString("code");
        if(code!=null && !code.equals(SUCCESS_CODE)){
            return  new JsonRESTResult(ERROR, resobj.toJSONString()).encode();
        }
        String txId=resobj.getString("txId");
        //数据存数据库
        tInfo.setDepartmentId(YYS_ID);
        tInfo.setEventTime(data);
        tInfo.setFlag(upload_flag);
        tInfo.setTxId(txId);
        tInfo.setDataHash(reshash);
        tInfo.setDataOwns(YYS_ID);
        tInfo.setDataType(verification_result);
        tInfo.setDepartmentName("运营商");
        boolean b1=service.insertInfo(tInfo);
        if (b1) {
            return new JsonRESTResult(resobj).encode();
        }
        return new JsonRESTResult(resobj).encode();
    }
    /*
        上链查询加密数据哈希------->IPFS取加密数据
     */
    @RequestMapping(value =  "/DContentSearchById", method = RequestMethod.POST)
    public String DncodeDContentSearchById(@RequestParam(value ="departmentid",required = true)String departmentid) throws IOException {
        JSONObject resobj;
        String[] args = {YYS_ID, departmentid};
        try {
            resobj = fabricOperation.queryOnFabric("RequirementSearchById", args);
        } catch (Exception e) {
            LOGGER.info("invokeChaincode异常:{}", e.getMessage());
            return new JsonRESTResult(e.getMessage()).encode();
        }
        String code = resobj.getString("code");
        if (code != null && !code.equals(SUCCESS_CODE)) {
            return new JsonRESTResult(ERROR, resobj.toJSONString()).encode();
        }
        String data = resobj.getString("data");
        JSONObject jsonObject1 = JSONObject.parseObject(data);
        //取出哈希值
        String hash = jsonObject1.get("hashinfo").toString();
        String jsonStr = IpfsUtils2.download(hash);
        //jsonStr  为IPFS 取出数据
        //数据解密
        String strKeyDncode = yanglaoAppData.getDepartmentData(departmentid).getStrKey();
        LOGGER.info("委办局信息：{}", yanglaoAppData.getDepartmentData(departmentid));
        String content = jsonStr;
        content.replaceAll("\r\n", "");
        String DncodeData = AESDncode(strKeyDncode, content);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());
        //数据存数据库
        tInfo.setDepartmentId(YYS_ID);
        tInfo.setEventTime(date);
        tInfo.setFlag(query_flag);
        tInfo.setTxId("");
        tInfo.setDataHash(hash);
        tInfo.setDataOwns(departmentid);
        tInfo.setDataType(verification_data);
        tInfo.setDepartmentName("运营商");
        boolean b1 = service.insertInfo(tInfo);
        if (b1) {
            return DncodeData;
        }
        return DncodeData;
    }
}
