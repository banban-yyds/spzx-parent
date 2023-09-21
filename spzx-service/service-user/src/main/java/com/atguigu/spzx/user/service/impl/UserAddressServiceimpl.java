package com.atguigu.spzx.user.service.impl;

import com.atguigu.spzx.common.util.utils.AuthContextUtil;
import com.atguigu.spzx.model.entity.h5.UserAddress;
import com.atguigu.spzx.user.mapper.UserAddressMapper;
import com.atguigu.spzx.user.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
public class UserAddressServiceimpl implements UserAddressService {
    @Autowired
    private UserAddressMapper userAddressMapper;
    @Override
    public List<UserAddress> findUserAddressList() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        List<UserAddress> userAddressList = userAddressMapper.findUserAddressList(userId);
        return userAddressList;
    }

    @Override
    public UserAddress getUserAddressById(Long id) {
        UserAddress userAddress = userAddressMapper.getUserAddressById(id);
        return userAddress;
    }
}
