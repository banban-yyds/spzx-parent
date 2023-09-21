package com.atguigu.spzx.user.controller;

import com.atguigu.spzx.model.entity.h5.UserAddress;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.user.service.UserAddressService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/api/user/userAddress")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @Operation(summary = "获取用户所有的地址")
    @GetMapping("/auth/findUserAddressList")
    public Result findUserAddressList(){
        //调用UserAddressService中获取用户所有地址的方法
        List<UserAddress> userAddressList = userAddressService.findUserAddressList();
        return Result.build(userAddressList, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "供订单微服务远程调用的接口")
    @GetMapping("/getUserAddressById/{id}")
    public UserAddress getUserAddressById(@PathVariable Long id){
        //调用UserAddressService中根据id获取地址的方法
        UserAddress userAddress = userAddressService.getUserAddressById(id);
        return userAddress;
    }
}
