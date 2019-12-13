package com.xcuni.guizhouyl.rest.controller;

import com.xcuni.guizhouyl.model.ReturnInfo;
import com.xcuni.guizhouyl.model.StatisticalInfo;
import com.xcuni.guizhouyl.model.UserInfo;
import com.xcuni.guizhouyl.model.traceInfo;
import com.xcuni.guizhouyl.service.TraceInfoService;
import com.xcuni.guizhouyl.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/trace")
public class TraceInfoController {
    final static Logger LOGGER = LoggerFactory.getLogger(FabricChaincodeController.class);
    @Autowired
    TraceInfoService service;
    @Autowired
    UserInfoService uservice;

    @RequestMapping(value = "/getStaInfo", method = RequestMethod.POST)
    public Object getStaInfo()  {
        List<StatisticalInfo> slist=service.getStaInfo();
        return slist;
    }

    @RequestMapping(value = "/getDetailInfo", method = RequestMethod.POST)
    public Object getDetailInfo()  {
        List<traceInfo> tlist=service.getDetailInfo();
        return tlist;
    }
    //测试批量插入
    @RequestMapping(value = "/testInsert", method = RequestMethod.POST)
    public Object testInsert()  {
        List<UserInfo> ulist=new ArrayList<>();
        for (int i=10;i<20;i++){
            UserInfo u=new UserInfo(i,"test"+i,i);
            ulist.add(u);
        }
        uservice.insertInfo(ulist);
        return "ok";
    }
    //测试批量更新
    @RequestMapping(value = "/testUpdate", method = RequestMethod.POST)
    public Object testUpdate()  {
        List<UserInfo> ulist=new ArrayList<>();
        for (int i=0;i<10;i++){
            UserInfo u=new UserInfo(i,"admin"+i,i);
            ulist.add(u);
        }
        uservice.updateInfo(ulist);
        return "ok";
    }
}
