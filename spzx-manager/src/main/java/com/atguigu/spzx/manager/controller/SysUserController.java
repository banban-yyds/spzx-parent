package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/admin/system/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/findUserPage/{pageNum}/{pageSize}")
    public Result<PageInfo<SysUser>> findUserPage(@PathVariable(value = "pageNum")Integer pageNum,
                                                  @PathVariable(value = "pageSize")Integer pageSize,
                                                 SysUserDto sysUserDto){
        PageInfo<SysUser> pageInfo = sysUserService.findUserPage(pageNum,pageSize,sysUserDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/addUser")
    public Result addUser(@RequestBody SysUser sysUser){
        log.info(sysUser.toString());
        sysUserService.addUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @PutMapping("/updateUser")
    public Result updateUser(@RequestBody SysUser sysUser){
        log.info(sysUser.toString());
        sysUserService.updateUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/deleteUserById/{id}")
    public Result deleteUserById(@PathVariable(value = "id")Integer id){
        sysUserService.deleteUserById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/getUserById/{id}")
    public Result getUserById(@PathVariable(value = "id") Integer id){
        SysUser sysUser = sysUserService.getUserById(id);
        log.info(sysUser.toString());
        return Result.build(sysUser, ResultCodeEnum.SUCCESS);
    }


}
