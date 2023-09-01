package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.SysRoleMapper;
import com.atguigu.spzx.manager.mapper.SysUserRoleMapper;
import com.atguigu.spzx.manager.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.entity.system.SysUserRole;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SysRoleServiceimpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public PageInfo<SysRole> findPage(Integer pageNum,Integer pageSize,SysRoleDto sysRoleDto) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysRole> sysRoleList = sysRoleMapper.findPage(sysRoleDto);
        PageInfo<SysRole> pageInfo = new PageInfo<>(sysRoleList);
        return pageInfo;
    }

    @Override
    public void addSysRole(SysRole sysRole) {
        sysRoleMapper.addSysRole(sysRole);
    }

    @Override
    public void deleteById(Long id) {
        sysRoleMapper.deleteById(id);
    }

    public void updateByid(SysRole sysRole){
        sysRoleMapper.updateByid(sysRole);
    }

    @Override
    public SysRole getSysRole(Long id) {
        return sysRoleMapper.getSysRole(id);
    }

    @Override
    public Map<String, Object> toAssignPage(Integer userId) {   // 查询用户的拥有的 角色权限
        // 1.获取所有的角色 查询 role表
        List<SysRole> allRoles = sysRoleMapper.findAllRole();
        // 2.获取当前用户的角色Id - 查询中间表user-role
        List<SysUserRole> userRoleList = sysUserRoleMapper.findRoleByUserId(userId);
        List<Long> userRoleIds = new ArrayList<>();
        for (SysUserRole userRoleId : userRoleList) {
             userRoleIds.add(userRoleId.getRoleId());
        }
        // 3.封装map集合返回
        Map<String, Object> map = new HashMap<>();
        map.put("allRoles",allRoles);
        map.put("userRoleIds",userRoleIds);

        return map;
    }

    @Override
    public void assignRoles(AssginRoleDto assginRoleDto) {
        Long userId = assginRoleDto.getUserId();
        sysUserRoleMapper.deleteUserRoleByUserId(userId);

        List<Long> roleIdList = assginRoleDto.getRoleIdList();
        if (roleIdList != null && roleIdList.size() > 0){
            sysUserRoleMapper.batchAddRoleId(assginRoleDto);
        }

    }
}
