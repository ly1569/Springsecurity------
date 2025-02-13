package org.schoole.service;

import org.schoole.domain.Menu;
import org.schoole.domain.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {

    // 查询角色接口
    Map<String, Object> roleList(String name, Long pageNo, Long pageSize);

    // 新增角色接口
    void addRole(Role role);

    // 通过id查询角色信息接口
    Role getRoleInfoById(Integer id);

    // 更新角色 id 接口
    void updateoleById(Role role);

    // 删除角色接口
    void deleteRoleById(Integer id);

    //查询所有角色接口
    List<Role> List();
// user模块 UserDetailsServiceImpl服务中的   List<String> list = menuMapper.selectPermsByUserId(user.getId());
    List<String> menuMapperSelectPermsByUserId(Integer id);

    // user模块 UserServiceImpl服务中的  getMenuListByid 方法
    List<Menu> UserServiceImplGetMenuListByid(Integer userId, Integer pid);
}
