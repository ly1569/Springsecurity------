package org.schoole.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import org.schoole.domain.Item;
import org.schoole.mapper.ItemMapper;
import org.schoole.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemMapper itemMapper ;


    // 所有零件
    @Override
    public Map<String, Object> getItemList(Long pageNo, Long pageSize, String deviceType, String deviceBrand) {
        //条件
        LambdaQueryWrapper<Item> wrapper = new LambdaQueryWrapper<>() ;
        wrapper.eq(StringUtils.hasLength(deviceType),Item::getDeviceType,deviceType) ;
        wrapper.eq(StringUtils.hasLength(deviceBrand),Item::getDeviceBrand,deviceBrand);

        //分页
        Page<Item> page = new Page<>(pageNo,pageSize) ;
        itemMapper.selectPage(page,wrapper) ;

        Map<String,Object> data = new HashMap<>() ;
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords()) ;

        return data ;
    }
    // 添加零件
    @Override
    public void addItem(Item item) {
        Integer id = itemMapper.maxItemId()+1 ;
        item.setId(id);
        itemMapper.insert(item) ;
    }
    //获取零件信息
    @Override
    public Item getItemInfoById(Integer id) {
        Item item = itemMapper.selectById(id) ;
        return item;
    }
    // 更新零件信息
    @Override
    public void updateItemById(Item item) {
        itemMapper.updateById(item) ;
    }
    // 删除零件信息
    @Override
    public void deleteItemById(Integer id) {
        itemMapper.deleteById(id) ;
    }
}
