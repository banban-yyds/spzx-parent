package com.atguigu.spzx.pay.service.impl;

import com.atguigu.spzx.common.util.utils.AuthContextUtil;
import com.atguigu.spzx.feign.order.OrderFeignClient;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.entity.order.OrderItem;
import com.atguigu.spzx.model.entity.pay.PaymentInfo;
import com.atguigu.spzx.pay.mapper.PaymentInfoMapper;
import com.atguigu.spzx.pay.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PaymentInfoServiceImpl implements PaymentInfoService {

    @Autowired
    private PaymentInfoMapper paymentInfoMapper;
    @Autowired
    private OrderFeignClient orderFeignClient;
    @Override
    public PaymentInfo savePaymentInfo(String orderNo) {
        PaymentInfo paymentInfo = paymentInfoMapper.getPaymentInfoByorderNo(orderNo);
        if (paymentInfo == null){
            OrderInfo orderInfo = orderFeignClient.getOrderInfoByOrderNo(orderNo);
            paymentInfo = new PaymentInfo();
            paymentInfo.setUserId(AuthContextUtil.getUserInfo().getId());
            paymentInfo.setPaymentStatus(0); // 0为还未支付
            paymentInfo.setAmount(orderInfo.getTotalAmount());
            paymentInfo.setOrderNo(orderNo);
            paymentInfo.setPayType(2);  // 1为微信 2为支付宝
            List<OrderItem> orderItemList = orderInfo.getOrderItemList();
            for (OrderItem orderItem : orderItemList) {
                paymentInfo.setContent(orderItem.getSkuName() + ",");
            }
            paymentInfoMapper.savePaymentInfo(paymentInfo);
        }
        return paymentInfo;
    }

    @Override
    public void updatePaymentStatus(Map<String, String> paramMap) {
        //获取订单号
        String orderNo = paramMap.get("out_trade_no");
        //根据订单号获取支付信息
        PaymentInfo paymentInfo = paymentInfoMapper.getPaymentInfoByorderNo(orderNo);
        //判断是否已经支付过
        if(paymentInfo.getPaymentStatus() == 1){
            //该订单已经支付过
            return;
        }
        //设置支付状态为1
        paymentInfo.setPaymentStatus(1);
        //设置回调时间
        paymentInfo.setCallbackTime(new Date());
        //设置回调内容
        paymentInfo.setCallbackContent(paramMap.toString());
        //从回调传入的map中获取交易编号
        String tradeNo = paramMap.get("trade_no");
        //设置交易编号
        paymentInfo.setOutTradeNo(tradeNo);
        //更新支付表
        paymentInfoMapper.updatePaymentInfoById(paymentInfo);
    }


}
