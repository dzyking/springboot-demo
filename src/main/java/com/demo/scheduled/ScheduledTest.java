package com.demo.scheduled;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author dzy
 * @date 2021/5/7
 * @desc 定时任务测试类
 */
@Slf4j
@Component
public class ScheduledTest {

    //每分钟执行一次
    @Scheduled(cron = "0 0/1 * * * ? ")
    //每次锁定一分钟(多服务部署)
    @SchedulerLock(name = "test", lockAtMostFor = 60000, lockAtLeastFor = 60000)
    public void test() {
        log.info("now time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
