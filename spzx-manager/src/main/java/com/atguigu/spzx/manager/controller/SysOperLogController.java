package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysOperLogService;
import com.atguigu.spzx.model.dto.system.SysOperLogDto;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/system/sysoperlog")
public class SysOperLogController {
    @Autowired
    private SysOperLogService sysOperLogService;

    @GetMapping("/{pageNum}/{pageSize}")
    public Result findPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize, SysOperLogDto sysOperLogDto){
        PageInfo pageInfo = sysOperLogService.findPage(pageNum,pageSize,sysOperLogDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

}
