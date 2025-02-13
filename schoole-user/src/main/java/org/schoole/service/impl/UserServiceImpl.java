package org.schoole.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.schoole.domain.*;
import org.schoole.mailBean.MailBean;
import org.schoole.mailBean.MailUtil;
import org.schoole.mapper.UserMapper;
import org.schoole.mapper.UserRoleMapper;
import org.schoole.service.MenuService;
import org.schoole.service.UserService;
import org.schoole.service.selectPermsByUserId;
import org.schoole.utils.AES;
import org.schoole.utils.IdentifyCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    // 注入 UserMapper 实例
//    @Autowired
//    UserMapper userMapper ;
//
//    @Autowired
//    UserRoleMapper userRoleMapper ;
//
//    @Autowired
//    MenuService menuService ;
//
//    @Autowired
//    MailUtil mailUtil ;

    @Resource
    UserMapper userMapper ;

    @Resource
    UserRoleMapper userRoleMapper ;

    @Resource
    MenuService menuService ;

    @Resource
    MailUtil mailUtil ;

    @Resource
    selectPermsByUserId selectPermsByUserId ;

    //查询用户方法
    @Override
    public Map<String, Object> userList(String user_name, String phonenumber, Long pageNo, Long pageSize) {

        //  检查 pageNo 和 pageSize
        System.out.println("UserServiceImpl中："  + "pageNO:"+pageNo + "pageSize:"+pageSize );

        // 条件查询
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>() ;
        wrapper.eq(StringUtils.hasLength(user_name),User::getUserName,user_name);
        wrapper.eq(StringUtils.hasLength(phonenumber),User::getPhonenumber,phonenumber) ;
        wrapper.orderByDesc(User::getId) ;

        //分页
        Page<User> page = new Page<>(pageNo,pageSize) ;

        userMapper.selectPage(page ,  wrapper) ;
        Map<String , Object> data = new HashMap<>() ;


        // 检查 page 中 total 和 records
        System.out.println("total :" + page.getTotal());
        System.out.println("records:" + page.getRecords());

        data.put("total" , page.getTotal());
        data.put("rows" , page.getRecords()) ;

        return data ;
    }

    // 实现新增用户接口,管理员添加用户
    @Override
    @Transactional
    public void addUser(User user) throws Exception {
        // 写入用户表
        Long count = (long) (userMapper.getUserMaxId()+1);
        System.out.println("测试中id为"+count);
        user.setId(count);
        // 密码加密存储
        // 设置可以解密的密码字段
        AES aes = new AES() ;
        user.setPassword1(aes.encrypt(user.getPassword()));
        // 该密码为不可解密的密码,用于springsecurity验证
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder() ;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(user) ;
        System.out.println("插入后的user"+user);
        // 写入用户角色表
//        1.存储前端user中的roleIdList
        if (user.getRoleIdList() != null) {
            List<Integer> roleIdList = user.getRoleIdList();
            for (Integer roleId : roleIdList) {
                // 前面 user 中对应的 userid 是 Long 类型，
                userRoleMapper.insert(new UserRole(user.getId(), roleId));
            }
        }

    }

    // 用UserForRegister 接收数据 到 User 类中 ，在对 user对象进行操作
    // 注册用户
    @Override
    public void registerUser(userForRegister userForRegister) throws Exception {
        User user = new User() ;
        AES aes = new AES() ;
        //将usrForRegister中的数据为User类中响应的数据赋值
        user.setEmail(userForRegister.getEmail1());
        user.setPassword(userForRegister.getPassword1());
        user.setPassword1(aes.encrypt(userForRegister.getPassword1()));
        user.setPhonenumber(userForRegister.getPhone1());
        //用户注册时默认状态为开
        user.setStatus(String.valueOf(1));
//        user.setStatus(String.valueOf(userForRegister.getStatus1()));
//        System.out.println("Status"+user.getStatus());
        user.setUserName(userForRegister.getUsername1());
        Long count = (long) (userMapper.getUserMaxId()+1);
        System.out.println("count:"+count);
        user.setId(count);
        System.out.println("设置完id后的user"+user);
        // 密码加密存储
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder() ;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(user) ;

    }
    // 修改用户接口实现
    @Override
    @Transactional
    public void updateUserById(User user) {

        // 更新用户表
        System.out.println("userMapper中user"+user);
        userMapper.updateById(user);

        // 清除所有角色
//        设置 wrapper 条件
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,user.getId());
        userRoleMapper.delete(wrapper);

        //设置新角色
        if (user.getRoleIdList() != null) {
            List<Integer> roleIdList = user.getRoleIdList();
            for (Integer roleId : roleIdList) {
                // 前面 user 中对应的 userid 是 Long 类型，
                userRoleMapper.insert(new UserRole(user.getId(), roleId));

            }
        }


    }

//    通过用户 id 获取相应的 用户信息接口
    @Override
    public User getUserInfoById(Integer id) {
//        System.out.println("usermapper查询用户信息实现"+userMapper.selectById(id));
          // 获取用户对象
            User user = new User() ;
            user = userMapper.selectById(id) ;
          // 创造相应的 wrapper 条件 然后 进行查询
            LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserRole::getUserId,id) ;
            List<UserRole> userRoleList = userRoleMapper.selectList(wrapper);
            List<Integer> roleIdList = userRoleList.stream()
                                                            .map(userRole -> {return userRole.getRoleId();})
                                                            .collect(Collectors.toList()) ;


            user.setRoleIdList(roleIdList);
            return user;
    }
//  修改用户接口实现结束


//    删除用户接口
    @Override
    public void deleteUserById(Integer id) {
        // 清除用户接口
        userMapper.deleteById(id) ;
        // 清除用户角色接口
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,id);
        userRoleMapper.delete(wrapper);


    }

    @Override
    public List<Menu> getMenuListById(String token) {
        //获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Integer userid = Math.toIntExact(loginUser.getUser().getId());
        // 成功获取 userid 的值
        System.out.println("UserServiceImpl中的 userid 值为："+userid );
//    ======================
        // 权限列表
        List<Menu> menuList = menuService.getMenuListByUserId(userid) ;
//    =======================
        return menuList;
    }

    // 查询用户密码
    @Override
    public String getUserPassword(String userName, String phonenumber, String email) {
        System.out.println("getUserPassword中的数据为： userName:"+userName + "  phonenumber:"+phonenumber +"  email"+email);
        String password = userMapper.getUserPassword(userName ,email , phonenumber);
        return password;
    }

    // 发送邮件

    // 发送邮件获取验证码
    @Override
    public String getIdentifyCode(String email , String userName) {
        MailBean mailBean = new MailBean() ;
        IdentifyCodeUtil identifyCodeUtil = new IdentifyCodeUtil() ;
        // 邮件接收者
        mailBean.setRecipient(email);
        // 邮件标题
        mailBean.setSubject("你正在使用学校快修平台找回密码");
        // 邮件内容
        String identifyCode = identifyCodeUtil.code() ;
        mailBean.setContent("验证码为："+identifyCode);
        mailUtil.sendSimpleMail(mailBean);
        // 将该验证码写入数据库
        userMapper.setUserIdentifyCode(identifyCode , userName);
        return identifyCode;
    }
    // 传入用户信息+验证码，再次进行判断，返回密码
    @Override
    public String getUserPassword2(String userName, String phonenumber, String email, String identifyCode) {
        String password = userMapper.getUserPassword2(userName , phonenumber , email,identifyCode) ;
        return password;
    }

    // 获取用户可解密的密码
    @Override
    public String getUserPassword3(String userName, String phonenumber, String email, String identifyCode) throws Exception {
        String password = userMapper.getUserPassword3(userName , phonenumber , email,identifyCode) ;
        AES aes = new AES() ;
        return aes.decrypt(password);
//        System.out.println(aes.encrypt(""));
    }
//  发送邮件结束


    // 用户首页获取用户信息
    @Override
    public User getUserInfoIndex(String token) {
        // 从上下文中获取用户名
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        // 通过用户名获取用户信息
        User user = userMapper.getUserInfoIndex(userName);
        return user;
    }

    // 管理员获取所有用户名方法
    @Override
    public Map<String, Object> getAllUserName() {
        List<String> userNames = userMapper.getAllUserName();
        Map<String,Object> data = new HashMap<>();
        data.put("userNames",userNames);
        return data;
    }


}
