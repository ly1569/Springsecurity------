package org.schoole.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.schoole.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    // 查询订单集合
    @Select("select * from sys_order")
    public List<Order> getAllOrder();

    // 查询订单的最大 id
    @Select("select max(id) from sys_order")
    public Integer selectMaxOrderId();

    // 查询订单 id
    @Select("select id from sys_order where user_name=#{userName} and fix_type=#{fixType} and fixman_name=#{fixmanName}")
    public Integer selectOrderId(String userName ,String fixType , String fixmanName ) ;
}
