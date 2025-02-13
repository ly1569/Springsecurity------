package org.schoole.controller;


import org.schoole.domain.Order;
import org.schoole.domain.ResponseResult;
import org.schoole.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OrderController {


    @Autowired
    OrderService orderService ;
    // 返回所有订单信息
    @RequestMapping("/order/list")
    public ResponseResult allOrder(@RequestParam(value = "username" , required = false) String userName,
                                   @RequestParam(value = "fixmanname", required = false) String fixmanName,
                                   @RequestParam(value = "pageNo") Long pageNo,
                                   @RequestParam(value = "pageSize") Long pageSize){
        Map<String,Object> data = new HashMap<>();
        data = orderService.orderList(userName,fixmanName,pageNo,pageSize);
        return new ResponseResult<>(200,"success",data) ;
    }

    // 新增订单接口
    @PostMapping("/order")
    public ResponseResult addOrder(@RequestBody Order order){
        orderService.addOrder(order);
        return new ResponseResult(200,"success",null);

    }

    // 修改订单接口
    @PutMapping("/order")
    public ResponseResult updateOrder(@RequestBody Order order){
        orderService.updateOrder(order);
        return new ResponseResult(200,"success",null);

    }
    // 根据id查询用户信息接口
    @GetMapping("/order/{id}")
    public ResponseResult getOrderById(@PathVariable("id") Integer id){
        Order order = orderService.getById(id) ;
        return new ResponseResult<>(200,"success",order);
    }
    @DeleteMapping("/order/{id}")
    public ResponseResult deleteOrderById(@PathVariable("id") Integer id){
        orderService.deleteById(id) ;
        return new ResponseResult<>(200,"success",null);
    }

}
