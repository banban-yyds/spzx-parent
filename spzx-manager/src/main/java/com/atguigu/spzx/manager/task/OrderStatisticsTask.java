package com.atguigu.spzx.manager.task;

import cn.hutool.core.date.DateUtil;
import com.atguigu.spzx.manager.service.OrderInfoService;
import com.atguigu.spzx.manager.service.OrderStatisticsService;
import com.atguigu.spzx.model.entity.order.OrderStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OrderStatisticsTask {
    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private OrderStatisticsService orderStatisticsService;

    @Scheduled(cron = "0 0 2 * * ?")
    public void insertOrderStatistics(){
    // 1.获取将前一天的订单
    // 获取用于查询前一天的sql字段(参数)
        String yesterdayName = DateUtil.offsetDay(new Date(), -1).toString("yyyy-MM-dd");

    //  查询昨天的订单数据
        OrderStatistics orderStatistics = orderInfoService.getYesterdayOrder(yesterdayName);

    // 2.插入到statistics表中
        if (orderStatistics != null){
            orderStatisticsService.insert(orderStatistics);
        }
    }

}
