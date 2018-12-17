package com.lesports.qmt.log;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by zhangdeqiang on 2016/12/9.
 */
public class LoggerHandler {
    private static final Logger monitor = LoggerFactory.getLogger("monitor");
    private static final Logger LOG = LoggerFactory.getLogger(LoggerHandler.class);
    public static final FastDateFormat FORMAT_YYYYMMDDHHMMSS = FastDateFormat.getInstance("yyyyMMddHHmmss");
    private static final String INDEX_TYPE = "json_log";

    public static void out(String message) {
        monitor.info(message);
    }

    public static void out(LogContent content) {
        try {
            content.setCreateAt(FORMAT_YYYYMMDDHHMMSS.format(new Date()));
            content.setType(content.getIdType() != null ? content.getIdType().name() : INDEX_TYPE);
            monitor.info(JSON.toJSONString(content));
        } catch (Exception e) {
            LOG.error("业务日志监控，json序列化错误:{}", e.getMessage(), e);
        }
    }

    public static void sendEMail(LogContent content) {
        try {
            content.setLogType(1);
            out(content);
        } catch (Exception e) {
            LOG.error("业务日志监控，json序列化错误:{}", e.getMessage(), e);
        }
    }

}
