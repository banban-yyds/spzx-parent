package com.atguigu.spzx.common.service.utils;

import com.atguigu.spzx.model.entity.system.SysUser;

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
}
