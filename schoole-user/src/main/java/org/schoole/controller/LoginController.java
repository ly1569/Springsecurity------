package org.schoole.controller;

import org.schoole.domain.Menu;
import org.schoole.domain.ResponseResult;
import org.schoole.domain.User;
import org.schoole.domain.userForRegister;
import org.schoole.service.LoginServcie;
import org.schoole.service.UserService;
import org.schoole.validation.groups.AddUser;
import org.schoole.validation.groups.Login;
import org.schoole.validation.groups.Register;
import org.schoole.validation.groups.UpdateUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Validated
@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private LoginServcie loginServcie;
    @Autowired
    UserService userService;

    // 调用login组验证登录账号密码是否为空
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody @Validated(value = {Login.class}) User user) {
        //登录
        System.out.println("进入LoginController./uer/login函数成功" + user.getUserName() + ":" + user.getPassword());
        //login返回的类型是 ResponseResult
        return loginServcie.login(user);
    }
// 就被拦截了？？？
//    关于拦截器请求体中的坑： 1. 使用get请求 postman 中 params 中添加token 没有拦截器的情况下直接把get请求体中的
//    参数传递给函数括号中的参数 2. 但是在添加了spring拦截器的情况下，如果没有在请求体中添加token参数，拦截器识别不了该
//    请求，拦截器是在请求头中添加获取token参数的，而不是通过 参数params 来获取token参数的


    @RequestMapping("/user/info")
//    @PreAuthorize("@ex.hasAuthority('system:dept:list')")
//    @PreAuthorize("hasAnyAuthority('admin','test','system:dept:list')")
//    @PreAuthorize("hasRole('system:dept:list')")
//    @PreAuthorize("hasAnyRole('admin','system:dept:list')")
//    @PreAuthorize("hasAnyAuthority('test')")
    public ResponseResult info(@RequestParam(value = "token") @NotBlank(message = "获取用户信息token不能为空") String token) {
        // 返回一个 Map 类型的的 data ，data中带有 token ，还带有 menuList 的父子菜单集合
        System.out.println("token ：" + token);
        // 从上下文中获取用户名字
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
//      1.首先 将 token 放入data 中
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("name", name);


        // 再将 menuList 的 集合 List<Menu> 放到 hashMape 数组中
//        1.定义方法 返回List<Menu> 集合
        List<Menu> menuListByUserId = userService.getMenuListById(token);
//        2.将menuListByUserId放入data中
        data.put("menuList", menuListByUserId);
        return new ResponseResult(200, "成功", data);
    }

//    post方法测试成功，但是爆了一些网络问题
//    @PostMapping("/user/logout")
//    public ResponseResult logout(@RequestBody String token){
//        System.out.println("token："+token);
//        return loginServcie.logout();
//    }


    //    1.下面是注销的get请求方法的内容，测试 成功 配合user.js中的测试注销的get请求方法
    @RequestMapping("/user/logout")
    public ResponseResult logout() {
        return loginServcie.logout();
    }


    //    1.进行用户数据查询
    @RequestMapping("/user/list")
    public ResponseResult userList(@RequestParam(value = "username", required = false) String username,
                                   @RequestParam(value = "phone", required = false) String phone,
                                   @RequestParam(value = "pageNo") @PositiveOrZero(message = "分页数据必须为正整数") Long pageNo,
                                   @RequestParam(value = "pageSize") @PositiveOrZero(message = "分页数据必须为正整数")  Long pageSize
    ) {
        Map<String, Object> data = new HashMap<>();
        System.out.println("LoginController中：" + "pageNO:" + pageNo + "pageSize:" + pageSize);
        System.out.println("LoginController中：" + "username:" + username + "phone:" + phone);
        data = userService.userList(username, phone, pageNo, pageSize);
        return new ResponseResult(200, "success", data);
    }


    //    2. 管理员界面新增用户
    // 调用AddUser组判断用户名，密码，手机号，邮箱是否合法
    @PostMapping("/user")
    public ResponseResult addUser(@RequestBody @Validated(value = {AddUser.class}) User user) throws Exception {
        System.out.println("在前端传过来的user：" + user);
        userService.addUser(user);
        return new ResponseResult(200, "success", null);
    }


    //    3.用户注册接口
    // 调用Register组怕段用户名，密码，手机号，邮箱，是否合法
    @PostMapping("/user/login/register")
    public ResponseResult registerUser(@RequestBody @Validated(value = {Register.class}) userForRegister userForRegister) throws Exception {
        System.out.println("在前端传过来的注册user：" + userForRegister);
        userService.registerUser(userForRegister);
        return new ResponseResult(200, "success", null);
    }

    //    4.修改用户接口
    // 通过id查询用户信息
    @GetMapping("/user/{id}")
    public ResponseResult getUserById(@PathVariable("id") Integer id) {
        User user = userService.getUserInfoById(id);

        return new ResponseResult(200, "success", user);
    }

    // 再通过前端传递过来的数据，识别id通过id 来进行更新
    @PutMapping("/user")
    // 数据校验
    public ResponseResult updateUser(@RequestBody @Validated(value = {UpdateUser.class}) User user) {
        System.out.println("在前端传过来的修改user：" + user);
        // 当实体类属性为 null 时表示该实体类的该属性不会被修改
        user.setPassword(null);

        try {
            userService.updateUserById(user);
            return new ResponseResult(200, "success", null);
        } catch (Exception e){
            return new ResponseResult(201,"用户名重复",null);
        }
    }

// 修改用户接口结束


    //    删除用户接口
    @DeleteMapping("/user/{id}")
//    @PreAuthorize("hasRole('system:user:list')")
    public ResponseResult deleteUserById(@PathVariable("id") Integer id) {
        userService.deleteUserById(id);
        return new ResponseResult(200, "success", null);
    }


//    获取用户验证码
    @GetMapping("/user/identifyCode")
    public ResponseResult findPassword1(@RequestParam(value = "username") @NotBlank(message = "获取验证码时用户名不能为空") String userName,
                                       @RequestParam(value = "phonenumber") @NotBlank(message = "获取验证码时电话号码不能为空") String phonenumber,
                                       @RequestParam(value = "email") @NotBlank(message = "获取验证码时邮箱不能空") String email){
    // 在获取password时加上了自定义校验，判断信息是否匹配
    String password = userService.getUserPassword(userName , phonenumber , email ) ;
    Map<String,Object> data = new HashMap<>();
    if (password != null) {
        // 调用工具发送验证码
        String identifyCode = userService.getIdentifyCode(email,userName) ;
        System.out.println("LoginController中的验证码为："+identifyCode);
        // 返回信息
        data.put("msg", "已发送验证码到您的邮箱");
        return new ResponseResult(200, "success", data);
    }
    return new ResponseResult(201,"用户信息不匹配" , null ) ;
}

//    验证用户验证码返回密码接口
    @GetMapping("/user/findpassword")
    public ResponseResult findPassword2(@RequestParam(value = "username") String userName,
                                       @RequestParam(value = "phonenumber") String phonenumber,
                                       @RequestParam(value = "email") String email,
                                       @RequestParam(value = "identifyCode", required = false ) String identifyCode) throws Exception {
        if (identifyCode != null) {
            String password = userService.getUserPassword2(userName, phonenumber, email, identifyCode);
            String password1 = userService.getUserPassword3(userName, phonenumber, email, identifyCode);
            if (password != null){
            Map<String, Object> data = new HashMap<>();
            data.put("password", password1);
            return new ResponseResult<>(200, "Success", data);
            }else {
                Map<String, Object> data = new HashMap<>();
                data.put("password", "验证码错误");
                return new ResponseResult(200,"failed",data);
            }
        }else {
            Map<String,Object> data = new HashMap<>();
            data.put("password","请输入验证码");
            return new ResponseResult<>(200 , "faild",data);
        }
    }

//    用户首页返回用户信息接口
    @GetMapping("/userindex")
    public ResponseResult getUserInfo(@RequestParam(value = "token") String token ){
        User user = userService.getUserInfoIndex(token) ;

        return new ResponseResult<>(200,"success",user) ;
    }
}
