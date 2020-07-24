package com.ultlog.searcher.model;

import java.io.File;

/**
 * @program: collector
 * @link: github.com/ultlog/searcher
 * @author: will
 * @create: 2020-07-13
 **/
public class FileInfo {

    private long fileSize;

    private File file;

    private long lastReadTime;

    public long getFileSize() {
        return fileSize;
    }

    public FileInfo setFileSize(long fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public File getFile() {
        return file;
    }

    public FileInfo setFile(File file) {
        this.file = file;
        return this;
    }

    public long getLastReadTime() {
        return lastReadTime;
    }

    public FileInfo setLastReadTime(long lastReadTime) {
        this.lastReadTime = lastReadTime;
        return this;
    }
}
