package com.atguigu.spzx.user.mapper;

import com.atguigu.spzx.model.entity.h5.UserAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserAddressMapper {
    List<UserAddress> findUserAddressList(Long userId);

    UserAddress getUserAddressById(Long id);
}
