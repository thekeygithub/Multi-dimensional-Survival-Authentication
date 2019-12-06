package com.xcuni.guizhouyl.service;

import com.xcuni.guizhouyl.model.ReturnInfo;
import com.xcuni.guizhouyl.model.StatisticalInfo;
import com.xcuni.guizhouyl.model.traceInfo;

import java.util.List;


public interface TraceInfoService {

    boolean insertInfo(traceInfo info);
    List<StatisticalInfo> getStaInfo();
    List<traceInfo> getDetailInfo();
    int getCount();
}
