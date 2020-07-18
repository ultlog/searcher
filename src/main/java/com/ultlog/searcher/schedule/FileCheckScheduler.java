package com.ultlog.searcher.schedule;

import com.ultlog.searcher.log.LogSender;
import com.ultlog.searcher.model.FileInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @program: collector
 * @link: github.com/ultlog/searcher
 * @author: will
 * @create: 2020-07-19
 **/
@Component
public class FileCheckScheduler {

    @Value("${ultlog.searcher.path}")
    private String path;

    @Value("${ultlog.searcher.pattern}")
    private String filePattern;

    @Value("${ultlog.searcher.max}")
    private int maxFileNumber;

    public static final Logger LOGGER = LoggerFactory.getLogger(FileCheckScheduler.class);

    private CopyOnWriteArrayList<FileInfo> fileInfos = new CopyOnWriteArrayList<>();

    private Boolean firstCheckFile = Boolean.TRUE;

    @Autowired
    private LogSender logSender;

    @Scheduled(cron = "*/10 * * * * ?")
    public void checkFile() {

        File file = new File(path);

        final File[] files = file.listFiles((dir, name) -> name.contains(filePattern));

        if (files == null || files.length == 0) {
            final String s = "no log file";
            System.out.println(s);
            this.firstCheckFile = false;
            return;
        }

        if (CollectionUtils.isEmpty(fileInfos)) {
            Arrays.stream(files).sorted((o1, o2) -> (int) (o2.lastModified() - o1.lastModified())).forEach(file1 -> {
                final FileInfo fileInfo = new FileInfo();
                fileInfo.setFile(file1);
                fileInfo.setLastReadTime(0L);
                // file be created before searcher start(),read new data
                if (firstCheckFile) {
                    fileInfo.setFileSize(file1.length());
                } else {
                    fileInfo.setFileSize(0L);
                }
                fileInfos.add(fileInfo);
            });
        } else {
            Arrays.stream(files).filter(file1 -> {

                for (FileInfo fileInfo : fileInfos) {
                    if (StringUtils.equals(fileInfo.getFile().getName(), file1.getName())) {
                        return false;
                    }
                }
                return true;
            }).map(file1 -> {
                final FileInfo fileInfo = new FileInfo();
                fileInfo.setFile(file1);
                fileInfo.setLastReadTime(0L);
                // file be created after searcher start,read all data
                fileInfo.setFileSize(0L);
                return fileInfo;
            }).forEach(fileInfo -> fileInfos.add(fileInfo));

        }
        this.firstCheckFile = false;
        // remove file when fileInfos's size greater than maxFileNumber
        if (fileInfos.size() > maxFileNumber) {
            fileInfos.sort((o1, o2) -> (int) (o2.getFile().lastModified() - o1.getFile().lastModified()));
            final int size = fileInfos.size();
            for (int i = 0; i < size - maxFileNumber; i++) {
                fileInfos.remove(fileInfos.size() - 1);
            }
        }
    }

    @Scheduled(fixedRateString="5000")
    public void readStringArrFromFile() {
        fileInfos.parallelStream().forEach(fileInfo -> {
            // read file from fileInfos
            try (final RandomAccessFile randomAccessFile = new RandomAccessFile(fileInfo.getFile(), "r")) {

                // if file no change, return
                if(fileInfo.getFileSize() >= randomAccessFile.length()){
                    return;
                }

                // get change part
                randomAccessFile.seek(fileInfo.getFileSize());
                String tmp = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((tmp = randomAccessFile.readLine()) != null) {
                    // just get today or yesterday log
                    if (stringBuilder.length() > 0 && StringUtils.isNoneBlank(tmp) && (tmp.startsWith(LocalDate.now().toString()) || tmp.startsWith(LocalDate.now().minusDays(1L).toString()))) {
                        // async send log to ula
                        logSender.sendLog(stringBuilder.toString());
                        stringBuilder.delete(0, stringBuilder.length());
                    }
                    // iso to utf8
                    byte[] originalBytes = tmp.getBytes(StandardCharsets.ISO_8859_1);
                    stringBuilder.append(new String(originalBytes)).append("\n");
                }
                // when stream will be close,check is there string is not be send
                if(stringBuilder.length() > 0){
                    logSender.sendLog(stringBuilder.toString());
                }
                fileInfo.setFileSize(randomAccessFile.length());

            }catch (FileNotFoundException e){
                fileInfos.remove(fileInfo);
            }catch (IOException e) {
                LOGGER.error(e.getMessage(),e);
            }
        });
    }
}
