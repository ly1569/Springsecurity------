package org.schoole.controller;


import org.schoole.domain.ResponseResult;
import org.schoole.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DeviceController {
    // 所有model
    @Autowired
    DeviceService deviceService ;

    @GetMapping("/all/type")
    public ResponseResult getAllModel(){

        Map data = new HashMap<>() ;
        data = deviceService.getAllModel();
        return new ResponseResult<>(200,"success",data);
    }
    // 获取机型所有信息
    @GetMapping("/model/info")
    public ResponseResult getModelInfo(String Model ){

        Map data = new HashMap<>() ;
        data = deviceService.getModelInfo( Model );
        return new ResponseResult<>(200,"success",data);
    }


}
