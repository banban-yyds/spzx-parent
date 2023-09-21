package com.atguigu.spzx.common.service.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.common.util.utils.AuthContextUtil;
import com.atguigu.spzx.model.entity.user.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class UserLoginAuthInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头获取token
        String token = request.getHeader("token");
        // 从redis中获取用户信息
        String josnUserInfo = (String)redisTemplate.opsForValue().get("user:login:" + token);
        if (!StrUtil.isEmpty(josnUserInfo)){
            UserInfo userInfo = JSON.parseObject(josnUserInfo, UserInfo.class);
            //将UserInfo对象于当前对象绑定
            AuthContextUtil.setUserInfo(userInfo);
        }
        return true;
    }


}
