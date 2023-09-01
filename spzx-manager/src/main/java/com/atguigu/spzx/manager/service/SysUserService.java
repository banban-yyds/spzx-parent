package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface SysUserService {

    PageInfo<SysUser> findUserPage(Integer pageNum, Integer pageSize,SysUserDto sysUserDto);

    void addUser(SysUser sysUser);

    void updateUser(SysUser sysUser);

    void deleteUserById(Integer id);

    SysUser getUserById(Integer id);

    LoginVo login(LoginDto loginDto);

    SysUser getUserInfo(String token);

    void logout(String string);

    List<SysMenuVo> getSysUserMenuByUserId(Long userId);
}
