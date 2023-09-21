package com.atguigu.spzx.feign.order;

import com.atguigu.spzx.model.entity.order.OrderInfo;
import org.springframework.stereotype.Component;

@Component
public class OrderFeignClientimpl implements OrderFeignClient{
    @Override
    public OrderInfo getOrderInfoByOrderNo(String orderNo) {
        return null;
    }
}
