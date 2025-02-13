package org.schoole.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.schoole.domain.LoginUser;
import org.schoole.domain.User;
import org.schoole.mapper.MenuMapper;
import org.schoole.mapper.UserMapper;
import org.schoole.service.selectPermsByUserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private MenuMapper menuMapper;

    @Resource
    selectPermsByUserId  selectPermsByUserId ;




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(queryWrapper);
        System.out.println("UserDetailServiceImpl中的获取到的User对象内容为：" + user);
        //如果没有查询到用户就抛出异常
        if(Objects.isNull(user)){
            System.out.println("UserDetailsServiceImpl.loadUserByUsername用户名或者密码错误");
            throw new RuntimeException("用户名或者密码错误");
        }

//        测试密码是否一致


//        List<String> list = new ArrayList<>(Arrays.asList("test","admin"));
        // 获取用户名
//        String name = SecurityContextHolder.getContext().getAuthentication().getName() ;
//        // 通过用户名获取用户id
//        Integer id = userMapper.getUserIdsByUserName(name) ;
//        System.out.println("获取到的id为：" + id);
        List<String> list = selectPermsByUserId.PermsList(Math.toIntExact(user.getId()));

//        List<String> list = menuMapper.selectPermsByUserId(user.getId());
        System.out.println("menMapper中的list" + list);
        //把数据封装成UserDetails返回
        return new LoginUser(user,list);
    }
}
