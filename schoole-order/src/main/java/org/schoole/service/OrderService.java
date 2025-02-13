package org.schoole.service;

import org.schoole.domain.Order;

import java.util.Map;

public interface OrderService {
    // 查询所有订单信息
    public Map<String,Object> orderList(String userName , String fixmanName,Long pageNo , Long pageSize);

    // 新增订单接口口
    void addOrder(Order order);

    // 修改订单接口
    void updateOrder(Order order);

    // 获取订单信息
    Order getById(Integer id);

    void deleteById(Integer id);
}
