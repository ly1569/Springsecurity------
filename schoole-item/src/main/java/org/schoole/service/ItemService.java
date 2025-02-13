package org.schoole.service;

import org.schoole.domain.Item;

import java.util.Map;

public interface ItemService {


    // 获取所有零件信息
    Map<String, Object> getItemList(Long pageNo, Long pageSize, String deviceType, String deviceBrand);

    // 添加零件
    void addItem(Item item);

    // id获取零件信息
    Item getItemInfoById(Integer id);

    // id更新item信息
    void updateItemById(Item item);
    // 删除id
    void deleteItemById(Integer id);
}
