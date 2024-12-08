package com.xiaoxu.xuojbackenduserservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;


@MapperScan("com.xiaoxu.xuojbackenduserservice.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@SpringBootApplication
@ComponentScan("com.xiaoxu")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.xiaoxu.xuojbackendserviceclient.service"})
public class XuojBackendUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(com.xiaoxu.xuojbackenduserservice.XuojBackendUserServiceApplication.class, args);
    }

}
