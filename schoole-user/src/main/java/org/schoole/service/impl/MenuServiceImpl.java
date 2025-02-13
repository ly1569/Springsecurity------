package org.schoole.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.schoole.domain.Menu;
import org.schoole.mapper.MenuMapper;
import org.schoole.service.GetMenuListByid;
import org.schoole.service.MenuService;
import org.schoole.service.selectPermsByUserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService {

//   @Autowired
//    MenuMapper menuMapper ;

    @Resource
    MenuMapper menuMapper ;

    @Resource
    GetMenuListByid getMenuListByid1 ;

    @Override
    public List<Menu> getAllMenu() {

        System.out.println(("成功进入menuServiceImpl的getAllMenu 函数中来"));
        // 一般菜单
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId,0);
        System.out.println("测试 目的函数之前");
        List<Menu> menuList = menuMapper.selectList(wrapper);
        System.out.println("成功进入menuServiceImpl 的munuList:"+menuList);
        // 填充子菜单,抽取为私有递归获取子菜单的方法
        setMenuChildren(menuList);

        return menuList;
    }

    private void setMenuChildren(List<Menu> menuList) {
        if (menuList != null){
            for (Menu menu : menuList){
                LambdaQueryWrapper<Menu> subWrapper = new LambdaQueryWrapper<>();
                // 查询父 id 为传进来的 menuList 中的 id 的 id
                subWrapper.eq(Menu::getParentId,menu.getId());
                List<Menu> subMenuList = menuMapper.selectList(subWrapper);
                menu.setChildren(subMenuList);
            }
        }
    }

//  通过用户id查询菜单集合
    @Override
    public List<Menu> getMenuListByUserId(Integer userId) {

//        menuMapper.selectPermsByUserId
        // 一级菜单
//        List<Menu> menuList = menuMapper.getMenuListByUserId(userId , 0) ;
        Map data = new HashMap<>() ;
        List<Menu> menuList = getMenuListByid1.UserServiceImplGetMenuListByid(userId,0);
//        System.out.println("data"+data);
//        List<Menu> menuList = (List<Menu>) data.get("menuList");
        System.out.println("没有子菜单的时候:   "+menuList);
        // 子菜单
        setMenuChildrenByUserId(userId, menuList);

        return menuList;
    }

    private void setMenuChildrenByUserId(Integer userId, List<Menu> menuList) {
        if (menuList != null ){
            for (Menu menu : menuList){
//                List<Menu> subMenuList = menuMapper.getMenuListByUserId(userId, Math.toIntExact(menu.getId()));
                List<Menu> subMenuList = getMenuListByid1.UserServiceImplGetMenuListByid(userId, Math.toIntExact(menu.getId()));
                menu.setChildren(subMenuList);
                //递归
                setMenuChildrenByUserId(userId,subMenuList);
            }
        }
    }
}
