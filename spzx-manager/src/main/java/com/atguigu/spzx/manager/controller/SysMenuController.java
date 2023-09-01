package com.atguigu.spzx.manager.controller;


import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/findAllNode")
    public Result findAllNode(){
        List<SysMenu> menuList = sysMenuService.findAllNode();
        return Result.build(menuList, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/addMenuNode")
    public Result addMenuNode(@RequestBody SysMenu sysMenu){
        sysMenuService.addMenuNode(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @PutMapping("/updateMenuNode")
    public Result updateMenuNode(@RequestBody SysMenu sysMenu){
        sysMenuService.updateMenuNode(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/findMenuNodeById/{id}")
    public Result findMenuNodeById(@PathVariable(value = "id") Long id){
        SysMenu sysMenu = sysMenuService.findMenuNodeById(id);
        return Result.build(sysMenu, ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/deleteMenuNode/{id}")
    public Result deleteMenuNode(@PathVariable(value = "id")Long id){
        sysMenuService.deleteMenuNode(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    // =============菜单分配=============
    @GetMapping("/getSysMenuTreeByRoleId/{roleId}")
    public Result toAssignMenuPage(@PathVariable(value = "roleId")Long roleId){
        Map<String,Object> map = sysMenuService.toAssignMenuPage(roleId);
        return Result.build(map, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/assignMenus")
    public Result assignMenus(@RequestBody AssginMenuDto assginMenuDto){
        sysMenuService.assignMenus(assginMenuDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
