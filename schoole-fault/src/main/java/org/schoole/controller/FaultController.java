package org.schoole.controller;

import org.schoole.domain.FaultType;
import org.schoole.domain.ResponseResult;
import org.schoole.service.FaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@Validated
public class FaultController {
    @Autowired
    FaultService faultService ;

    // 初始错误信息
    @GetMapping("/fault/info")
    public ResponseResult getFaultInfo(@PathParam("deviceTypede") String deviceType){
        HashMap<String , Object> data = new HashMap<>() ;
        // 设备品牌
        List<String> deviceBrands = faultService.deviceBrand(deviceType) ;
        data.put("deviceBrand",deviceBrands) ;
        // 设备故障描述
        List<String> deviceFaultDesc = faultService.deviceFaultDesc(deviceType) ;
        data.put("deviceFaultDesc",deviceFaultDesc);
        // 服务方式
        List<String> serviceType = faultService.serviceType(deviceType) ;
        data.put("serviceType",serviceType) ;
        return new ResponseResult<>(200,"success",data) ;
    }



    // 前台获取 维修品牌  故障描述  服务方式
    @GetMapping("/fault/info1")
    public ResponseResult getFaultInfo1(String deviceType){
        System.out.println(deviceType);
        HashMap<String , Object> data = new HashMap<>() ;
        // 设备品牌
        List<String> deviceBrands = faultService.deviceBrand(deviceType) ;
        data.put("deviceBrand",deviceBrands) ;
        // 设备故障描述
        List<String> deviceFaultDesc = faultService.deviceFaultDesc(deviceType) ;
        data.put("deviceFaultDesc",deviceFaultDesc);
        // 服务方式
        List<String> serviceType = faultService.serviceType(deviceType) ;
        data.put("serviceType",serviceType) ;
        return new ResponseResult<>(200,"success",data) ;
    }


    // 用户错误订单回填 用post 请求体的方法 报错 400
    @PostMapping("/fault/back")
    public ResponseResult faultBackFilling(@RequestBody @Validated FaultType faultType){

        System.out.println("faultController中的用户错误信息回填"+faultType);
        faultService.faultBackFilling(faultType) ;
        return new ResponseResult<>(200,"success",null) ;
    }

    // 前台下单信息
//    @GetMapping("/fault/submit")
//    public ResponseResult faultFrontSubmit(@RequestParam(value = "username") String userName,
//                                           @RequestParam(value = "password") String password,
//                                           @RequestParam(value = "phonenumber") String phonenumber,
//                                           @RequestParam(value = "faultInfo1") String faultInfo1,
//                                           ){
//
//        // 判断用户是否存在
//
//
//
//
//        // 进行订单填写
//        System.out.println("faultController中的用户错误信息回填"+faultType);
//        faultService.faultBackFilling(faultType) ;
//        return new ResponseResult<>(200,"success",null) ;
//    }




    // 用户所有订单回显
    @GetMapping("/fault/list")
    public ResponseResult faultList(@RequestParam(value = "pageNo") Long pageNo,
                                    @RequestParam(value = "pageSize") Long pageSize,
                                    @RequestParam(value = "deviceType" , required = false)  String deviceType,
                                    @RequestParam(value = "deviceBrand" , required = false)  String deviceBrand){
        Map<String,Object> data = new HashMap<>();
        data = faultService.getFaultList(pageNo,pageSize,deviceType,deviceBrand) ;
        return new ResponseResult<>(200,"success",data);
    }




// 该请求方法前端get方法传参还有些许问题，以后再做研究
// --------------------------------------------------------------------------------------------------------
    // 前端故障回写
//    @GetMapping("/fault/back")
//    public ResponseResult faultBackFilling( FaultType faultType){
//
//        faultService.faultBackFilling(faultType);
//
//        return  new ResponseResult<>(200,"success",null) ;
//    }
//     前端故障回写
//    @GetMapping("/fault/back")
//    public ResponseResult faultBackFilling(@RequestParam(value = "deviceType") String deviceType,
//                                           @RequestParam(value = "deviceBrand") String deviceBrand,
//                                           @RequestParam(value = "faultReason") String[] faultReason,
//                                           @RequestParam(value = "serviceType") String serviceType,
//                                           @RequestParam(value = "customerFaultDesc") String customerFaultDesc,
//                                           @RequestParam(value = "urgent") Integer urgent){
//        FaultType faultType1 = new FaultType() ;
//        faultType1.setDeviceType(deviceType);
//        faultType1.setDeviceBrand(deviceBrand);
//        faultType1.setFaultType(Arrays.toString(faultReason));
//        faultType1.setServiceType(serviceType);
//        faultType1.setCustomerFaultDesc(customerFaultDesc);
//        faultType1.setUrgent(urgent);
//        faultService.faultBackFilling(faultType1);
//
//        return  new ResponseResult<>(200,"success",null) ;
//    }
// 该请求方法前端get方法传参还有些许问题，以后再做研究
// -------------------------------------------------------------------------------------------------------




    // 维修员获取未接单的订单
    @GetMapping("/fault future/list")
    public ResponseResult getFaultFutureList(@RequestParam(value = "pageNo") Long pageNo,
                                                 @RequestParam(value = "pageSize") Long pageSize,
                                                 @RequestParam(value = "deviceType" , required = false)  String deviceType,
                                                 @RequestParam(value = "deviceBrand" , required = false)  String deviceBrand){

//        System.out.println("FaultController中的数据为：" + );

        Map<String,Object> data = new HashMap<>() ;
        data = faultService.getFaultFutureList(pageNo,pageSize,deviceType,deviceBrand) ;
        return new ResponseResult<>(200,"success",data) ;
    }


//    维修员接单接口
    @GetMapping("/receive/order")
    public ResponseResult receiveOrder(@RequestParam("id") Integer id){
        faultService.receiveOrder(id) ;
        return new ResponseResult<>(200,"接单成功",null) ;
    }

//    维修员获取已接订单接口
    @GetMapping("/fault past/list")
    public ResponseResult getFaultPastList(@RequestParam(value = "pageNo") Long pageNo,
                                           @RequestParam(value = "pageSize") Long pageSize,
                                           @RequestParam(value = "deviceType" , required = false)  String deviceType,
                                           @RequestParam(value = "deviceBrand" , required = false)  String deviceBrand){
        Map<String,Object> data = new HashMap<>();
        data = faultService.getFaultPastList(pageNo,pageSize,deviceType,deviceBrand) ;

        return new ResponseResult<>(200,"success",data) ;
    }


    // 前台提交图片信息

    @PostMapping("/fault/submit")
    public ResponseResult frontSubmint(FaultType faultType, MultipartFile[] files) throws IOException {

        String path = "D://upload//";
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            String filename = file.getOriginalFilename();
            System.out.println(filename);
            String realFileName = UUID.randomUUID() + filename;//这个图片名保存数据库
            fileNames.add(realFileName);
            File targetFile = new File(path+ UUID.randomUUID() + realFileName);

            file.transferTo(targetFile);
        }

        //入库

        return new ResponseResult<>(200,"success",null);
    }
}
