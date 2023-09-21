package com.atguigu.spzx.order.service.impl;

import com.atguigu.spzx.common.service.exception.GuiguException;
import com.atguigu.spzx.common.util.utils.AuthContextUtil;
import com.atguigu.spzx.feign.cart.CartFeignClient;
import com.atguigu.spzx.feign.product.ProductFeignClient;
import com.atguigu.spzx.feign.user.UserFeignClient;
import com.atguigu.spzx.model.dto.h5.OrderInfoDto;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.entity.h5.UserAddress;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.entity.order.OrderItem;
import com.atguigu.spzx.model.entity.order.OrderLog;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.TradeVo;
import com.atguigu.spzx.order.mapper.OrderInfoMapper;
import com.atguigu.spzx.order.mapper.OrderItemMapper;
import com.atguigu.spzx.order.mapper.OrderLogMapper;
import com.atguigu.spzx.order.service.OrderInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class OrderInfoServiceimpl implements OrderInfoService {
    @Autowired
    private CartFeignClient cartFeignClient;
    @Autowired
    private ProductFeignClient productFeignClient;
    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private OrderLogMapper orderLogMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Override
    public TradeVo trade() {
        List<CartInfo> cartInfoList = cartFeignClient.getAllChecked();
        List<OrderItem> orderItemList = new ArrayList<>();
        cartInfoList.forEach(cartInfo -> {
            OrderItem orderItem = new OrderItem();
            BeanUtils.copyProperties(cartInfo, orderItem);
            orderItem.setThumbImg(cartInfo.getImgUrl());
            orderItem.setSkuPrice(cartInfo.getCartPrice());
            orderItemList.add(orderItem);
        });

        BigDecimal totalAmount = new BigDecimal(0);
//        orderItemList.forEach(orderItem -> {
//            BigDecimal skuPrice = orderItem.getSkuPrice();
//            Integer skuNum = orderItem.getSkuNum();
//            BigDecimal multiply = skuPrice.multiply(new BigDecimal(skuNum));
//            BigDecimal b = totalAmount.add(multiply);
//        });
        for (OrderItem orderItem : orderItemList) {
            BigDecimal skuPrice = orderItem.getSkuPrice();
            Integer skuNum = orderItem.getSkuNum();
            BigDecimal multiply = skuPrice.multiply(new BigDecimal(skuNum));
            totalAmount = totalAmount.add(multiply);
        }

        TradeVo tradeVo = new TradeVo();
        tradeVo.setOrderItemList(orderItemList);
        tradeVo.setTotalAmount(totalAmount);
        return tradeVo;
    }

    @Override
    public TradeVo buy(Long skuId) {
        //远程调用获取ProductSku对象
        ProductSku productSku = productFeignClient.getProductSkuBySkuId(skuId);
        //创建一个集合保存订单项
        List<OrderItem> orderItemList = new ArrayList<>();
        //创建OrderItem对象
        OrderItem orderItem = new OrderItem();
        orderItem.setThumbImg(productSku.getThumbImg());
        orderItem.setSkuPrice(productSku.getSalePrice());
        orderItem.setSkuNum(1);
        orderItem.setSkuName(productSku.getSkuName());
        orderItem.setSkuId(skuId);
        //将orderItem对象放到orderItemList中
        orderItemList.add(orderItem);

        //创建返回的TradeVo对象
        TradeVo tradeVo = new TradeVo();
        //设置要展示的订单项
        tradeVo.setOrderItemList(orderItemList);
        //设置所有订单项的总金额
        tradeVo.setTotalAmount(productSku.getSalePrice());
        return tradeVo;
    }

    @Override
    public Long submitOrder(OrderInfoDto orderInfoDto) {

        // 获取用户地址id
        Long userAddressId = orderInfoDto.getUserAddressId();
        // 获取运费
        BigDecimal feightFee = orderInfoDto.getFeightFee();
        // 获取备注
        String remark = orderInfoDto.getRemark();
        // 获取所有订单项
        List<OrderItem> orderItemList = orderInfoDto.getOrderItemList();
        //验证数据
        if(CollectionUtils.isEmpty(orderItemList)){
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
        for (OrderItem orderItem : orderItemList) {
            ProductSku productSku = productFeignClient.getProductSkuBySkuId(orderItem.getSkuId());
            if(null == productSku){
                throw new GuiguException(ResultCodeEnum.DATA_ERROR);
            }
            if (orderItem.getSkuNum().intValue() > productSku.getStockNum().intValue()){
                throw new GuiguException(ResultCodeEnum.STOCK_LESS);
            }
        }

        // 构建订单数据，保存订单
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderNo(String.valueOf(System.currentTimeMillis()));
        orderInfo.setUserId(userInfo.getId());
        orderInfo.setNickName(userInfo.getNickName());
        //远程调用用户微服务
        UserAddress userAddress = userFeignClient.getUserAddressById(userAddressId);
        orderInfo.setReceiverName(userAddress.getName());
        orderInfo.setReceiverPhone(userAddress.getPhone());
        orderInfo.setReceiverTagName(userAddress.getTagName());
        orderInfo.setReceiverProvince(userAddress.getProvinceCode());
        orderInfo.setReceiverCity(userAddress.getCityCode());
        orderInfo.setReceiverDistrict(userAddress.getDistrictCode());
        orderInfo.setReceiverAddress(userAddress.getFullAddress());

        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }
        orderInfo.setTotalAmount(totalAmount);
        orderInfo.setCouponAmount(new BigDecimal(0));
        orderInfo.setOriginalTotalAmount(totalAmount);
        orderInfo.setFeightFee(feightFee);
        orderInfo.setRemark(remark);
        orderInfo.setPayType(2);
        orderInfo.setOrderStatus(0);
        //保存订单
        orderInfoMapper.save(orderInfo);

        //保存订单明细
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderId(orderInfo.getId());
            //保存订单项
            orderItemMapper.save(orderItem);
        }

        //记录日志
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setProcessStatus(0);
        orderLog.setNote("提交订单");
        //保存订单日志
        orderLogMapper.save(orderLog);

        // 远程调用service-cart微服务接口清空购物车数据
        cartFeignClient.deleteChecked();
        return orderInfo.getId();
    }

    @Override
    public PageInfo getUserOrderInfo(Integer page, Integer limit, String orderStatus) {
        PageHelper.startPage(page, limit);
        Long userId = AuthContextUtil.getUserInfo().getId();
        return new PageInfo(orderInfoMapper.getUserOrderInfo(userId,orderStatus));
    }

    @Override
    public OrderInfo getOrderInfoByOrderNo(String orderNo) {
        return orderInfoMapper.getOrderInfoByOrderNo(orderNo);
    }

    @Override
    public OrderInfo getOrderInfoById(Long orderId) {
        OrderInfo orderInfo = orderInfoMapper.getOrderInfoById(orderId);
        return orderInfo;
    }
}
