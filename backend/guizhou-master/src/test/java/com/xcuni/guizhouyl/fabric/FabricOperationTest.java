package com.xcuni.guizhouyl.fabric;

import com.alibaba.fastjson.JSONObject;
import com.xcuni.guizhouyl.utils.HttpUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FabricOperationTest {

    @Autowired
    FabricOperation fabricOperation;

    @Test
    public void testFabricInvokeOperation() {
        String func = "invoke";
        String[] args = new String[]{"a", "b", "10"};
        JSONObject resObj = fabricOperation.invokeOnFabric("mychannel", "mycc", func, args);
        System.out.println(resObj);
    }

    @Test
    public void testFabricInvokeVerfication() {
//        String func = "invoke";
//        String[] args = new String[]{ "a" };
//        JSONObject resObj = fabricOperation.queryOnFabric("mychannel","mycc",func,args);
//        System.out.println(resObj);
    }

    @Test
    public void testFabricQueryOperation() {
        String func = "query";
        String[] args = new String[]{"a"};
        JSONObject resObj = fabricOperation.queryOnFabric("mychannel", "mycc", func, args);
        System.out.println(resObj);
    }

    @Test
    public void testFabricQueryModel() {
        JSONObject resObj = fabricOperation.queryUserModelStatus("a", "mycc");
        System.out.println(resObj);
    }

    @Test
    public void testInvokeViaHttp() {
        String url = "http://127.0.0.1:8080/fabric/chaincode/invokeVerification";
        String id = "52252619271226421X";
        String usrDataJson = "{\"UserInfo\":{\"ID\":\"52252619271226421X\",\"Name\":\"王建全\"},\"Date\":\"2018-11-17\",\"UserDataList\":[{\"DataList\":[],\"DataSrc\":\"02\"},{\"DataList\":[{\"DataId\":\"03\",\"Message\":\"\",\"DateTime\":\"2018-10-19\",\"Result\":\"0\"}],\"DataSrc\":\"01\"},{\"DataList\":[],\"DataSrc\":\"04\"}]}";
        JSONObject reqObj = new JSONObject();
        reqObj.put("id", id);
        reqObj.put("date", "2018-11-22");
        reqObj.put("userDataJson", usrDataJson);
        String response = HttpUtils.sendPOST(url, reqObj);
        System.out.println(response);
    }
}
