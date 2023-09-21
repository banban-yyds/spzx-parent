package com.atguigu.spzx.order.service;

import com.atguigu.spzx.model.dto.h5.OrderInfoDto;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.vo.h5.TradeVo;
import com.github.pagehelper.PageInfo;

public interface OrderInfoService {
    TradeVo trade();

    TradeVo buy(Long skuId);

    Long submitOrder(OrderInfoDto orderInfoDto);

    PageInfo getUserOrderInfo(Integer page, Integer limit, String orderStatus);

    OrderInfo getOrderInfoByOrderNo(String orderNo);

    OrderInfo getOrderInfoById(Long orderId);
}
