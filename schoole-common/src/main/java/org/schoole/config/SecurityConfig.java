package org.schoole.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.schoole.filter.JwtAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    Logger logger = LogManager.getLogger() ;
    //创建BCryptPasswordEncoder注入容器
    // 将该 BCryptPasswordEncoder配置类注入容器，在数据库中，必须使用该配置类转化后的 加密密码进行存储
    @Bean
    public PasswordEncoder passwordEncoder(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        System.out.println(passwordEncoder.encode("password2"));
        logger.info("password2的BCryptPasswordEncoder加密密码为：" + passwordEncoder.encode("password2"));

        return passwordEncoder;
    }
    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;


    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private AccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/user/login").anonymous()
                .antMatchers("/user/login/register").anonymous()
                .antMatchers("/user/identifyCode").anonymous()
                .antMatchers("/user/findpassword").anonymous()
                .antMatchers("/all/type").anonymous()
                .antMatchers("/model/info").anonymous()
                .antMatchers("/fault/info1").anonymous()
                .antMatchers("/user/user/login").anonymous()
                .antMatchers("/UserDetailsServiceImpl/menuMapper.selectPermsByUserId").anonymous()
                .antMatchers("/UserServiceImpl/getMenuListByid").anonymous()
//                .antMatchers("/user/info").anonymous() // 第二次前端使用 /user/info 接口
//                .antMatchers("/testCors").hasAuthority("system:dept:list222")
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        //添加过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //配置异常处理器
        http.exceptionHandling()
                //配置认证失败处理器
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);

        //允许跨域
        http.cors();
    }

    @Bean
    @Lazy
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
