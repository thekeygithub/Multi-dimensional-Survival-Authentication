package com.xcuni.guizhouyl.service.impl;


import com.xcuni.guizhouyl.dao.TraceMapper;
import com.xcuni.guizhouyl.dao.UserMapper;
import com.xcuni.guizhouyl.model.ReturnInfo;
import com.xcuni.guizhouyl.model.StatisticalInfo;
import com.xcuni.guizhouyl.model.UserInfo;
import com.xcuni.guizhouyl.model.traceInfo;
import com.xcuni.guizhouyl.service.TraceInfoService;
import com.xcuni.guizhouyl.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserMapper mapper;
    @Override
    public void insertInfo(List<UserInfo> ulist) {
        mapper.insertInfo(ulist);
    }

    @Override
    public void updateInfo(List<UserInfo> ulist) {
        mapper.updateInfo(ulist);
    }
}
