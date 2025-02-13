package org.schoole.service;

import java.util.Map;

public interface DeviceService {
    //获取所有model
    Map getAllModel();
    // 获取机型信息
    Map getModelInfo(String model);
}
