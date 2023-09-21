package com.atguigu.spzx.feign.cart;

import com.atguigu.spzx.model.entity.h5.CartInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value =  "service-cart",fallback = CartFeignClientimpl.class)
public interface CartFeignClient {
    @GetMapping("/api/order/cart/auth/getAllCkecked")
    List<CartInfo> getAllChecked();

    @GetMapping("/api/order/cart/auth/deleteChecked")
    void deleteChecked();
}
