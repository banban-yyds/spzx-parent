package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper {
    List<SysRole> findPage(SysRoleDto sysRoleDto);

    void addSysRole(SysRole sysRole);

    void updateByid(SysRole sysRole);

    void deleteById(Long id);

    SysRole getSysRole(Long id);

    List<SysRole> findAllRole();
}
