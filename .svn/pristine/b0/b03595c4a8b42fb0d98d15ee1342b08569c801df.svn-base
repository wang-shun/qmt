package com.lesports.qmt.web.api.core.vo;

/**
 * Created by lufei1 on 2015/7/18.
 */
public enum PlayerErrorCode {
    BUSI_VOD_IP_DALU(1, "大陆ip受限-点播"),
    BUSI_VOD_IP_HAIWAI(2, "海外ip受限-点播"),
    BUSI_USER_NO_LOGIN(3, "用户没有登录"),
    BUSI_VIDEO_OFFLINE(4, "视频下线或者不存在"),
    BUSI_CANT_PLAY(5, "版权限制-点播"),
    BUSI_PAY_VIDEO(6, "付费视频"),
    BUSI_ALBUM_MASK(7, "专辑被屏蔽"),
    BUSI_LIVE_AUTH_FAIL(8, "boss鉴权不通过"),
    BUSI_USER_TOKEN_ERROR(9, "用户token验证不通过"),
    BUSI_USER_TRY_SEED(10, "用户已经试看过"),
    BUSI_USER_HAS_LOGIN(11, "该用户已在另一设备登录"),
    MMS_ACCESS_ERROR(1001, "新媒资接口访问失败"),
    MMS_RES_ERROR(1003, "新媒资接口返回数据错误"),
    LIVE_ACCESS_ERROR(2001, "直播接口访问失败"),
    LIVE_RES_ERROR(2003, "直播接口返回数据错误"),
    BOSS_ACCESS_ERROR(3001, "boss接口访问失败"),
    BOSS_RES_ERROR(3003, "boss接口返回数据错误"),
    USER_ACCESS_ERROR(4001, "用户接口访问失败"),
    USER_RES_ERROR(4003, "用户接口返回数据错误"),
	SPLATID_NOT_ALLOWED_ERROR(8888, "splatId不是内部ID"),
    OTHER_ERROR(9999, "其他异常"),
    SUCCESS(10000, "正常播放");

    private final int code;
    private final String reason;


    PlayerErrorCode(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "PlayerErrorCode{" +
                "code=" + code +
                ", reason='" + reason + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }
}
