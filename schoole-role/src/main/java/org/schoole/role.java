package org.schoole;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

@MapperScan("org.schoole.mapper")
@SpringBootApplication
@EnableDiscoveryClient //开启服务注册发现功能
public class role {
    public static void main(String[] args) {
        SpringApplication.run(role.class,args);
    }
}
