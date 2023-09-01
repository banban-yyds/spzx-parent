package com.atguigu.spzx.manager.intercetor;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.common.service.utils.AuthContextUtil;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@Component
public class LoginAuthInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.预检请求放行
        String method = request.getMethod();
        if ("OPTIONS".equalsIgnoreCase(method)){
            return true;
        }
        // 2.获取token，并验证token的合法性
        String token = request.getHeader("token");
        if (token == null || token.equals("")){
            NoLoginInfo(response);
            return false;
        }
        // 从redis中取出token
        String sysUserString =(String) redisTemplate.opsForValue().get("user:log:info:" + token);
        if (sysUserString == null || sysUserString.equals("")){
            NoLoginInfo(response);
            return false;
        }
        // 3.验证token成功，刷新超时时间
        redisTemplate.expire("user:log:info:" + token, 30, TimeUnit.MINUTES);
        SysUser sysUser = JSON.parseObject(sysUserString, SysUser.class);
        // 4.存入ThreadLocal中
        AuthContextUtil.setThreadLocal(sysUser);
        // 放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 删除TreadLocal中的信息
        AuthContextUtil.remove();
    }

    // 请求源未登录，返回登录界面方法
    public void NoLoginInfo(HttpServletResponse response){
        Result<Object> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        try {
            writer = response.getWriter();
            writer.println(JSON.toJSONString(result));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null){
                writer.close();
            }
        }

    }
}
