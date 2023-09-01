package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.common.service.utils.AuthContextUtil;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.manager.service.ValidateCodeService;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.atguigu.spzx.model.vo.system.ValidateCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ValidateCodeService validateCodeService;


    @PostMapping("/login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto){
        LoginVo login = sysUserService.login(loginDto);
        return Result.build(login, ResultCodeEnum.SUCCESS);
    }


    @GetMapping("/generateValidateCode") // 获取验证码
    public Result<ValidateCodeVo> generateValidateCode(){
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode(); //  生成验证码
        return Result.build(validateCodeVo, ResultCodeEnum.SUCCESS);  // 封装返回
    }


    @GetMapping("/getUserInfo")
    public Result<SysUser> getUserInfo(){
//        SysUser userInfo = sysUserService.getUserInfo(token);
        return Result.build(AuthContextUtil.getThreadLocal(), ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/logout")
    public Result logout(@RequestHeader(name = "token") String token){
        sysUserService.logout(token);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/getSysUserMenu")
    public Result getSysUserMenu(){
        Long userId = AuthContextUtil.getThreadLocal().getId();

        List<SysMenuVo> sysMenuVoList = sysUserService.getSysUserMenuByUserId(userId);
        return Result.build(sysMenuVoList,ResultCodeEnum.SUCCESS);
    }

}
