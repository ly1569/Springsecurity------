package org.schoole.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.schoole.domain.FaultType;
import org.schoole.mapper.FaultMapper;
import org.schoole.mapper.UserRoleMapper;
import org.schoole.service.FaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FaultServiceImpl implements FaultService {

    @Autowired
    FaultMapper faultMapper ;
    @Autowired
    UserRoleMapper userRoleMapper ;
    // 设备品牌
    @Override
    public List<String> deviceBrand(String deviceType) {
        if (deviceType == null){
            return faultMapper.deviceBrand1(deviceType);
        }
        return faultMapper.deviceBrand(deviceType);
    }
    // 设备故障描述
    @Override
    public List<String> deviceFaultDesc(String deviceType) {
        if (deviceType == null ){
            return faultMapper.deviceFaultDesc1(deviceType) ;
        }
        return faultMapper.deviceFaultDesc(deviceType) ;
    }
    // 服务方式
    @Override
    public List<String> serviceType(String deviceType) {
        if (deviceType == null){
            return faultMapper.serviceType1(deviceType) ;
        }
        return faultMapper.serviceType(deviceType) ;
    }


    // 用户信息回填
    @Override
    public void faultBackFilling(FaultType faultType) {
        //设置创建时间
        faultType.setCreateTime(LocalDateTime.now());
        //设置新增 id
        faultType.setId(faultMapper.maxIDFaultType()+1);
        // 将用户id和当前id绑定
        // 从当前上下文中获取用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        faultType.setUserName(username);
        // 将前端的faultReasons 中的内容放到 faultType 中
        faultType.setFaultType(faultType.getFaultReasons().toString());
        faultMapper.insert(faultType) ;
    }

    // 获取用户所有订单接口
    @Override
    public Map<String, Object> getFaultList(Long pageNo, Long pageSize, String deviceType, String deviceBrand) {

        // 设置条件
        LambdaQueryWrapper<FaultType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(deviceType),FaultType::getDeviceType,deviceType);
        wrapper.eq(StringUtils.hasLength(deviceBrand),FaultType::getDeviceBrand,deviceBrand);
        // 需要一个默认条件自带用户姓名
        String username = SecurityContextHolder.getContext().getAuthentication().getName() ;
        wrapper.eq(FaultType::getUserName,username);
        // 分页
        Page<FaultType> page = new Page<>(pageNo,pageSize);

        faultMapper.selectPage(page,wrapper);

        Map<String,Object> data = new HashMap<>() ;

        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());

        // 获取当前用户角色
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = userRoleMapper.roleName(userName);
        data.put("roleName",role);

        return data;
    }

    // 维修员获取用户未接单订单
    @Override
    public Map<String,Object> getFaultFutureList(Long pageNo, Long pageSize, String deviceType, String deviceBrand) {

        // 设置Lambda表达式
        LambdaQueryWrapper<FaultType> wrapper = new LambdaQueryWrapper<>() ;
        wrapper.eq(StringUtils.hasLength(deviceType),FaultType::getDeviceType,deviceType) ;
        wrapper.eq(StringUtils.hasLength(deviceBrand),FaultType::getDeviceBrand,deviceBrand);
        wrapper.eq(FaultType::getFixMan,"待接单中") ;
        // 分页
        Page<FaultType> page = new Page<>(pageNo,pageSize);
        faultMapper.selectPage(page,wrapper) ;

        Map<String,Object> data = new HashMap<>() ;
        data.put("totalFuture",page.getTotal()) ;
        data.put("rows",page.getRecords()) ;

        return data;
    }

    // 维修员接单
    @Override
    public void receiveOrder(Integer id) {
        // 从上下文中获取名字
        String fixman = SecurityContextHolder.getContext().getAuthentication().getName() ;
        // 将订单写入订单表
        faultMapper.receiveOrder(fixman,id) ;
    }

    @Override
    public Map<String, Object> getFaultPastList(Long pageNo, Long pageSize, String deviceType, String deviceBrand) {

        // 条件
        LambdaQueryWrapper<FaultType> wrapper = new LambdaQueryWrapper<>() ;
        wrapper.eq(StringUtils.hasLength(deviceType),FaultType::getDeviceType,deviceType);
        wrapper.eq(StringUtils.hasLength(deviceBrand),FaultType::getDeviceBrand,deviceBrand);
        String fix_man = SecurityContextHolder.getContext().getAuthentication().getName() ;
        wrapper.eq(FaultType::getFixMan,fix_man) ;
        // 分页
        Page<FaultType> page = new Page<>(pageNo,pageSize) ;
        faultMapper.selectPage(page,wrapper) ;

        Map<String,Object> data = new HashMap<>() ;
        data.put("totalPast",page.getTotal()) ;
        data.put("rows",page.getRecords()) ;


        return data;
    }
}
