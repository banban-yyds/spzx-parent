package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.system.SysOperLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AsyncSysOperLogMapper {
    void saveOperLog(SysOperLog sysOperLog);
}
