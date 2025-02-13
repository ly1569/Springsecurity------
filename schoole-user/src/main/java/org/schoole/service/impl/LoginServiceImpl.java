package org.schoole.service.impl;

import org.schoole.domain.LoginUser;
import org.schoole.domain.ResponseResult;
import org.schoole.domain.User;
import org.schoole.service.LoginServcie;
import org.schoole.utils.JwtUtil;
import org.schoole.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginServcie {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
//        AuthenticationManager authenticate进行用户认证
        System.out.println("进入LoginServiceImpl.login函数进行认证");
//      创建一个 带有用户名和用户密码的 authenticationToken 对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        System.out.println("成功创建 authenticationToken 为："+ authenticationToken);
//      通过 authenticationManager 来验证 authenticationToken 对象 是否合法
//      authenticationManager函数的认证过程是调用系统中的 UserDetailService接口 的实现类来和数据库中存储的内容进行对比
//      12.16更新：调用接口authenticationManager中的authenticate方法，authenticationManager该接口中的实现类为 providerManager
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        System.out.println("判断 authenticate 是否成功, Authentication信息为："+authenticate);

        //如果认证没通过，给出对应的提示
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }
        //如果认证通过了，使用userid生成一个jwt jwt存入ResponseResult返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userid = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userid);
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);

        //把完整的用户信息存入redis  userid作为key 可以设置在redis中的存放时间setCacheObject("login:"+userid,loginUser，30，Timeout.Unite.Minutes)
        redisCache.setCacheObject("login:"+userid,loginUser);

        System.out.println("用户的完整信息为：" + loginUser );

        return new ResponseResult(200,"登录成功",map);
    }
    @Override
    public ResponseResult logout() {
        //获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();

        //删除redis中的值
        redisCache.deleteObject("login:"+userid);
        return new ResponseResult(200,"注销成功");
    }
}
