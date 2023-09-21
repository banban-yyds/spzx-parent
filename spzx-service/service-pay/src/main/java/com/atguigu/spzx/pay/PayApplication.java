package com.atguigu.spzx.pay;

import com.atguigu.spzx.common.service.anno.EnableKnife4j;
import com.atguigu.spzx.pay.properties.AlipayProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableKnife4j
@ComponentScan(value = "com.atguigu.spzx")
@EnableFeignClients(basePackages = "com.atguigu.spzx.feign.order")
@EnableConfigurationProperties(value = AlipayProperties.class)
public class PayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }
}
