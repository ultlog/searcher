package com.ultlog.searcher.log;

import com.ultlog.common.model.Log;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LogTransformerTest {

    @Autowired
    private LogTransformer logTransformer;

    String logString = "2020-07-21 09:16:37 [ERROR] [pool-420-thread-7] c.t.g.h.HttpConnectionPoolUtil - test error \n" +
            "java.lang.RuntimeException: tea das asdsawda asdas a\n" +
            "at com.example.demo.DemoApplicationTests.contextLoads(DemoApplicationTests.java:21)\n" +
            "at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n" +
            "at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n" +
            "at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n" +
            "at java.lang.reflect.Method.invoke(Method.java:498)\n" +
            "at org.junit.platform.commons.util.ReflectionUtils.invokeMethod(ReflectionUtils.java:675)\n";


    String pattern = " <pattern>%d{yyyy-MM-dd HH:mm:ss} [%-5level] [%-5thread] %logger{20} - %msg%n</pattern>";


    @Test
    public void testReadLogFromString() {
        String d = "2020-07-21 09:16:37 [ERROR] [pool-420-thread-7] c.t.g.h.HttpConnectionPoolUtil - test error \n" +
                "java.lang.RuntimeException: tea das asdsawda asdas a\n" +
                "at com.example.demo.DemoApplicationTests.contextLoads(DemoApplicationTests.java:21)\n";
        final Log log = logTransformer.readLogFromString(d);
        Assert.assertEquals(log.getLevel(), "ERROR");
    }

}