package com.lesports.qmt.transcode.web.support;

import com.lesports.utils.math.LeNumberUtils;

/**
 * Created by pangchuanxiao on 2016/11/11.
 */
public class TranscodeUtils {
    /**
     * 将前端提交的时间转为秒
     * @param time
     * @return
     */
    public static int formatToSeconds(String time) {
        if (null == time)
            return -1;
        if (!time.matches("[0-9][0-9]:[0-9][0-9]:[0-9][0-9]"))
            return -1;
        String[] times = time.split(":");
        return LeNumberUtils.toInt(times[0]) * 3600 + LeNumberUtils.toInt(times[1]) * 60 + LeNumberUtils.toInt(times[2]);
    }
}
