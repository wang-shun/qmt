package com.lesports.qmt.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhangdeqiang on 2016/7/21.
 */
public class TimeOutComputer {
    private static final Logger LOG = LoggerFactory.getLogger(TimeOutComputer.class);
    private static double printTimeout = 1;

    public void setPrintTimeout(double printTimeout) {
        TimeOutComputer.printTimeout = printTimeout;
    }

    public static long getStartTime() {
        return System.currentTimeMillis();
    }

    public static void printTimeOut(String methodName, long start) {
        long end = System.currentTimeMillis();
        double spend = (double) (end - start);
        if (spend > printTimeout) {
            LOG.error(methodName + " time spend:" + spend / 1000 + "秒");
        } else {
            LOG.warn(methodName + " time spend:" + spend / 1000 + "秒");
        }
    }
}
