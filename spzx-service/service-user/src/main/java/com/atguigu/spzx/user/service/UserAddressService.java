package com.atguigu.spzx.user.service;

import com.atguigu.spzx.model.entity.h5.UserAddress;

import java.util.List;

public interface UserAddressService {
    List<UserAddress> findUserAddressList();

    UserAddress getUserAddressById(Long id);
}
