package com.xcuni.guizhouyl.fabric;

import com.alibaba.fastjson.JSONObject;
import com.xcuni.guizhouyl.config.ChaincodeData;
import com.xcuni.guizhouyl.config.ChannelData;
import com.xcuni.guizhouyl.config.ConfigurationManager;
import com.xcuni.guizhouyl.config.FabricAppData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FabricManagerTest {

    @Autowired
    FabricAppData fabricAppData;
    @Autowired
    FabricManager manager;

    @Test
    public void testFabricManager() {
        manager.setup();
        String channelName = "mychannel";
        ChannelData chData = fabricAppData.getChannelData(channelName);
        ChaincodeData chaincodeData = chData.getChaincodeData("mycc");
        chaincodeData.setVersion("v1.0");
        //manager.queryChainCode(channelName,chaincodeData);
        JSONObject res = manager.invokeChaincode(channelName, chaincodeData);
        System.out.println("invoke result: " + res);
    }
}
