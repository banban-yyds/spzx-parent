package com.atguigu.spzx.feign.user;

import com.atguigu.spzx.model.entity.h5.UserAddress;
import org.springframework.stereotype.Component;

@Component
public class UserFeignClientImpl implements UserFeignClient{
    @Override
    public UserAddress getUserAddressById(Long id) {
        return new UserAddress();
    }
}
