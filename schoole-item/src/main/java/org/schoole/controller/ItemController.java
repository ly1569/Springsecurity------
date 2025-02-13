package org.schoole.controller;

import org.schoole.domain.Item;
import org.schoole.domain.ResponseResult;
import org.schoole.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ItemController {
    @Autowired
    ItemService itemService;
    // 获取所有零件
    @GetMapping("/item/list")
    public ResponseResult getItemList(@RequestParam(value = "pageNo") Long pageNo ,
                                      @RequestParam(value = "pageSize") Long pageSize ,
                                      @RequestParam(value = "deviceType",required = false) String deviceType ,
                                      @RequestParam(value = "deviceBrand",required = false) String deviceBrand){
        Map<String,Object> data = new HashMap<>() ;
        data = itemService.getItemList(pageNo,pageSize,deviceType,deviceBrand) ;
        return new ResponseResult(200,"success",data);
    }

    // 添加零件
    @PostMapping("/item")
    public ResponseResult addItem(@RequestBody Item item){

        itemService.addItem(item) ;

        return new ResponseResult<>(200,"success",null) ;
    }
    // 获取零件返回信息
    @GetMapping("/item/{id}")
    public ResponseResult getItemById(@PathVariable("id") Integer id){
        Item item = itemService.getItemInfoById( id) ;
        return new ResponseResult<>(200,"success",item) ;
    }

    // 更新零件信息
    @PutMapping("/item")
    public ResponseResult updateItem(@RequestBody Item item){
        itemService.updateItemById(item) ;
        return new ResponseResult<>(200,"success",null) ;
    }
    // 删除零件信息
    @DeleteMapping("/item/{id}")
    public ResponseResult deleteItemById(@PathVariable("id") Integer id){
        itemService.deleteItemById(id) ;
        return new ResponseResult<>(200,"success",null) ;
    }
}
