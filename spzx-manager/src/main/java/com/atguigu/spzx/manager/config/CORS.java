package com.atguigu.spzx.manager.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootConfiguration  // springmvc的配置类
public class CORS implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/**")  // 请求路径
                .allowCredentials(true)        // 允许cookie
                .allowedOriginPatterns("*")    // 请求源：协议+端口
                .allowedHeaders("*")           // 请求头：content-type。。。。。
                .allowedMethods("*");          // 请求方式
    }
}
