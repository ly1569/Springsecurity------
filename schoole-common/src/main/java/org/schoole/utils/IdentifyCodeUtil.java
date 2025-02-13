package org.schoole.utils;

import java.util.Random;

public class IdentifyCodeUtil {
    public String code(){
        //1，用随机生成数方法，生成验证码
        //定义一个随机生成数技术，用来生成随机数
        Random yzm = new Random();
        //定义一个空的Atring变量用来接收生成的验证码
        String yzm2 = "";
        //循环5次每次生成一位，5位验证码
        for (int i = 0; i < 5; i++) {
            //验证码包括数字、大小写字母组成
            int a = yzm.nextInt(3);
            //a:    0       1       2
            switch(a){
                //      数字   小写字母   大写字母
                case 0:
                    char s=(char)(yzm.nextInt(26)+65);
                    yzm2 = yzm2 + s;
                    break;
                case 1:
                    char s1=(char)(yzm.nextInt(26)+97);
                    yzm2 = yzm2 + s1;
                    break;
                case 2:
                    int s2=yzm.nextInt(10);
                    yzm2 = yzm2 + s2;
                    break;
            }
        }
//        System.out.println("用随机生成数方法，生成的验证码:"+yzm2);
    return  yzm2 ;
    }
}
