package com.atguigu.spzx.user.controller;

import com.atguigu.spzx.model.dto.user.UserLoginDto;
import com.atguigu.spzx.model.dto.user.UserRegisterDto;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;

import com.atguigu.spzx.model.vo.user.UserInfoVo;
import com.atguigu.spzx.user.service.SmsService;
import com.atguigu.spzx.user.service.UserInfoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private SmsService smsService;

    @GetMapping("/sms/sendCode/{phone}")
    public Result sendValidateCode(@PathVariable(value = "phone") String phone){
        smsService.sendValidateCode(phone);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/userInfo/register")
    public Result register(@RequestBody UserRegisterDto userRegisterDto){
        userInfoService.register(userRegisterDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/userInfo/login")
    public Result login(@RequestBody UserLoginDto userLoginDto, HttpServletRequest request){
        String token = userInfoService.login(userLoginDto,request);
        return Result.build(token, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/userInfo/auth/getCurrentUserInfo")
    public Result getCurrentUserInfo(HttpServletRequest request){
        UserInfoVo userInfoVo = userInfoService.getUserInfoBytoken(request);
        return Result.build(userInfoVo, ResultCodeEnum.SUCCESS);
    }
}
