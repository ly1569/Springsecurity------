package org.schoole.service;

import org.schoole.domain.FaultType;

import java.util.List;
import java.util.Map;

public interface FaultService {
    // 获取故障品牌方法
    public List<String> deviceBrand(String deviceType) ;
    // 设备故障类型
    List<String> deviceFaultDesc(String deviceType);
    // 服务方式描述
    List<String> serviceType(String deviceType);

    // 用户信息回填
    void faultBackFilling(FaultType faultType);

    // 获取用户所有订单
    Map<String, Object> getFaultList(Long pageNo, Long pageSize, String deviceType, String deviceBrand);

    // 维修员获取用户未接订单
    Map<String,Object> getFaultFutureList(Long pageNo, Long pageSize, String deviceType, String deviceBrand);

    // 维修员接单
    void receiveOrder(Integer id);

    // 维修员已接单显示
    Map<String, Object> getFaultPastList(Long pageNo, Long pageSize, String deviceType, String deviceBrand);
}
