package com.atguigu.spzx.user.service.impl;

import com.atguigu.spzx.user.service.SmsService;
import com.atguigu.spzx.user.utils.SmsUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SmsServiceimpl implements SmsService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public void sendValidateCode(String phone) {
        // 生成4位数的随机验证码
        String code = RandomStringUtils.randomNumeric(4);
        SmsUtils.SmsMessage(phone,code);
        // 放入缓存中
        redisTemplate.opsForValue().set("user:code:" + phone,code);

    }
}
