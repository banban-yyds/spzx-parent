package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.SysOperLogMapper;
import com.atguigu.spzx.manager.service.SysOperLogService;
import com.atguigu.spzx.model.dto.system.SysOperLogDto;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SysOperLogServiceimpl implements SysOperLogService {
    @Autowired
    private SysOperLogMapper sysOperLogMapper;
    @Override
    public PageInfo findPage(Integer pageNum, Integer pageSize, SysOperLogDto sysOperLogDto) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo pageInfo = new PageInfo(sysOperLogMapper.findPage(sysOperLogDto));
        return pageInfo;
    }
}
