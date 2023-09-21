package com.atguigu.spzx.cart.service.impl;

import cn.hutool.core.util.StrUtil;
import com.atguigu.spzx.cart.service.CartService;
import com.atguigu.spzx.common.util.utils.AuthContextUtil;
import com.atguigu.spzx.feign.product.ProductFeignClient;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.model.vo.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CartServiceimpl implements CartService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ProductFeignClient productFeignClient;
    private String getCartKey(Long userId){
        return "user:cart:" + userId;
    }
    @Override
    public void addToCart(Long skuId, Integer skuNum) {
        // 获取用户id
        Long userId = AuthContextUtil.getUserInfo().getId();
        // 获取Redis中保存的购物车key
        String cartKey = getCartKey(userId);
        // 从redis获取购物车
        CartInfo cartInfo =(CartInfo) redisTemplate.opsForHash().get(cartKey,skuId + "");
        // 判空
        if(cartInfo != null){
            cartInfo.setSkuNum(cartInfo.getSkuNum() + skuNum);
        }else {
            // 为null则表示第一次查询，根据skuId远程调用获取sku对象
            ProductSku productSku = productFeignClient.getProductSkuBySkuId(skuId);
            // 第一次加入购物车需要给cartInfo设置属性
            cartInfo = new CartInfo();
            cartInfo.setSkuId(skuId);
            cartInfo.setSkuNum(skuNum);
            cartInfo.setUserId(userId);
            cartInfo.setIsChecked(1);
            cartInfo.setCartPrice(productSku.getSalePrice());
            cartInfo.setImgUrl(productSku.getThumbImg());
            cartInfo.setSkuName(productSku.getSkuName());
        }
        // 将用户购物车内的信息 放入redis中
        redisTemplate.opsForHash().put(cartKey, skuId+"", cartInfo);
    }

    @Override
    public List<CartInfo> getCartList() {
        // 从ThreadLocal封装的工具类中 获取用户Id
        Long userId = AuthContextUtil.getUserInfo().getId();
        // 获取向Redis中存放购物车的key
        String cartKey = getCartKey(userId);
        // 从redis中获取Hash类类型的所有值
        List<CartInfo> cartInfoList = redisTemplate.opsForHash().values(cartKey);
        return cartInfoList;
    }

    @Override
    public void deleteCartItem(Integer skuId) {
        // 获取用户id
        Long userId = AuthContextUtil.getUserInfo().getId();
        // 获取购物车再redis中的key
        String cartKey = getCartKey(userId);
        // 删除指定的购物项
        redisTemplate.opsForHash().delete(cartKey,skuId+"");
    }

    @Override
    public void updateChecked(Long skuId, Integer isChecked) {
        // 获取用户id
        Long userId = AuthContextUtil.getUserInfo().getId();
        // 获取用户购物车的key
        String cartKey = getCartKey(userId);
        // 获取Redis中hash类型的所有值，即购物车中所有的购物项
        List<CartInfo> cartList = redisTemplate.opsForHash().values(cartKey);
        // 判空
        if (CollectionUtils.isEmpty(cartList)){
            cartList.forEach(cartInfo -> {
                // 判断当前cartInfo是否是我们的设置项
                if (cartInfo.getSkuId() == skuId) {
                    // 设置当前购物项的选中状态
                    cartInfo.setIsChecked(isChecked);
                    // 将修改之后的放到redis中
                    redisTemplate.opsForHash().put(cartKey, skuId+"", cartInfo);
                }
            });
        }
    }

    @Override
    public void allCheckCart(Integer isChecked) {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        List<CartInfo> cartList = redisTemplate.opsForHash().values(cartKey);
        if (CollectionUtils.isEmpty(cartList)){
            cartList.forEach(cartInfo -> {
                cartInfo.setIsChecked(isChecked);
                redisTemplate.opsForHash().put(cartKey, cartInfo.getSkuId(), cartInfo);

            });
        }

    }

    @Override
    public void clearCart() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        redisTemplate.delete(cartKey);
    }

    @Override
    public List<CartInfo> getAllChecked() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        List<CartInfo> cartInfoList = redisTemplate.opsForHash().values(cartKey);
        List<CartInfo> cartInfos = cartInfoList.stream().filter(cartInfo -> cartInfo.getIsChecked() == 1).collect(Collectors.toList());
        return cartInfos;
    }

    @Override
    public void deleteChecked() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        List<CartInfo> cartInfoList  = redisTemplate.opsForHash().values(cartKey);
        if (!CollectionUtils.isEmpty(cartInfoList)){
            cartInfoList.forEach(cartInfo -> {
                if (cartInfo.getIsChecked() == 1) {
                    redisTemplate.opsForHash().delete(cartKey, cartInfo.getSkuId()+"");
                }
            });
        }
    }
}
