package com.lesports.qmt.web.api.core.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.lesports.api.common.CountryCode;
import com.lesports.utils.LeProperties;

import java.util.List;
import java.util.Map;

/**
 * Created by lufei1 on 2015/7/18.
 */
public class PlayerConstants {

    //点播码流：极速、流畅、标清、高清
    public static final Map<String, String> vodVTypeMap = Maps.newHashMap();

    //drm点播码流：极速、流畅、标清、高清
    public static final Map<String, String> drmVodVTypeMap = Maps.newHashMap();

    //drm点播码流：极速、流畅、标清、高清
    public static final Map<String, String> cleanDrmVTypeMap = Maps.newHashMap();

    //点播码流
    public static final String vodVType = "58,21,13,22,51,52,53";

    //drm点播码流
    public static final String drmVodVType = "188,189,190,191,192,193,194";

    //点播付费高码流常量52,53
    public static final String VOD_HIGH_VTYPE = "52,53";

    //直播付费高码流常量flv_1080p3m
    public static final String LIVE_HIGH_VTYPE = "flv_1080p3m";

    //直播码流：流畅、标清、高清、超清
    public static final List<String> liveVTypeList = ImmutableList.of("flv_350", "flv_1000", "flv_1300", "flv_720p", "flv_1080p3m");

    //360度视频直播splatId
    public static String SPLATID_360_LIVE = "1050716002";

    //360度视频点播splatId
    public static String SPLATID_360_VOD = "1603";

    public static final String RATE_TYPE_1080P = "flv_1080p";
    public static final String RATE_TYPE_3000_1080P = "flv_1080p3m";
    public static final String RATE_TYPE_5000_1080P = "flv_1080p6m";
    public static final String RATE_TYPE_1080P_PREFIX = "flv_1080";
    //兼容app的1080p码流格式名称,由于wiki错误，只能将错就错了
    public static final String APP_COMPATIBLE_RATE_TYPE_3000_1080P = "flv_1080p3";
    public static final String APP_COMPATIBLE_RATE_TYPE_5000_1080P = "flv_1080p3";
    public static final boolean LIVE_CONTAIN_1080P = LeProperties.getBoolean("live.contain.1080p", false);
    public static final boolean PAY_SWITCH = LeProperties.getBoolean("pay.switch", true);
    public static final int CISCO_DRM = 642003;
    public static final Map<String, String> LIVE_TERMINAL_MAP = Maps.newHashMap();

    public static final Map<CountryCode, Integer> countryMap = Maps.newHashMap();


    //微信直播流key 1031- 562398cc914f69ea302e20f6c4a54eb5
    public static final String WECHATKEY = "562398cc914f69ea302e20f6c4a54eb5";
    static {
        LIVE_TERMINAL_MAP.put("app","141007");
        LIVE_TERMINAL_MAP.put("pad","141005");
        LIVE_TERMINAL_MAP.put("tv","141003");
        LIVE_TERMINAL_MAP.put("web","141001");
    }
    static {
        //码流vodVTypeMap
        vodVTypeMap.put("58", "mp4_180");
        vodVTypeMap.put("21", "mp4_350");
        vodVTypeMap.put("13", "mp4_800");
        vodVTypeMap.put("22", "mp4_1300");
        vodVTypeMap.put("51", "mp4_720p");
        vodVTypeMap.put("52", "mp4_1080p3m");
        vodVTypeMap.put("53", "mp4_1080p6m");


        drmVodVTypeMap.put("188", "mp4_180");
        drmVodVTypeMap.put("189", "mp4_350");
        drmVodVTypeMap.put("190", "mp4_800");
        drmVodVTypeMap.put("191", "mp4_1300");
        drmVodVTypeMap.put("192", "mp4_720p");
        drmVodVTypeMap.put("193", "mp4_1080p3m");
        drmVodVTypeMap.put("194", "mp4_1080p6m");
        drmVodVTypeMap.put("58", "mp4_180");
        drmVodVTypeMap.put("21", "mp4_350");
        drmVodVTypeMap.put("13", "mp4_800");
        drmVodVTypeMap.put("22", "mp4_1300");
        drmVodVTypeMap.put("51", "mp4_720p");
        drmVodVTypeMap.put("52", "mp4_1080p3m");
        drmVodVTypeMap.put("53", "mp4_1080p6m");

        cleanDrmVTypeMap.put("58", "188");
        cleanDrmVTypeMap.put("21", "189");
        cleanDrmVTypeMap.put("13", "190");
        cleanDrmVTypeMap.put("22", "191");
        cleanDrmVTypeMap.put("51", "192");
        cleanDrmVTypeMap.put("52", "193");
        cleanDrmVTypeMap.put("53", "194");

        //countryMap
        countryMap.put(CountryCode.CN, 86);
        countryMap.put(CountryCode.HK, 852);
        countryMap.put(CountryCode.US, 1);

    }


}
