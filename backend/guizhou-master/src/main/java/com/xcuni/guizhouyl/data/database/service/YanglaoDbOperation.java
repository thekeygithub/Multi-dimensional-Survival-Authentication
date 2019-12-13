package com.xcuni.guizhouyl.data.database.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
public class YanglaoDbOperation {
    @Autowired
    private TblUserStatusService userStatusService;
    @Autowired
    private TblDeathDataSrcService deathDataSrcService;
}
