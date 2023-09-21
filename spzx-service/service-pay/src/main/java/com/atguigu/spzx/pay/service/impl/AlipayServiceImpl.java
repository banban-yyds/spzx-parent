package com.atguigu.spzx.pay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.atguigu.spzx.model.entity.pay.PaymentInfo;
import com.atguigu.spzx.pay.properties.AlipayProperties;
import com.atguigu.spzx.pay.service.AlipayService;
import com.atguigu.spzx.pay.service.PaymentInfoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AlipayServiceImpl implements AlipayService {
    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired
    private PaymentInfoService paymentInfoService;

    @Override
    public String submitAlipay(String orderNo) {
        try {
            //保存支付信息
            PaymentInfo paymentInfo = paymentInfoService.savePaymentInfo(orderNo);

            //        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","app_id","your private_key","json","GBK","alipay_public_key","RSA2");
            AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
            //异步接收地址，仅支持http/https，公网可访问
            request.setNotifyUrl(alipayProperties.getNotifyPaymentUrl());
            //同步跳转地址，仅支持http/https
            request.setReturnUrl(alipayProperties.getReturnPaymentUrl());
            /******必传参数******/
            JSONObject bizContent = new JSONObject();
            //商户订单号，商家自定义，保持唯一性
            bizContent.put("out_trade_no", orderNo);
            //支付金额，最小值0.01元
            //            bizContent.put("total_amount", paymentInfo.getAmount());
            bizContent.put("total_amount", 0.01);
            //订单标题，不可使用特殊符号
            bizContent.put("subject", paymentInfo.getContent());

            /******可选参数******/
            //手机网站支付默认传值FAST_INSTANT_TRADE_PAY
            bizContent.put("product_code", "QUICK_WAP_WAY");
            //bizContent.put("time_expire", "2022-08-01 22:00:00");


            request.setBizContent(bizContent.toString());
            //调用支付宝接口发送请求
            AlipayTradeWapPayResponse response = alipayClient.pageExecute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功");
                //获取响应的表单
                String form = response.getBody();
                return form;
            } else {
                System.out.println("调用失败");
            }
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public String alipayNotify(Map<String, String> paramMap, HttpServletRequest request) {
        try {
            boolean flag = AlipaySignature.rsaCheckV1(paramMap, alipayProperties.getAlipayPublicKey(), AlipayProperties.charset,AlipayProperties.sign_type);
            if (flag){
                String trade_status = paramMap.get("trade_status");
                if ("TRADE_SUCCESS".equals(trade_status) || "TRADE_FINISHED".equals(trade_status)){
                    // TODO:1.更新支付列表中的支付状态和回调数据
                    paymentInfoService.updatePaymentStatus(paramMap);
                    // TODO:2.远程调用商品微服务更新商品的库存和销量

                    // TODO:3.远程调用订单微服务更新订单状态

                    return "success";
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
