package com.ultlog.searcher.schedule;

import com.ultlog.searcher.model.FileInfo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @program: collector
 * @link:
 * @create: 2020-07-09
 **/
@Component
public class FileCheckScheduler {

    @Scheduled(cron = "*/20 * * * * ?")
    public void checkFile(){

        // todo check file add to fileInfos

    }

    private CopyOnWriteArrayList<FileInfo> fileInfos = new CopyOnWriteArrayList<>();


    @Scheduled(cron = "*/5 * * * * ?")
    public void readStringArrFromFile(){
        // read file from fileInfos

    }
}
