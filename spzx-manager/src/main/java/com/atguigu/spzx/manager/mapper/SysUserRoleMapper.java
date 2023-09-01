package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.entity.system.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserRoleMapper {
    List<SysUserRole> findRoleByUserId(Integer userId);

    void deleteUserRoleByUserId(Long userId);

    void batchAddRoleId(AssginRoleDto assginRoleDto);
}
