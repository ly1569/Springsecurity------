package org.schoole.controller;

import org.schoole.domain.Menu;
import org.schoole.domain.ResponseResult;
import org.schoole.domain.Role;
import org.schoole.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RoleController {

//    1.进行角色数据查询
    @Autowired
    RoleService roleService ;
    @RequestMapping("/role/list")
    public ResponseResult roleList(@RequestParam(value = "name" , required = false) String name ,
                                   @RequestParam(value = "pageNo" ) Long pageNo ,
                                   @RequestParam(value = "pageSize") Long pageSize
                                   ){
//        Map<String , Object> data = new HashMap<>() ;
//        System.out.println("RoleController中："  + "pageNO:"+pageNo + "pageSize:"+pageSize + "name" + name);
//        data =  roleService.roleList(name, pageNo , pageSize) ;
//        return new ResponseResult(200 , "success" , data) ;
        Map<String,Object> data = new HashMap<>();
        System.out.println("前端传过来的数据 "+ "name: "+name + " pageNo: "+ pageNo + " pageSize:"+pageSize);
        data = roleService.roleList(name , pageNo ,pageSize ) ;
        return new ResponseResult(200,"success" , data) ;
    }


//    2.新增角色接口
    @PostMapping("/role")
    public ResponseResult addUser(@RequestBody Role role){
        System.out.println("在前端传过来的role："+ role);
        roleService.addRole(role);
        return new ResponseResult(200,"success" , null) ;
    }

    //    3.修改角色接口
    // 通过id查询角色信息
    @GetMapping("/role/{id}")
    public ResponseResult getRoleById(@PathVariable("id") Integer id){
        Role role = roleService.getRoleInfoById(id);
        return new ResponseResult(200,"success",role) ;
    }
    // 再通过前端传递过来的数据，识别id通过id 来进行更新
    @PutMapping("/role")
    public ResponseResult updateRole(@RequestBody Role role){
        System.out.println("在前端传过来的修改role："+ role);
        // 当实体类属性为 null 时表示该实体类的该属性不会被修改
        roleService.updateoleById(role);
        return new ResponseResult(200,"success",null);
    }
// 修改用户接口结束

//   4 删除角色接口
    @DeleteMapping("/role/{id}")
    public ResponseResult deleteRoleById(@PathVariable("id") Integer id){
        roleService.deleteRoleById(id);
        return new ResponseResult(200,"success",null);
    }

    // 返回所有的角色
    @GetMapping("/role/all")
    public ResponseResult getAllRole(){
        List<Role> roleList = roleService.List();
        return new  ResponseResult(200,"success",roleList) ;
    }


    // user模块 UserDetailsServiceImpl服务中的   List<String> list = menuMapper.selectPermsByUserId(user.getId());
    @GetMapping("/UserDetailsServiceImpl/menuMapper.selectPermsByUserId")
    public List<String> menuMapperSelectPermsByUserId(@RequestParam(value = "id") Integer id){
        List<String> stringList =  roleService.menuMapperSelectPermsByUserId(id) ;
        return stringList ;
    }

    // user模块 UserServiceImpl服务中的  getMenuListByid 方法
//    @GetMapping("/UserServiceImpl/getMenuListByid")
//    List<Menu> UserServiceImplGetMenuListByid(@RequestParam("userId") Integer userId , @RequestParam(value = "pid") Integer pid){
//
//        List<Menu> menuList = roleService.UserServiceImplGetMenuListByid(userId,pid);
//        return menuList ;
//    }
    @GetMapping ("/UserServiceImpl/getMenuListByid")
    List<Menu> UserServiceImplGetMenuListByid(@RequestParam("userId") Integer userId , @RequestParam(value = "pid") Integer pid) {
        List<Menu> menuList = roleService.UserServiceImplGetMenuListByid(userId,pid);
        Map data =  new HashMap<>() ;
        data.put("menuList",menuList);
        return menuList;
    }


}
