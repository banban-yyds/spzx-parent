package com.atguigu.spzx.order;

import com.atguigu.spzx.common.service.anno.EnableKnife4j;
import com.atguigu.spzx.common.service.anno.EnableUserTokenFeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.atguigu.spzx")
@SpringBootApplication
@EnableKnife4j
@EnableFeignClients(basePackages ="com.atguigu.spzx.feign")
@EnableUserTokenFeignInterceptor
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class , args) ;
    }

}