package com.lesports.qmt.web.api.core.util;

import org.apache.commons.lang3.StringUtils;


/**
 * Created by gengchengliang on 2016/5/5.
 */
public class CheckSplatidUtils {

    //体育其他终端 platId
    private static final String VOD_PLATID = "16";
    //体育tv platId
    private static final String TV_VOD_PLATID = "27";
    //体育超级手机 platId
    private static final String SUPER_MOBILE_VOD_PLATID = "15";

    private static final String LIVE_PLATID = "10";

    public static Boolean checkVod(String splatid, String platid) {
        if (StringUtils.isBlank(splatid) || StringUtils.isBlank(platid)) {
            return false;
        }
        if ((platid.equals(VOD_PLATID) && splatid.startsWith(VOD_PLATID)) ||
                (platid.equals(TV_VOD_PLATID) && splatid.startsWith(TV_VOD_PLATID)) ||
                (platid.equals(SUPER_MOBILE_VOD_PLATID) && splatid.startsWith(SUPER_MOBILE_VOD_PLATID))) {
            return true;
        }
        return false;
    }

    public static Boolean checkLive(String splatid, String platid) {
        if (StringUtils.isBlank(splatid) || StringUtils.isBlank(platid)) {
            return false;
        }
        if (platid.equals(LIVE_PLATID) && splatid.startsWith(LIVE_PLATID)) {
            return true;
        }
        return false;
    }
}
