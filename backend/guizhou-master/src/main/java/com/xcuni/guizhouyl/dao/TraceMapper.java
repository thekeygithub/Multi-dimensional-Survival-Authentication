package com.xcuni.guizhouyl.dao;

import com.xcuni.guizhouyl.model.StatisticalInfo;
import com.xcuni.guizhouyl.model.traceInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TraceMapper {
    void insertInfo(traceInfo info);
    List<StatisticalInfo> getStaInfo();
    List<traceInfo> getDetailInfo();
    int getCount();
}
