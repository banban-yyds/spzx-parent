package com.atguigu.spzx.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.manager.mapper.SysUserMapper;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.system.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceimpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public LoginVo login(LoginDto loginDto) {
        // 1.验证账号
        String userName = loginDto.getUserName();
        SysUser sysUser = sysUserMapper.findSysUserByUsername(userName);

        if (sysUser == null){
            throw new RuntimeException("账号有误");
        }

        // 2.验证密码
        String loginDtoPassword = loginDto.getPassword();
        String MD5 = DigestUtils.md5DigestAsHex(loginDtoPassword.getBytes());
        if (!MD5.equals(sysUser.getPassword())){
            throw new RuntimeException("密码有误");
        }

        // 3.验证账号是否被停用
        if (sysUser.getStatus() == 0){
            throw new RuntimeException("账号被停用");
        }

        /*
        *  4.生成token 并保存在redis中
        *    4.1使用uuid生成唯一id
        *    4.2转换为json
        *    4.3存入redis中并设置key的过期时间
        * */
        String token = UUID.randomUUID().toString().replace("-", "");
        String toJSONString = JSON.toJSONString(sysUser);
        redisTemplate.boundValueOps(toJSONString).set(toJSONString,30,TimeUnit.MINUTES);

        // 5.返回数据响应对象
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setRefresh_token("");
        return loginVo;
    }

}
