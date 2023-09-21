package com.atguigu.spzx.feign.cart;


import com.atguigu.spzx.model.entity.h5.CartInfo;
import org.springframework.stereotype.Component;

import java.util.List;
@Component

public class CartFeignClientimpl implements CartFeignClient{
    @Override
    public List<CartInfo> getAllChecked() {
        return null;
    }

    @Override
    public void deleteChecked() {
        return;
    }
}
