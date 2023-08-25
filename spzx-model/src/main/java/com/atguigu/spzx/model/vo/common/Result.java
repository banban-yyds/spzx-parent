package com.atguigu.spzx.model.vo.common;

import lombok.Data;

import java.io.Serializable;
@Data
public class Result<T>{
    private Integer code;
    private String message;
    private T data;

    // 私有化无参构造
    private Result(){}

    // 返回数据
    public static <T> Result<T> build(T body,Integer code,String message){
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setData(body);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> build(T body,ResultCodeEnum resultCodeEnum){
        return build(body,resultCodeEnum.getCode(),resultCodeEnum.getMessage());
    }
}
