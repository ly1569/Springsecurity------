package org.schoole.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.schoole.domain.Menu;
import org.schoole.domain.Role;
import org.schoole.domain.RoleMenu;
import org.schoole.mapper.MenuMapper;
import org.schoole.mapper.RoleMapper;
import org.schoole.mapper.RoleMenuMapper;
import org.schoole.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Lazy
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper ;

    @Resource
    MenuMapper menuMapper ;

    // 查询角色接口
    @Override
    public Map<String, Object> roleList(String name, Long pageNo, Long pageSize) {

//  检查 pageNo 和 pageSize
        System.out.println("实现类中接受的数据：" + "name: "+name + " pageNo: "+ pageNo + " pageSize"+ pageSize) ;
//        // 条件查询
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>() ;
        wrapper.eq(StringUtils.hasLength(name),Role::getName,name);
        wrapper.orderByDesc(Role::getId) ;
        System.out.println("wrapper: "+wrapper);
        //分页
        Page<Role> page = new Page<>(pageNo,pageSize) ;
        roleMapper.selectPage(page,wrapper) ;
        Map<String , Object> data = new HashMap<>() ;
        // 检查 page 中 total 和 records
        System.out.println("total :" + page.getTotal());
        System.out.println("records:" + page.getRecords());
        data.put("total" , page.getTotal());
        data.put("rows" , page.getRecords()) ;
        System.out.println("records:" + page.getRecords());
        System.out.println("RoleServiceImpl中 data: " + data);
        return data ;
    }

    // 新增角色接口
    @Autowired
    RoleMenuMapper roleMenuMapper ;
    @Override
    @Transactional
    public void addRole(Role role) {
//      在 角色表中插入该角色
        Long count = (long) (roleMapper.getRoleMaxId()+1);
        System.out.println("count:"+count);
        role.setId(count);
        System.out.println("设置完id后的role"+role);
        roleMapper.insert(role) ;
//      角色中存在一个 menuList 的集合属性，此时需要从 menuList 中获取 对应的menu值然后插入 sys_role_menu中
        if (role.getMenuIdList() != null){
            for (Integer menuId : role.getMenuIdList() ){
//                    roleMenu 实体类中 roleId 是Long 类型 Menu id 是 Integer 类型
                    roleMenuMapper.insert(new RoleMenu(role.getId(),menuId)) ;
            }
        }



    }

    // 通过 id 来查询角色信息
    @Override
    public Role getRoleInfoById(Integer id) {
//        System.out.println("rolemapper查询用户信息实现"+roleMapper.selectById(id));
        Role role = new Role() ;
        role = roleMapper.selectById(id) ;
        List<Integer> menuIdList = roleMenuMapper.getMenuIdListByRoleId(id) ;
        role.setMenuIdList(menuIdList);
        return role;
    }

    // 通过 id 来更新角色
    @Override
    @Transactional
    public void updateoleById(Role role) {
        // 修改角色表( 角色名 ，角色描述之类的)
        roleMapper.updateById(role) ;
        // 删除原有权限
//        1.创建 wrapper的条件
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>() ;
        wrapper.eq(RoleMenu::getRoleId,role.getId());
//        2.调用 roleMenuMapper 工具 进行删除
        roleMenuMapper.delete(wrapper) ;
        //新增权限
        if (role.getMenuIdList() != null){
            for (Integer menuId : role.getMenuIdList() ){
//                    roleMenu 实体类中 roleId 是Long 类型 Menu id 是 Integer 类型
                roleMenuMapper.insert(new RoleMenu(role.getId(),menuId)) ;
            }
        }


    }
    // 删除角色接口
    @Override
    @Transactional
    public void deleteRoleById(Integer id) {
        // 删除原有权限
//        1.创建 wrapper的条件
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>() ;
        wrapper.eq(RoleMenu::getRoleId,id);
//        2.调用 roleMenuMapper 工具 进行删除
        roleMenuMapper.delete(wrapper) ;
        roleMapper.deleteById(id) ;
    }

    @Override
    public List<Role> List() {
        List<Role> roleList =  roleMapper.selectList(null) ;
        return roleList;
    }
// user模块 UserDetailsServiceImpl服务中的   List<String> list = menuMapper.selectPermsByUserId(user.getId());
    @Override
    public List<String> menuMapperSelectPermsByUserId(Integer id) {
       List<String> stringList = menuMapper.menuMapperSelectPermsByUserId(id) ;
        return stringList;
    }

    // user模块 UserServiceImpl服务中的  getMenuListByid 方法
    @Override
    public List<Menu> UserServiceImplGetMenuListByid(Integer userId, Integer pid) {

        List<Menu> menuList = menuMapper.UserServiceImplGetMenuListByid(userId,pid);
        return menuList;
    }
}
