package com.xiaoxu.xuojbackendjudgeservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@SpringBootApplication
@ComponentScan("com.xiaoxu")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.xiaoxu.xuojbackendserviceclient.service"})
public class XuojBackendJudgeServiceApplication {


    public static void main(String[] args) {
        //初始化消息队列
        InitRabbitMq.doInit();
        SpringApplication.run(XuojBackendJudgeServiceApplication.class, args);
    }

}
