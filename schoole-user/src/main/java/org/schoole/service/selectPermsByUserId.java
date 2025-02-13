package org.schoole.service;


import org.schoole.domain.Menu;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "role-service",contextId = "selectPermsByUserId")
public interface selectPermsByUserId {
    // 获取用户权限
    @GetMapping(value = "/UserDetailsServiceImpl/menuMapper.selectPermsByUserId")
    List<String> PermsList(@RequestParam(value = "id") Integer id);
}




