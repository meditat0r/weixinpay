package com.kuangstudy.weixinpay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.kuangstudy.weixinpay.mapper")
public class WeinxinpayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeinxinpayApplication.class, args);
    }

}
