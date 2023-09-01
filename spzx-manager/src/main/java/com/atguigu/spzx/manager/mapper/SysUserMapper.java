package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface SysUserMapper {

    List<SysUser> findUserPage(SysUserDto sysUserDto);

    void addUser(SysUser sysUser);

    void deleteUserById(Integer id);

    void updateUser(SysUser sysUser);

    SysUser getUserById(Integer id);

    SysUser findSysUserByUsername(String username);


    List<SysMenu> getSysUserMenuByUserId(Long userId);
}
