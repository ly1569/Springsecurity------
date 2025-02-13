package org.schoole.service;

import org.schoole.domain.Menu;
import org.schoole.domain.User;
import org.schoole.domain.userForRegister;

import java.util.List;
import java.util.Map;

public interface UserService {
    // 查询用户信息接口
    public Map<String , Object> userList(String user_name , String phonenumber , Long pageNo , Long pageSize) ;


    //新增用户接口
    public void addUser(User user) throws Exception;

    // 注册用户接口
    public void registerUser(userForRegister userForRegister) throws Exception;

   // 修改用户接口
    public void updateUserById(User user) ;
    // 通过用户id获取用户信息接口
    public User getUserInfoById(Integer id);
    // 逻辑删除用户
    public void deleteUserById(Integer id);

    // 获取用户所对应的 菜单列表
    List<Menu> getMenuListById(String token);

    // 获取用户密码接口
    String getUserPassword(String userName, String phonenumber, String email);

    // 获取用户验证码
    String getIdentifyCode(String email , String userName);

    // 获取用户密码
    String getUserPassword2(String userName, String phonenumber, String email, String identifyCode);
    // 获取用户可解密的密码
    String getUserPassword3(String userName, String phonenumber, String email, String identifyCode) throws Exception;
    // 首页获取用户信息
    User getUserInfoIndex( String token);
    // 管理员获取所有用户名方法
    Map<String, Object> getAllUserName();
}
