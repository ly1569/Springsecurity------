package org.schoole.service;

import org.schoole.domain.Menu;

import java.util.List;

public interface MenuService {

//    直接获取菜单
    List<Menu> getAllMenu();

//    通过用户id和父节点id来获取菜单
    List<Menu> getMenuListByUserId(Integer userId);
}
