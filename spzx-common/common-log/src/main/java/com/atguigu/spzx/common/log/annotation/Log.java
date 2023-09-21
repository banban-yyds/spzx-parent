package com.atguigu.spzx.common.log.annotation;

import com.atguigu.spzx.common.log.enums.BusinessType;
import com.atguigu.spzx.common.log.enums.OperatorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    // 操作模块的名字
    String title();

    // 业务类型
    BusinessType businessType() default BusinessType.OTHER;

    // 操作人类型
    OperatorType operatorType() default OperatorType.MANAGE;

    // 是否保存请求
    boolean isSaveRequestData() default true;

    // 是否保存响应
    boolean isSaveResponseData() default true;
}
