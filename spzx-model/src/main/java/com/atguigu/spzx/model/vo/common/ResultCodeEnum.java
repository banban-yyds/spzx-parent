package com.atguigu.spzx.model.vo.common;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {
    SUCCESS(200,"操作成功"),
    LOGIN_ERROR(201,"用户名或密码错误"),
    SYSTEM_ERROR(202,"用户不存在"),
    VALIDATECODE_ERROR(202 , "验证码错误"),
    CODE_ERROR(203,"不能为空"),
    PHONECODE_ERROR(206,"验证码不正确" ),
    LOGIN_AUTH(208,"登陆超时"),
    DATA_ERROR(209,"数据为空" ),
    IS_PARENT(207,"总有逆子想当爹"),

    PHONE_EXIST(210,"手机号已被注册" ), USER_NOT_FOUND(211,"用户不存在" ),
    STOCK_LESS(211,"库存不足" );

    private Integer code;
    private String message;

    private ResultCodeEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    }
}
