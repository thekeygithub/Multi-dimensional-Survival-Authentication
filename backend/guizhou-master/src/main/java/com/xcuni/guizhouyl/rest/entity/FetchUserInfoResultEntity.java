package com.xcuni.guizhouyl.rest.entity;

import com.xcuni.guizhouyl.data.entity.YanglaoUserInfoEntity;
import lombok.Data;

import java.util.List;

@Data
public class FetchUserInfoResultEntity {
    private String Code;
    private String Message;
    private FetchUserInfoPageEntity PageInfo;
    private List<YanglaoUserInfoEntity> UserInfoList;
}
