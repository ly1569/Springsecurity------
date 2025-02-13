package org.schoole.handler;

import com.alibaba.fastjson.JSON;
import org.schoole.domain.ResponseResult;
import org.schoole.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 首先 Component 将该组件放在 Spring 容器中交给Spring管理，然后在认证失败时调用该实现类中的commence函数，返回数据
        ResponseResult result = new ResponseResult(HttpStatus.UNAUTHORIZED.value(),"用户认证失败请查询登录");
        String json = JSON.toJSONString(result);
        //处理异常
        WebUtils.renderString(response,json);
    }
}
