package com.atguigu.spzx.common.log.service;

import com.atguigu.spzx.model.entity.system.SysOperLog;

public interface AsyncSysOperLogService {
    // 用于保存日志信息
    void saveOperLog(SysOperLog sysOperLog);
}
