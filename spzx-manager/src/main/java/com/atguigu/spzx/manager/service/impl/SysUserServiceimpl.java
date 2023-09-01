package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.common.service.exception.GuiguException;
import com.atguigu.spzx.common.service.utils.MenuHelper;
import com.atguigu.spzx.manager.mapper.SysUserMapper;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceimpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PageInfo<SysUser> findUserPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto) {

        PageHelper.startPage(pageNum, pageSize);
        PageInfo<SysUser> sysUserPageInfo = new PageInfo<>(sysUserMapper.findUserPage(sysUserDto));
        return sysUserPageInfo;
    }

    @Override
    public void addUser(SysUser sysUser) {
        // 添加用户时，将密码使用md5加密
        String passwordMD5 = DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes());
        sysUser.setPassword(passwordMD5);

        sysUserMapper.addUser(sysUser);
    }

    @Override
    public void updateUser(SysUser sysUser) {
        sysUserMapper.updateUser(sysUser);
    }

    @Override
    public void deleteUserById(Integer id) {
        sysUserMapper.deleteUserById(id);
    }

    @Override
    public SysUser getUserById(Integer id) {
        return sysUserMapper.getUserById(id);
    }

    @Override
    public LoginVo login(LoginDto loginDto) {

        // 0. 验证码
        String captcha = loginDto.getCaptcha(); // 用户输入的验证码
        String codeKey = loginDto.getCodeKey(); // 所生成的 验证码

        String redisKey =(String) redisTemplate.opsForValue().get("user:log:validatecode:" + codeKey);

//        if (redisKey == "" || !captcha.equals(redisKey)){
        if(StrUtil.isEmpty(redisKey) || !StrUtil.equalsIgnoreCase(captcha, redisKey)){
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        // 验证码成功删除缓存
        redisTemplate.delete("user:log:validatecode:" + codeKey);

        // 1.验证账号
        String userName = loginDto.getUserName();
        SysUser sysUser = sysUserMapper.findSysUserByUsername(userName);

        if (sysUser == null){
            throw new GuiguException(ResultCodeEnum.CODE_ERROR);
        }

        // 2.验证密码
        String loginDtoPassword = loginDto.getPassword();
        String MD5 = DigestUtils.md5DigestAsHex(loginDtoPassword.getBytes());
        if (!MD5.equals(sysUser.getPassword())){
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }

        // 3.验证账号是否被停用
        if (sysUser.getStatus() == 0){
            throw new GuiguException(ResultCodeEnum.SYSTEM_ERROR);
        }

        /*
        *  4.生成token 并保存在redis中
        *    4.1使用uuid生成唯一id
        *    4.2转换为json
        *    4.3存入redis中并设置key的过期时间
        * */
        String token = UUID.randomUUID().toString().replace("-", "");
        String toJSONString = JSON.toJSONString(sysUser);
        redisTemplate.boundValueOps("user:log:info:" + token).set(toJSONString,30,TimeUnit.MINUTES);

        // 5.返回数据响应对象
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setRefresh_token("");
        return loginVo;



    }

    @Override
    public SysUser getUserInfo(String token) {
        String sysUserString =(String) redisTemplate.opsForValue().get("user:log:info:" + token);
        SysUser sysUser = JSON.parseObject(sysUserString, SysUser.class);
        return sysUser;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete("user:log:info:" + token);
    }

    @Override
    public List<SysMenuVo> getSysUserMenuByUserId(Long userId) {
        List<SysMenu> sysUserMenuList = sysUserMapper.getSysUserMenuByUserId(userId);
        List<SysMenu> sysUserMenuTree = MenuHelper.buildTree(sysUserMenuList);
        List<SysMenuVo> sysMenuVoList = buildMenus(sysUserMenuTree);
        return sysMenuVoList;
    }

    private List<SysMenuVo> buildMenus(List<SysMenu> menus) {

        List<SysMenuVo> sysMenuVoList = new LinkedList<SysMenuVo>();
        for (SysMenu sysMenu : menus) {
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setTitle(sysMenu.getTitle());
            sysMenuVo.setName(sysMenu.getComponent());
            List<SysMenu> children = sysMenu.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                sysMenuVo.setChildren(buildMenus(children));
            }
            sysMenuVoList.add(sysMenuVo);
        }
        return sysMenuVoList;
    }



}
