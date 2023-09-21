package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.SysOperLogDto;
import com.github.pagehelper.PageInfo;

public interface SysOperLogService {
    PageInfo findPage(Integer pageNum, Integer pageSize, SysOperLogDto sysOperLogDto);
}
