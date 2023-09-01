package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.atguigu.spzx.common.service.exception.GuiguException;
import com.atguigu.spzx.common.service.utils.MenuHelper;
import com.atguigu.spzx.manager.mapper.SysMenuMapper;
import com.atguigu.spzx.manager.mapper.SysRoleMenuMapper;
import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SysMenuServiceimpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Override
    public List<SysMenu> findAllNode() {
        // 查询所有的菜单节点
        List<SysMenu> menuList = sysMenuMapper.findAllNode();
        // 封装树形菜单
        //   判空
        //   调用MenuHelper工具类封装 - 递归查询所有父节点的所有子节点目录，返回父节点List
        if (CollectionUtils.isEmpty(menuList)) {
            return menuList;
        }
        List<SysMenu> parentNodeList = MenuHelper.buildTree(menuList);

        return parentNodeList;
    }

    @Override
    public void addMenuNode(SysMenu sysMenu) {
        sysMenuMapper.addMenuNode(sysMenu);
    }

    @Override
    public void updateMenuNode(SysMenu sysMenu) {
        sysMenuMapper.updateMenuNode(sysMenu);
    }

    @Override
    public SysMenu findMenuNodeById(Long id) {
        SysMenu sysMenu = sysMenuMapper.findMenuNodeById(id);
        return sysMenu;
    }

    @Override
    public void deleteMenuNode(Long id) {
        Integer count = sysMenuMapper.findChildNode(id);
        if (count > 0){
            throw new GuiguException(ResultCodeEnum.IS_PARENT);
        }else
            sysMenuMapper.deleteMenuNode(id);
    }

    @Override
    public Map<String, Object> toAssignMenuPage(Long roleId) {
        // 1.查询所有的菜单
        List<SysMenu> allMenuList = sysMenuMapper.getAllMenuTreeNode();
        //      将查询的所有菜单节点 转换位菜单树
        List<SysMenu> menuTreeList = MenuHelper.buildTree(allMenuList);
        // 2.根据角色id 获取角色已分配的菜单id
        List<Long> sysRoleMenuIds = sysMenuMapper.getSysRoleMenuIdsByRoleId(roleId);
        Map<String,Object> map = new HashMap<>();
        map.put("sysMenuTree",menuTreeList);
        map.put("sysRoleMenuIds",sysRoleMenuIds);
        return map;
    }

    @Override
    public void assignMenus(AssginMenuDto assginMenuDto) {
        Long roleId = assginMenuDto.getRoleId();
        sysMenuMapper.deleteMenuByRoleId(roleId);
        sysMenuMapper.assignMenus(assginMenuDto);
    }
}
