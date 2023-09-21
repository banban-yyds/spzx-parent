package com.atguigu.spzx.pay.mapper;

import com.atguigu.spzx.model.entity.pay.PaymentInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentInfoMapper {
    PaymentInfo getPaymentInfoByorderNo(String orderNo);

    void savePaymentInfo(PaymentInfo paymentInfo);

    void updatePaymentInfoById(PaymentInfo paymentInfo);
}
