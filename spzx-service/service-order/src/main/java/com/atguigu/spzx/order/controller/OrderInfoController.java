package com.atguigu.spzx.order.controller;

import com.atguigu.spzx.model.dto.h5.OrderInfoDto;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.TradeVo;
import com.atguigu.spzx.order.service.OrderInfoService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order/orderInfo")
public class OrderInfoController {
    @Autowired
    private OrderInfoService orderInfoService;

    @Operation(summary = "立即购买")
    @GetMapping("/auth/buy/{skuId}")
    public Result buy(@PathVariable Long skuId){
        //调用OrderInfoService中立即购买的方法
        TradeVo tradeVo = orderInfoService.buy(skuId);
        return Result.build(tradeVo,ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "去结算")
    @GetMapping("/auth/trade")
    public Result trade(){
        //调用OrderInfoService中去结算的方法
        TradeVo tradeVo = orderInfoService.trade();
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "提交订单")
    @PostMapping("/auth/submitOrder")
    public Result submitOrder(@RequestBody OrderInfoDto orderInfoDto){
        //调用OrderInfoService中提交订单的方法
        Long orderId = orderInfoService.submitOrder(orderInfoDto);
        return Result.build(orderId,ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "获取订单信息")
    @GetMapping("/auth/{orderId}")
    public Result<OrderInfo> getOrderInfo(@Parameter(name = "orderId", description = "订单id", required = true) @PathVariable Long orderId) {
        OrderInfo orderInfo = orderInfoService.getOrderInfoById(orderId);
        return Result.build(orderInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "查询我的订单")
    @GetMapping("/auth/{page}/{limit}")
    public Result getUserOrderInfo(@PathVariable Integer page,@PathVariable Integer limit,String orderStatus){
        PageInfo pageInfo = orderInfoService.getUserOrderInfo(page,limit,orderStatus);
        return Result.build(pageInfo,ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "供支付微服务调用的接口")
    @GetMapping("/auth/getOrderInfoByOrderNo/{orderNo}")
    public OrderInfo getOrderInfoByOrderNo(@PathVariable String orderNo){
        OrderInfo orderInfo = orderInfoService.getOrderInfoByOrderNo(orderNo);
        return orderInfo;
    }


}
