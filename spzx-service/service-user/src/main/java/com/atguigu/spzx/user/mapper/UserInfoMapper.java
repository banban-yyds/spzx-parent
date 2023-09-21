package com.atguigu.spzx.user.mapper;


import com.atguigu.spzx.model.entity.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper {


    UserInfo getUserInfoByuserName(String username);

    void saveUserInfo(UserInfo userInfo);

    void updateUserInfo(UserInfo updateUserInfo);
}
