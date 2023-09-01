package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.vo.common.Result;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface SysRoleService {
    PageInfo<SysRole> findPage(Integer pageNum,Integer pageSize,SysRoleDto sysRoleDto);

    // insert
    void addSysRole(SysRole sysRole);

    // delete
    void deleteById(Long id);

    // update
    void updateByid(SysRole sysRole);

    // findById
    SysRole getSysRole(Long id);

    Map<String,Object> toAssignPage(Integer userId);

    void assignRoles(AssginRoleDto assginRoleDto);
}
