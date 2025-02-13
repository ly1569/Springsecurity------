package org.schoole.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.schoole.domain.Order;
import org.schoole.mapper.OrderMapper;
import org.schoole.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper ;

    @Override
    public Map<String, Object> orderList(String userName, String fixmanName, Long pageNo, Long pageSize) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(userName),Order::getUserName,userName);
        wrapper.eq(StringUtils.hasLength(fixmanName),Order::getFixmanName,fixmanName);

        Page<Order> page = new Page<>(pageNo,pageSize);

        orderMapper.selectPage(page,wrapper);
        Map<String,Object> data = new HashMap<>();
        System.out.println("OrderServiceImpl中的 pageTotal:"+page.getTotal()+"   pageRecords"+page.getRecords());
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return data;
    }

    // 新增订单接口
    @Override
    public void addOrder(Order order) {
        // 查询出订单号的最大id数，设置订单id
        Integer id = orderMapper.selectMaxOrderId()+1;
        order.setId(id);
        orderMapper.insert(order);
    }

    // 修改订单接口
    @Override
    public void updateOrder(Order order) {
        orderMapper.updateById(order) ;
    }

    @Override
    public Order getById(Integer id) {
        Order order = orderMapper.selectById(id) ;
        return order;
    }

    @Override
    public void deleteById(Integer id) {
        orderMapper.deleteById(id) ;
    }


}
