package com.xcuni.guizhouyl.dao;


import com.xcuni.guizhouyl.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserMapper {
    void insertInfo(List<UserInfo> ulist);
    void updateInfo(List<UserInfo> ulist);
}
