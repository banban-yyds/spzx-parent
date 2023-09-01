package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper {
    List<SysMenu> findAllNode();

    void addMenuNode(SysMenu sysMenu);

    void updateMenuNode(SysMenu sysMenu);

    SysMenu findMenuNodeById(Long id);

    Integer findChildNode(Long id);
    void deleteMenuNode(Long id);

    List<SysMenu> getAllMenuTreeNode();

    List<Long> getSysRoleMenuIdsByRoleId(Long roleId);

    void deleteMenuByRoleId(Long roleId);

    void assignMenus(AssginMenuDto assginMenuDto);
}
