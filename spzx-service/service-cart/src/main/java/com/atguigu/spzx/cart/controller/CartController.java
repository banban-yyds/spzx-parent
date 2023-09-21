package com.atguigu.spzx.cart.controller;


import com.atguigu.spzx.cart.service.CartService;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Operation(description = "添加商品到购物车")
    @GetMapping("/auth/addToCart/{skuId}/{skuNum}")
    public Result addToCart(@PathVariable(value = "skuId",required = true)Long skuId,
                            @PathVariable(value = "skuNum",required = true)Integer skuNum){
        cartService.addToCart(skuId,skuNum);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(description = "查询购物车列表")
    @GetMapping("/auth/cartList")
    public Result getCartList(){
        List<CartInfo> cartList = cartService.getCartList();
        return Result.build(cartList,ResultCodeEnum.SUCCESS);
    }

    @Operation(description = "删除购物项")
    @GetMapping("/auth/deleteCart/{skuId}")
    public Result deleteCartItem(@PathVariable Integer skuId){
        cartService.deleteCartItem(skuId);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @Operation(description = "更新购物项选中状态")
    @GetMapping("/auth/checkCart/{skuId}/{isChecked}")
    public Result updateChecked(@PathVariable Long skuId,@PathVariable Integer isChecked){
        cartService.updateChecked(skuId,isChecked);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "购物车商品全选全不选")
    @GetMapping("/auth/allCheckCart/{isChecked}")
    public Result allCheckCart(@PathVariable Integer isChecked){
        //调用CartService中全选全不选的方法
        cartService.allCheckCart(isChecked);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "清空购物车")
    @GetMapping("/auth/clearCart")
    public Result clearCart(){
        //调用CartService中清空购物车的方法
        cartService.clearCart();
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "供订单微服务远程调用的接口")
    @GetMapping("/auth/getAllCkecked")
    public List<CartInfo> getAllChecked(){
        //调用CartService中获取所有选中的购物项
        List<CartInfo> cartInfoList = cartService.getAllChecked();
        return cartInfoList;
    }

    @Operation(summary = "供订单微服务远程调用的接口2")
    @GetMapping("/auth/deleteChecked")
    public void deleteChecked(){
        //调用CartService中清空选中的购物项的方法
        cartService.deleteChecked();
    }

}
