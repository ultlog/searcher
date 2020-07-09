package com.ultlog.searcher.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @program: collector
 * @link:
 * @create: 2020-07-09
 **/
@Component
public class FileCheckScheduler {

    @Scheduled(cron = "*/20 * * * * ?")
    public void checkFile(){
        System.out.println(LocalDateTime.now());

    }
}
