package com.atguigu.spzx.common.service.exception;

import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GuiguGlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result doResolveException(Exception e){
        e.printStackTrace();
        return Result.build(null,ResultCodeEnum.SYSTEM_ERROR);
    }

    //处理GuiguException的方法
    @ExceptionHandler(value = GuiguException.class)
    public Result doResolveGuiguException(GuiguException e){
        e.printStackTrace();
        return Result.build(null,e.getCode(),e.getMessage());
    }

}
