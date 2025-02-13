package org.schoole.service.impl;

import org.schoole.domain.DeviceType;
import org.schoole.mapper.DeviceMapper;
import org.schoole.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    DeviceMapper deviceMapper ;

    //获取所有model
    @Override
    public Map getAllModel() {
        Map data = new HashMap<>() ;
        List<String> rows = deviceMapper.getAllModel() ;
        data.put("rows",rows) ;
        return  data ;
    }
    // 获取机型信息
    @Override
    public Map getModelInfo(String model) {
        Map data  = new HashMap<>() ;
        List<DeviceType> deviceTypes = deviceMapper.getModelInfo(model);
        data.put("rows",deviceTypes) ;
        List<String> Brands = deviceMapper.getBrandByModel(model) ;
        data.put("brands",Brands) ;
        return data;
    }

}
