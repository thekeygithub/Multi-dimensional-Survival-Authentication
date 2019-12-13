package com.xcuni.guizhouyl.rest.entity;

import com.xcuni.guizhouyl.data.entity.YanglaoUserInfoEntity;
import lombok.Data;

@Data
public class InvokerSingleUserVerifyEntity {
    private String date;
    private YanglaoUserInfoEntity userInfo;
}
