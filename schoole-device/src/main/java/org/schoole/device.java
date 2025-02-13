package org.schoole;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
@SpringBootApplication
@EnableDiscoveryClient //开启服务注册发现功能
public class device {
//    public static void main(String[] args) {
//        // Press Ctrl+1 with your caret at the highlighted text to see how
//        // IntelliJ IDEA suggests fixing it.
//        System.out.printf("Hello and welcome!");
//
//        // Press Alt+Shift+X or click the green arrow button in the gutter to run the code.
//        for (int i = 1; i <= 5; i++) {
//
//            // Press Alt+Shift+D to start debugging your code. We have set one breakpoint
//            // for you, but you can always add more by pressing Ctrl+Shift+B.
//            System.out.println("i = " + i);
//        }
//    }
public static void main(String[] args) {
    SpringApplication.run(device.class,args);
}
}
