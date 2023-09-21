package com.atguigu.spzx.feign.user;

import com.atguigu.spzx.model.entity.h5.UserAddress;
import com.atguigu.spzx.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-user",fallback = UserFeignClientImpl.class)
public interface UserFeignClient {
    @GetMapping("/api/user/userAddress/getUserAddressById/{id}")
    UserAddress getUserAddressById(@PathVariable Long id);
}
