package com.xcuni.guizhouyl.service;

import com.xcuni.guizhouyl.model.StatisticalInfo;
import com.xcuni.guizhouyl.model.UserInfo;
import com.xcuni.guizhouyl.model.traceInfo;

import java.util.List;


public interface UserInfoService {

    void insertInfo(List<UserInfo> ulist);
    void updateInfo(List<UserInfo> ulist);
}
