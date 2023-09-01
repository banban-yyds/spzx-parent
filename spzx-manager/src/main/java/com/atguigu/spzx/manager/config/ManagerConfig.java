package com.atguigu.spzx.manager.config;

import com.atguigu.spzx.manager.intercetor.LoginAuthInterceptor;
import com.atguigu.spzx.manager.properties.NoAuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootConfiguration
public class ManagerConfig implements WebMvcConfigurer {
    @Autowired
    private LoginAuthInterceptor loginAuthInterceptor;
    @Autowired
    private NoAuthProperties noAuthProperties;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .allowedHeaders("*")
                .allowedMethods("*");
    }

    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(loginAuthInterceptor)
                .excludePathPatterns(noAuthProperties.getNoAuthUrls())
                .addPathPatterns("/**");
    }
}
