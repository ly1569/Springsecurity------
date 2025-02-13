package org.schoole.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.schoole.domain.FaultType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface FaultMapper extends BaseMapper<FaultType> {
    // 设备品牌
    @Select("select distinct device_brand from sys_fault_type where device_type=#{deviceType} and user_name is null")
    public List<String> deviceBrand(String deviceType) ;

    // 前台查询
    @Select("select distinct device_brand from sys_fault_type where  user_name is null")
    public List<String> deviceBrand1(String deviceType) ;

    // 设备故障描述
    @Select("select distinct fault_type from sys_fault_type where device_type=#{deviceType} and user_name is null")
    List<String> deviceFaultDesc(String deviceType);

    // 前台故障
    @Select("select distinct fault_type from sys_fault_type where user_name is null")
    List<String> deviceFaultDesc1(String deviceType);

    // 服务方式
    @Select("select distinct service_type from sys_fault_type where device_type=#{deviceType} and user_name is null")
    List<String> serviceType(String deviceType);

    // 前台信息
    @Select("select distinct service_type from sys_fault_type where user_name is null")
    List<String> serviceType1(String deviceType);

    // 获取错误信息中的最大id
    @Select("select max(id) from sys_fault_type")
    public Integer maxIDFaultType() ;

    // 维修员写入订单
    @Update("update sys_fault_type set fix_man = #{fix_man},receive_time = now() where id = #{id}")
    public void receiveOrder(String fix_man , Integer id) ;



}
