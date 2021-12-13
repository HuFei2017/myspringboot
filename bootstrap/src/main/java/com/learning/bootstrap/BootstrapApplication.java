package com.learning.bootstrap;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PreDestroy;

@SpringBootApplication
@EnableScheduling
public class BootstrapApplication {

    public static void main(String[] args) {
//        SpringApplication.run(BootstrapApplication.class, args);
        SpringApplication app = new SpringApplication(BootstrapApplication.class);
        app.setBannerMode(Banner.Mode.CONSOLE);
        app.addListeners((ApplicationListener<ApplicationStartingEvent>) event -> {
            System.out.println("事件监听。。。。。。");
        });
        app.run(args);
    }

    @PreDestroy
    public void destroy(){
        System.out.println("进程退出了，自定义的退出方法");
    }
}
