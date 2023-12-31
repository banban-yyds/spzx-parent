package com.atguigu.spzx.common.service.anno;

import com.atguigu.spzx.common.service.config.Knife4jConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Import(value = Knife4jConfig.class)
public @interface EnableKnife4j {

}