package com.atguigu.spzx.manager.controller;

import cn.hutool.db.Page;
import com.atguigu.spzx.common.log.annotation.Log;
import com.atguigu.spzx.manager.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("/findPage/{pageNum}/{pageSize}")
    public Result<PageInfo<SysRole>> findPage(@PathVariable(value = "pageNum")Integer pageNum,
                                              @PathVariable(value = "pageSize")Integer pageSize,
                                              SysRoleDto sysRoleDto){

        PageInfo<SysRole> page = sysRoleService.findPage(pageNum, pageSize, sysRoleDto);
        log.info(page.toString());
        return Result.build(page, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/addSysRole")
    public Result addSysRole(@RequestBody SysRole sysRole){
        log.info(sysRole.toString());
        sysRoleService.addSysRole(sysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

   @Log(title = "修改角色")
    @PutMapping("/updateByid")
    public Result updateByid(@RequestBody SysRole sysRole){
        sysRoleService.updateByid(sysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable(value = "id") Long id){
        sysRoleService.deleteById(id);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/getSysRole/{id}")
    public Result getSysRole(@PathVariable(value = "id") Long id){
        SysRole sysRole = sysRoleService.getSysRole(id);
        return Result.build(sysRole,ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/toAssignPage/{userId}") //
    public Result toAssignPage(@PathVariable(value = "userId")Integer userId){
        Map<String, Object> map = sysRoleService.toAssignPage(userId);
        return Result.build(map, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/assignRoles")
    public Result assignRoles(@RequestBody AssginRoleDto assginRoleDto){
        sysRoleService.assignRoles(assginRoleDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


}
