package com.ultlog.searcher.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultlog.common.model.Log;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.ultlog.common.constant.API.POST_LOG;

/**
 * @program: collector
 * @link: github.com/ultlog/searcher
 * @author: will
 * @create: 2020-07-14
 **/
@Component
public class LogSender {

    @Value("${ultlog.searcher.project}")
    private String project;
    @Value("${ultlog.searcher.module}")
    private String module;
    @Value("${ultlog.searcher.uuid}")
    private String uuid;
    @Value("${ultlog.ula.url}")
    private String ulaUrl;

    @Autowired
    private LogTransformer logTransformer;

    public static final Logger LOGGER = LoggerFactory.getLogger(LogSender.class);

    OkHttpClient client = new OkHttpClient();

    private final ObjectMapper mapper = new ObjectMapper();


    @Async
    public void SendLog(String logString) {

        final Log log = logTransformer.readLogFromString(logString);
        log.setProject(project);
        log.setUuid(uuid);
        log.setModule(module);
        // send log
        //sendLogToUla(log);

    }

    private void sendLogToUla(Log log) {


        // get json string
        final String json;
        try {
            json = mapper.writeValueAsString(log);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            return;
        }

        // create request with json
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request request = new Request.Builder().url(ulaUrl + POST_LOG).post(body).build();

        // post data to ula
        try (Response execute = client.newCall(request).execute()) {
            if (!execute.isSuccessful()) {
                LOGGER.error("Failed to send, log is " + json);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
