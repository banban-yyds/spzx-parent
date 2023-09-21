package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.OrderStatisticsMapper;
import com.atguigu.spzx.manager.service.OrderStatisticsService;
import com.atguigu.spzx.model.entity.order.OrderStatistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderStatisticsServiceimpl implements OrderStatisticsService {
    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;
    @Override
    public void insert(OrderStatistics orderStatistics) {
        orderStatisticsMapper.insert(orderStatistics);
    }
}
