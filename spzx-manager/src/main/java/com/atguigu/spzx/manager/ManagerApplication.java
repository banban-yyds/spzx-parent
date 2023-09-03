package com.atguigu.spzx.manager;

import com.atguigu.spzx.manager.config.MyMinioProperties;
import com.atguigu.spzx.manager.properties.NoAuthProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@MapperScan("com.atguigu.spzx.manager.mapper")  // 用于扫描mapper接口，相当于在Mapper接口上添加了@Mapper注解，装配到spring中
@EnableConfigurationProperties(value = {NoAuthProperties.class, MyMinioProperties.class})
@ComponentScan(basePackages = "com.atguigu.spzx")
public class ManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }

}
