package com.ultlog.searcher.log;


import com.ultlog.common.model.Log;
import io.krakens.grok.api.Grok;
import io.krakens.grok.api.GrokCompiler;
import io.krakens.grok.api.Match;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * @program: collector
 * @link: github.com/ultlog/searcher
 * @author: will
 * @create: 2020-07-12
 **/
@Component
public class LogTransformer {

    final private Grok grok;

    public static final Logger LOGGER = LoggerFactory.getLogger(LogTransformer.class);

    public LogTransformer() {

        GrokCompiler grokCompiler = GrokCompiler.newInstance();
        String dateTime = "(?>\\d\\d){1,2}-(?:0?[1-9]|1[0-2])-(?:(?:0[1-9])|(?:[12][0-9])|(?:3[01])|[1-9])[ ](?:2[0123]|[01]?[0-9]):?(?:[0-5][0-9])(?::?(?:(?:[0-5]?[0-9]|60)(?:[:.,][0-9]+)?))?";

        String thread = ".*?";
        String level = "([A|a]lert|ALERT|[T|t]race|TRACE|[D|d]ebug|DEBUG|[N|n]otice|NOTICE|[I|i]nfo|INFO|INFO |[W|w]arn?(?:ing)?|WARN?(?:ING)?|WARN |[E|e]rr?(?:or)?|ERR?(?:OR)?|[C|c]rit?(?:ical)?|CRIT?(?:ICAL)?|[F|f]atal|FATAL|[S|s]evere|SEVERE|EMERG(?:ENCY)?|[Ee]merg(?:ency)?)";
        String logger = ".*?";
        String msg = ".*";
        String stack = "[\\s\\S]*";

        grokCompiler.register("dateTime", dateTime);
        grokCompiler.register("thread", thread);
        grokCompiler.register("logger", logger);
        grokCompiler.register("level", level);
        grokCompiler.register("msg", msg);
        grokCompiler.register("stack", stack);

        grok = grokCompiler.compile("%{dateTime} \\[%{level}] \\[%{thread}] %{logger} - %{msg}%{stack}");
    }

    public Log readLogFromString(String[] logString) {


        final Match match = grok.match(logString[0]);
        final Map<String, Object> capture = match.capture();

        Log log = new Log();
        log.setLevel(String.valueOf(capture.get("level")));
        // date to long
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            final long dateTime = sdf.parse(String.valueOf(capture.get("dateTime"))).getTime();
            log.setCreateTime(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        log.setMessage(String.valueOf(capture.get("msg")));

        log.setLevel(String.valueOf(capture.get("value")).toUpperCase());

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 1; i < logString.length; i++) {
            stringBuilder.append(logString[i]).append("\n");
        }
        if (stringBuilder.length() > 0) {
            log.setStack(stringBuilder.toString());
        }
        return log;
    }

    public Log readLogFromString(String logString){
        final Match match = grok.match(logString);

        final Map<String, Object> capture = match.capture();

        final String msg = String.valueOf(capture.get("msg"));
        final String level = String.valueOf(capture.get("level"));
        final String stack = String.valueOf(capture.get("stack"));
        final String dateTimeString = String.valueOf(capture.get("dateTime"));
        if(StringUtils.isAnyBlank(msg,level,dateTimeString)){
            return null;
        }

        Log log = new Log();
        log.setLevel(level.trim());
        log.setStack(String.valueOf(stack));
        if(StringUtils.isBlank(log.getStack())){
            log.setStack(null);
        }else{
            // replace \n to,adapt ula
            log.setStack(stack.replace("\r\n",";"));
            log.setStack(stack.replace("\n\r",";"));
            log.setStack(stack.replace("\n\n",";"));
            log.setStack(stack.replace("\n",";"));
        }

        // date to long
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            final long dateTime = sdf.parse(dateTimeString).getTime();
            log.setCreateTime(dateTime);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(),e);
        }
        log.setMessage(String.valueOf(capture.get("msg")));

        return log;
    }


}
