package org.schoole;

import org.mybatis.spring.annotation.MapperScan;
//import org.schoole.conf.NacosDiscoveryProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient //开启服务注册发现功能
@EnableFeignClients //启动 Feign 客户端
//@EnableConfigurationProperties(NacosDiscoveryProperties.class)
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class user {

    public static void main(String[] args) {
        SpringApplication.run(user.class,args);
    }
}
