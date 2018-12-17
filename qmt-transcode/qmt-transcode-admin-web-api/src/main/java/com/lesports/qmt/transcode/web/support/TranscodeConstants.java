package com.lesports.qmt.transcode.web.support;

import com.google.common.collect.Lists;
import com.lesports.qmt.sbc.api.common.TranscodeStatus;
import com.lesports.qmt.transcode.model.VideoTranscodeTaskStatus;
import com.lesports.utils.LeProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pangchuanxiao on 2016/10/25.
 */
public class TranscodeConstants {
    //媒资相关
    public static final int MMS_LESPORTS_CATEGORY = 4;
    //    public static final int MMS_LESPORTS_DISABLE_TYPE = 4;
//    public static final int MMS_LESPORTS_COPYRIGHT_SITE = 650001;
    public static final int MMS_LESPORTS_SOURCE_ID = 200034;
    public static final int MMS_LESPORTS_CISCO_DRM = 642003;

    //    public static final String MMS_LESPORTS_PLATFORM = "sport";
    public static final String MMS_LESPORTS_PLATFORM = "upload";
    public static final String MMS_LESPORTS_PRIVATE_KEY = "4567";

    //云相关 新key 712e1b7f630e8954375105bf
    public static final String VIDEO_TRANSCODE_USER = "lesports";
//    public static final String VIDEO_UPLOAD_APP_KEY = "50163f2ee4b09e010c62a591";
//    public static final String LIVE_TO_VIDEO_APP_KEY = "504d80e8e4b071811bf7c7af";
//    public static final String CLOUD_STORAGE_APP_KEY = "504d80e8e4b071811bf7c7af";

//    0cf25369062ebd5876149605001
    public static final String TRANSCODE_SECRET_KEY = LeProperties.getString("transcode.secret.key", "504d80e8e4b071811bf7c7af504d");
    //0cf25369062ebd5876149605
    public static final String TRANSCODE_APP_KEY = LeProperties.getString("transcode.app.key", "712e1b7f630e8954375105bf");

    public static final String VIDEO_UPLOAD_APP_KEY = "712e1b7f630e8954375105bf";
    public static final String LIVE_TO_VIDEO_APP_KEY = "712e1b7f630e8954375105bf";
//    public static final String CLOUD_STORAGE_SECRET_KEY = "712e1b7f630e8954375105bf";

    public static final String VIDEO_UPLOAD_MULTI_TYPE = "L|4";
    public static final String VIDEO_UPLOAD_VERSION = "client_normal_01";
//    public static final Integer FORMAT_MP4 = 9;
//    public static final Integer FORMAT_MP4_350 = 21;
    public static final String LIVE_RECORDING_BIZ_ID = "L|2|1|1";
    public static final List<VideoCodeRateText> DEFAULT_FORMAT = Lists.newArrayList(
            VideoCodeRateText.MP4_180, VideoCodeRateText.MP4,
            VideoCodeRateText.MP4_350, VideoCodeRateText.MP4_800, VideoCodeRateText.MP4_1300
    );
    public static final List<VideoCodeRateText> SELECT_FORMAT = Lists.newArrayList(
            VideoCodeRateText.MP4_720P, VideoCodeRateText.MP4_1080P, VideoCodeRateText.MP4_1080P3M, VideoCodeRateText.MP4_1080P6M,
            VideoCodeRateText.MP4_4K, VideoCodeRateText.MP4_4K_15M
    );

    //后台相关
    public static final String CLIENT_FROM = "client";
    public static final String WEB_FROM = "web";

    public static final Map<Integer, TranscodeStatus> TRANSCODE_FORMAT_STATUS_MAPPING = new HashMap<>();
    static {
        //http://wiki.letv.cn/pages/viewpage.action?pageId=63378955
        TRANSCODE_FORMAT_STATUS_MAPPING.put(-25, TranscodeStatus.TRANSCODING_FAIL);
        TRANSCODE_FORMAT_STATUS_MAPPING.put(25, TranscodeStatus.DISPATCH_SUCCESS);
        TRANSCODE_FORMAT_STATUS_MAPPING.put(-15, TranscodeStatus.TRANSCODING_FAIL);
        TRANSCODE_FORMAT_STATUS_MAPPING.put(15, TranscodeStatus.TRANSCODING_SUCCESS);

    }

    public static final Map<Integer, VideoTranscodeTaskStatus> TRANSCODE_TASK_FORMAT_STATUS_MAPPING = new HashMap<>();
    static {
        //http://wiki.letv.cn/pages/viewpage.action?pageId=63378955
        TRANSCODE_TASK_FORMAT_STATUS_MAPPING.put(-25, VideoTranscodeTaskStatus.TRANSCODING_FAIL);
        TRANSCODE_TASK_FORMAT_STATUS_MAPPING.put(25, VideoTranscodeTaskStatus.TASK_FINISH);
        TRANSCODE_TASK_FORMAT_STATUS_MAPPING.put(-15, VideoTranscodeTaskStatus.TRANSCODING_FAIL);
        TRANSCODE_TASK_FORMAT_STATUS_MAPPING.put(15, VideoTranscodeTaskStatus.TRANSCODING_SUCCESS);

    }
}
