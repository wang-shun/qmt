package com.lesports.qmt.log.cat;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.AppenderBase;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Trace;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by zhangdeqiang on 2017/1/10.
 */
public class LesportsCatAppender extends AppenderBase<LoggingEvent> {
    private static final String TYPE = "Logback";

    @Override
    protected void append(LoggingEvent eventObject) {
        boolean isTraceMode = Cat.getManager().isTraceMode();
        Level level = eventObject.getLevel();
        //Cat.getCurrentMessageId();// todo 输出调用链
        if (level.isGreaterOrEqual(Level.ERROR)) {
            logError(eventObject);
        } else if (isTraceMode) {
            logTrace(eventObject);
        }
    }

    private void logError(LoggingEvent event) {
        ThrowableProxy info = (ThrowableProxy) event.getThrowableProxy();

        if (info != null) {
            String message = event.getMessage();

            if (message != null) {
                Cat.logError(event.getMessage(), info.getThrowable());
            } else {
                Cat.logError(info.getThrowable());
            }
        }
    }

    private void logTrace(LoggingEvent event) {
        String name = event.getLevel().toString();
        String data = event.getFormattedMessage();

        ThrowableProxy info = (ThrowableProxy) event.getThrowableProxy();

        if (info != null) {
            data = data + '\n' + buildExceptionStack(info.getThrowable());
        }
        Cat.logTrace(TYPE, name, Trace.SUCCESS, data);
    }

    private String buildExceptionStack(Throwable exception) {
        if (exception != null) {
            StringWriter writer = new StringWriter(2048);

            exception.printStackTrace(new PrintWriter(writer));
            return writer.toString();
        } else {
            return "";
        }
    }
}
