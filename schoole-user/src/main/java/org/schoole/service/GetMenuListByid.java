package org.schoole.service;

import org.schoole.domain.Menu;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

//@FeignClient(value = "role-service",contextId = "GetMenuListByid")
//public interface GetMenuListByid {
//
//    // 获取用户菜单列表menuList
//    @GetMapping(value = "/UserServiceImpl/getMenuListByid")
//    List<Menu> UserServiceImplGetMenuListByid(@RequestParam(value = "userId") Integer userId , @RequestParam(value = "pid") Integer pid);
//}
@FeignClient(value = "role-service", contextId = "GetMenuListByid")
public interface GetMenuListByid {

    // 获取用户菜单列表menuList
    @GetMapping(value = "/UserServiceImpl/getMenuListByid")
    List<Menu> UserServiceImplGetMenuListByid(@RequestParam(value = "userId") Integer userId, @RequestParam(value = "pid") Integer pid);
}

