package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.atguigu.spzx.manager.mapper.OrderInfoMapper;
import com.atguigu.spzx.manager.mapper.OrderStatisticsMapper;
import com.atguigu.spzx.manager.service.OrderInfoService;
import com.atguigu.spzx.model.dto.order.OrderStatisticsDto;
import com.atguigu.spzx.model.entity.order.OrderStatistics;
import com.atguigu.spzx.model.vo.order.OrderStatisticsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderInfoServiceimpl implements OrderInfoService {
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;

    @Override
    public OrderStatistics getYesterdayOrder(String yesterdayName) {
        return orderInfoMapper.getYesterdayOrder(yesterdayName);
    }

    @Override
    public OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto) {
        // 根据Dto获取OrderStatisticsVo中的dateList和accountList
        List<OrderStatistics> orderStatisticsList  =
                orderStatisticsMapper.getOrderStatisticsData(orderStatisticsDto);

        // 获取订单日期
        List<String> dateList = orderStatisticsList.stream()// 获取stream流
                // 使用hutool中的DateUtil解析日期为字符串
                .map(orderStatistics -> DateUtil.format(orderStatistics.getOrderDate(), "yyyy-MM-dd"))
                // 使用Collectors获取List集合
                .collect(Collectors.toList());

        // 获取订单总金额
        List<BigDecimal> accountList = orderStatisticsList.stream().map(OrderStatistics::getTotalAmount)
                .collect(Collectors.toList());

        OrderStatisticsVo orderStatisticsVo = new OrderStatisticsVo();
        orderStatisticsVo.setDateList(dateList);
        orderStatisticsVo.setAmountList(accountList);
        return orderStatisticsVo;
    }
}
