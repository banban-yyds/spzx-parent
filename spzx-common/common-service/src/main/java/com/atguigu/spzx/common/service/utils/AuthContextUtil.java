package com.atguigu.spzx.common.service.utils;

import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.entity.user.UserInfo;

public class AuthContextUtil {
    // 创建ThreadLocal -> set -> get -> remove
    private static final ThreadLocal<SysUser> threadLocal = new ThreadLocal<>();

    public static void setThreadLocal(SysUser sysUser){
         threadLocal.set(sysUser);
    }

    public static SysUser getThreadLocal() {
        SysUser sysUser = threadLocal.get();
        return sysUser;
    }

    public static void remove(){
        threadLocal.remove();
    }

    private static final ThreadLocal<UserInfo> userInfoThreadLocal = new ThreadLocal<>() ;


    // 定义存储数据的静态方法
    public static void setUserInfo(UserInfo userInfo) {
        userInfoThreadLocal.set(userInfo);
    }

    // 定义获取数据的方法
    public static UserInfo getUserInfo() {
        return userInfoThreadLocal.get() ;
    }

    // 删除数据的方法
    public static void removeUserInfo() {
        userInfoThreadLocal.remove();
    }
}
