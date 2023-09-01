package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;

import java.util.List;
import java.util.Map;

public interface SysMenuService {
    List<SysMenu> findAllNode();

    void addMenuNode(SysMenu sysMenu);

    void updateMenuNode(SysMenu sysMenu);

    SysMenu findMenuNodeById(Long id);

    void deleteMenuNode(Long id);

    Map<String,Object> toAssignMenuPage(Long roleId);

    void assignMenus(AssginMenuDto assginMenuDto);
}
