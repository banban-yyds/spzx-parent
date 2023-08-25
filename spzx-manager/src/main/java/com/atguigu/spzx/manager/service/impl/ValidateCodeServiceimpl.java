package com.atguigu.spzx.manager.service.impl;

import cn.hutool.Hutool;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;

import com.atguigu.spzx.manager.service.ValidateCodeService;
import com.atguigu.spzx.model.vo.system.ValidateCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ValidateCodeServiceimpl implements ValidateCodeService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public ValidateCodeVo generateValidateCode() {
        // 1.生成验证码
        // 2.存入redis
        // 3.封装返回
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(150, 48, 4, 60);
        String codeValue = lineCaptcha.getCode();
        String imageBase64 = lineCaptcha.getImageBase64();

        String codeKey = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set("user:log:validatecode:"+ codeKey,codeValue,5,TimeUnit.MINUTES);

        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(codeKey);
        validateCodeVo.setCodeValue("data:image/png;base64," + imageBase64);

        return validateCodeVo;
    }
//    @Override
//    public ValidateCodeVo generateValidateCode() {
//        // 1.生成验证码
//        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(150, 48, 4, 60);
//        // 获取验证码的值
//        String codeValue = lineCaptcha.getCode();
//        // 获取验证码的路径
//        String imageBase64 = lineCaptcha.getImageBase64(); // 获取验证码图片的Base64编码格式的字符串，前端不用下载就可以根据该字符串获取图片
//
//        // 2.存入redis
//        String codeKey = UUID.randomUUID().toString().replace("-", "");
//
//        redisTemplate.opsForValue().set("user:log:validatecode:" + codeKey, codeValue,1, TimeUnit.MINUTES);
//
//        // 3.封装vo返回
//        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
//        validateCodeVo.setCodeKey(codeKey);
//        validateCodeVo.setCodeValue("data:image/png;base64,"+imageBase64);
//
//        return validateCodeVo;
//    }



}
