package org.schoole;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableDiscoveryClient //开启服务注册发现功能
@ComponentScan(basePackages = "org.schoole.mapper")
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class common {

    public static void main(String[] args) {
        SpringApplication.run(common.class,args);
    }
}
