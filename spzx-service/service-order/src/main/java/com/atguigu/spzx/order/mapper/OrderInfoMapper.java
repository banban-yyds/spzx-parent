package com.atguigu.spzx.order.mapper;

import com.atguigu.spzx.model.entity.order.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderInfoMapper {
    void save(OrderInfo orderInfo);

    List<OrderInfo> getUserOrderInfo(Long userId, String orderStatus);

    OrderInfo getOrderInfoByOrderNo(String orderNo);

    OrderInfo getOrderInfoById(Long orderId);
}
