package com.atguigu.spzx.pay.controller;

import com.atguigu.spzx.common.service.anno.EnableUserTokenFeignInterceptor;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.pay.service.AlipayService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/order/alipay")
@EnableUserTokenFeignInterceptor
public class AlipayController {

    @Autowired
    private AlipayService alipayService;

    @Operation(summary = "立即支付")
    @GetMapping("/submitAlipay/{orderNo}")
    public Result submitAlipay(@PathVariable String orderNo){
        //调用PaymentInfoService中立即的方法
        String form = alipayService.submitAlipay(orderNo);
        return Result.build(form, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "支付宝回调")
    @PostMapping("/callback/notify")
    public String alipayNotify(@RequestParam Map<String, String> paramMap, HttpServletRequest request) {
        //调用AlipayService中通知的方法
        String flag = alipayService.alipayNotify(paramMap,request);
        return flag;
    }
}