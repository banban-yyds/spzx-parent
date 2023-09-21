package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.common.log.service.AsyncSysOperLogService;
import com.atguigu.spzx.manager.mapper.AsyncSysOperLogMapper;
import com.atguigu.spzx.model.entity.system.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsyncSysOperLogServiceimpl implements AsyncSysOperLogService {
    @Autowired
    private AsyncSysOperLogMapper asyncSysOperLogMapper;

    @Override
    public void saveOperLog(SysOperLog sysOperLog) {
        asyncSysOperLogMapper.saveOperLog(sysOperLog);
    }
}
