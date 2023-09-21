package com.atguigu.spzx.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.common.service.exception.GuiguException;
import com.atguigu.spzx.common.util.utils.AuthContextUtil;
import com.atguigu.spzx.common.util.utils.IpUtil;
import com.atguigu.spzx.model.dto.user.UserLoginDto;
import com.atguigu.spzx.model.dto.user.UserRegisterDto;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.user.UserInfoVo;
import com.atguigu.spzx.user.mapper.UserInfoMapper;
import com.atguigu.spzx.user.service.UserInfoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    // 手机号注册
    public void register(UserRegisterDto userRegisterDto) {
        // 获取参数
        String username = userRegisterDto.getUsername();
        String password = userRegisterDto.getPassword();
        String nickName = userRegisterDto.getNickName();
        String code = userRegisterDto.getCode();
        // 校验参数
        if (StrUtil.isEmpty(username) || StrUtil.isEmpty(password) || StrUtil.isEmpty(nickName) || StrUtil.isEmpty(code)){
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
        // 根据手机号(username) 在redis中查找 对应的 验证码
        String redisCode = (String)redisTemplate.opsForValue().get("user:code:" + username);
        // 校验 验证码
        if (!code.equals(redisCode)){
            // 手机验证码不正确
            throw new GuiguException(ResultCodeEnum.PHONECODE_ERROR);
        }
        // 删除Redis中的验证码
        redisTemplate.delete("user:code:" + username);
        //根据用户名查询数据库
        UserInfo userInfo = userInfoMapper.getUserInfoByuserName(username);
        //判断用户是否存在 存在->注册失败
        if (userInfo != null){
            throw new GuiguException(ResultCodeEnum.PHONE_EXIST);
        }
        // 对密码加密(md5)
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        //创建UserInfo对象
        userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(md5Password);
        userInfo.setNickName(nickName);
        userInfo.setPhone(username);
        userInfo.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        userInfo.setSex(1);
        userInfo.setStatus(1);
        // 保存用户信息
        userInfoMapper.saveUserInfo(userInfo);

    }

    @Override // 登录
    public String login(UserLoginDto userLoginDto, HttpServletRequest request) {
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();
        if (StrUtil.isEmpty(username) || StrUtil.isEmpty(password)){
            throw new GuiguException(ResultCodeEnum.CODE_ERROR);
        }

        UserInfo userInfo = userInfoMapper.getUserInfoByuserName(username);
        if (userInfo == null){
            throw new GuiguException(ResultCodeEnum.USER_NOT_FOUND);
        }

        String passwordEncoded = userInfo.getPassword();
        if(!DigestUtils.md5DigestAsHex(password.getBytes()).equalsIgnoreCase(passwordEncoded)){
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }

        if (userInfo.getStatus() == 0){
            throw new GuiguException(ResultCodeEnum.SYSTEM_ERROR);
        }


        // 用UUID生成token
        String token = UUID.randomUUID().toString().replace("-", "");
        // 将用户信息转换为JSON字符串
        String jsonUserInfo = JSON.toJSONString(userInfo);
        // 将用户保存在redis中
//        redisTemplate.opsForValue().set("user:login:" + token, jsonUserInfo,30, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set("user:login:" + token, jsonUserInfo);

        // 获取ip地址
        String ipAddress = IpUtil.getIpAddress(request);
        //创建一个空userInfo用于赋值
        UserInfo updateUserInfo = new UserInfo();
        // 设置id用于更新
        updateUserInfo.setId(userInfo.getId());
        // 设置ip
        updateUserInfo.setLastLoginIp(ipAddress);
        // 设置最后登录的时间
        updateUserInfo.setLastLoginTime(new Date());
        // 更新数据库
        userInfoMapper.updateUserInfo(updateUserInfo);

        return token;
    }

//    @Override
//    public UserInfoVo getUserInfoBytoken(HttpServletRequest request) {
//        String token = request.getHeader("token");
//        if (StrUtil.isEmpty(token)){
//            throw new GuiguException(ResultCodeEnum.LOGIN_AUTH);
//        }
//        String userInfoStr = (String) redisTemplate.opsForValue().get("user:login:" + token);
//        UserInfo userInfo = JSON.parseObject(userInfoStr, UserInfo.class);
//
//
//        UserInfoVo userInfoVo = new UserInfoVo();
//        BeanUtils.copyProperties(userInfo,userInfoVo );
//        return userInfoVo;
//    }

    @Override
    public UserInfoVo getUserInfoBytoken(HttpServletRequest request) {
        // 从AuthContextUtil中获取Info对象
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        // 创建userInfoVo对象
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo,userInfoVo);
        return userInfoVo;
    }

}
