package com.ultlog.searcher.log;


import com.ultlog.common.model.Log;
import io.krakens.grok.api.Grok;
import io.krakens.grok.api.GrokCompiler;
import io.krakens.grok.api.Match;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: collector
 * @link: github.com/ultlog/searcher
 * @author: will
 * @create: 2020-07-12
 **/
@Component
public class LogReader {

    final Grok grok;

    public LogReader(@Value("${ultlog.grok.pattern}") String grokPattern) {

        GrokCompiler grokCompiler = GrokCompiler.newInstance();
        String dateTime = "(?>\\d\\d){1,2}-(?:0?[1-9]|1[0-2])-(?:(?:0[1-9])|(?:[12][0-9])|(?:3[01])|[1-9])[ ](?:2[0123]|[01]?[0-9]):?(?:[0-5][0-9])(?::?(?:(?:[0-5]?[0-9]|60)(?:[:.,][0-9]+)?))?";

        String thread = "(.+?)";
        String level = "([A|a]lert|ALERT|[T|t]race|TRACE|[D|d]ebug|DEBUG|[N|n]otice|NOTICE|[I|i]nfo|INFO|[W|w]arn?(?:ing)?|WARN?(?:ING)?|[E|e]rr?(?:or)?|ERR?(?:OR)?|[C|c]rit?(?:ical)?|CRIT?(?:ICAL)?|[F|f]atal|FATAL|[S|s]evere|SEVERE|EMERG(?:ENCY)?|[Ee]merg(?:ency)?)";
        String logger = "\\S+";
        String msg = ".*";


        grokCompiler.register("dateTime",dateTime);
        grokCompiler.register("thread",thread);
        grokCompiler.register("logger",logger);
        grokCompiler.register("level",level);
        grokCompiler.register("msg",msg);
        //grok = grokCompiler.compile("%{dateTime} %{thread}");
        grok = grokCompiler.compile("%{dateTime} \\[%{thread}] %{level} %{logger} - %{msg}");
    }

    public Log readLogFromString(String logString) {


        final Match match = grok.match(logString);
        final Map<String, Object> capture = match.capture();
        final Object msg = capture.get("msg");
        final int i = logString.indexOf(msg.toString());
        final String substring = logString.substring(i);
        return null;
    }


}
