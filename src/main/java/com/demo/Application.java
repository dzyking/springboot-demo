package com.demo;

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
@EnableScheduling
@SpringBootApplication
@MapperScan(basePackages = "com.demo.mapper")
@ComponentScan(basePackages = {"com"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }
}
