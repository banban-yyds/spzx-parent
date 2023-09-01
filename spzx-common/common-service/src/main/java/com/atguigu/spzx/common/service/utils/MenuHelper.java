package com.atguigu.spzx.common.service.utils;

import com.atguigu.spzx.model.entity.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuHelper {
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList){
        List<SysMenu> trees = new ArrayList<>();

        for (SysMenu sysMenu : sysMenuList) {
            if (sysMenu.getParentId().longValue() == 0){
                // 添加前先查询子节点
                trees.add(findChild(sysMenu, sysMenuList));
            }
        }
        return trees;
    }

    // 递归查找所有的子节点放入 childList属性中 (子节点的子节点 - 树)
    public static SysMenu findChild(SysMenu sysMenu,List<SysMenu> treeNodes){
        sysMenu.setChildren(new ArrayList<SysMenu>());
        for (SysMenu it : treeNodes) {
            if (sysMenu.getId().longValue() == it.getParentId().longValue()){
                if (sysMenu.getChildren() == null){
                    sysMenu.setChildren(new ArrayList<>());
                }
                sysMenu.getChildren().add(findChild(it, treeNodes));
            }
        }
        return sysMenu;
    }
}
