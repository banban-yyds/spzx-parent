package com.atguigu.spzx.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.List;
@Data
@ConfigurationProperties(prefix = "spzx.auth")
public class NoAuthProperties {
    private List<String> noAuthUrls;
}
