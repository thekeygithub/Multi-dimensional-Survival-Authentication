package com.xcuni.guizhouyl.service.impl;


import com.xcuni.guizhouyl.dao.TraceMapper;
import com.xcuni.guizhouyl.model.ReturnInfo;
import com.xcuni.guizhouyl.model.StatisticalInfo;
import com.xcuni.guizhouyl.model.traceInfo;
import com.xcuni.guizhouyl.service.TraceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.xcuni.guizhouyl.ApplicationSetup.*;


@Service
@Transactional(rollbackFor = Exception.class)
public class TraceInfoServiceImpl implements TraceInfoService {
    @Autowired
    private TraceMapper mapper;
    @Autowired
    private ReturnInfo rinfo;
    @Override
    public boolean insertInfo(traceInfo info) {
        mapper.insertInfo(info);
        return true;
    }

    @Override
    public List<StatisticalInfo> getStaInfo() {

        List<StatisticalInfo> slist=mapper.getStaInfo();

        return slist;
    }

    @Override
    public List<traceInfo> getDetailInfo() {
        return mapper.getDetailInfo();
    }

    @Override
    public int getCount() {
        return mapper.getCount();
    }
}
