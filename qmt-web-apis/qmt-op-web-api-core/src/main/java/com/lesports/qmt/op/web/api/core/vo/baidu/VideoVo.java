package com.lesports.qmt.op.web.api.core.vo.baidu;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by lufei1 on 2015/8/20.
 */
@XmlType(propOrder = {"videoUrl", "isSchedule", "highlightsUrl", "seasonName", "gameTime","gameRounds","gameScores","gameTeam",
        "team1logoUrl", "team1logoRatio", "team2logoUrl", "team2logoRatio", "videoType", "imgUrl", "imgRatio",
        "title", "videoTag", "length", "subtitle", "updateTime", "watchNum", "horizontaImgurl", "horizontaImgRatio",
        "verticalImgurl", "verticalImgRatio", "updateStatus", "brief", "team", "flag_dead", "reserved3", "reserved4"})
public class VideoVo {
    // 必填，播放页URL或录像url，字符串256字节以内
    private String videoUrl;
    //必填，是否赛程视频，非负整数填写。1:是，0:不是.如果是赛程视频以下字段必填：集锦URL、赛季名称、比赛时间、比赛轮次、比赛比分、比赛球队
    private String isSchedule = "0";
    //如果是赛程视频则必填,集锦URL
    private String highlightsUrl;
    //如果是赛程视频则必填,赛季名称
    private String seasonName;
    //如果是赛程视频则必填
    private String gameTime;
    //如果是赛程视频则必填,比赛轮次 ，非负整数填写，没有则填0
    private String gameRounds = "0";
    //如果是赛程视频则必填,比赛比分
    private String gameScores;
    //如果是赛程视频则必填,比赛球队，多个球队用$$分割
    private String gameTeam;
    //比赛球队1logo的url地址
    private String team1logoUrl;
    private String team1logoRatio = "0";
    private String team2logoUrl;
    private String team2logoRatio = "0";
    //必填，例：录像、集锦、五佳、花絮、新闻、其他
    private String videoType;
    //必填，图片的url
    private String imgUrl;
    //必填，图片的比例
    private String imgRatio = "0";
    //必填，主标题,多个字段用$$分割，字符串256字节以内
    private String title;
    //必填，例：(英超、C罗、皇马）,多个字段用$$分割
    private String videoTag;
    //必填，副标题，字符串128字节以内
    private String length;
    //必填，副标题，字符串128字节以内
    private String subtitle;
    //必填，更新时间，包含日期和时间信息，格式为"YYYY-MM-DD hh:mm:ss"
    private String updateTime;
    //必填，观看人数，非负整数填写
    private String watchNum;
    //必填，更新状态,包含日期信息，格式为"YYYY-MM-DD
    private String updateStatus;
    //必填，简介，字符串1024字节以内
    private String brief;
    //必填，所属球队，多个球队用$$分割，字符串64字节以内
    private String team;
    //必填，是否删除此视频链接,1-删除、0-保留，非负整数填写
    private String horizontaImgurl = "0";
    private String horizontaImgRatio = "0";
    private String verticalImgurl = "0";
    private String verticalImgRatio = "0";
    private String flag_dead = "0";
    private String reserved3 = "预留";
    private String reserved4 = "预留";

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getIsSchedule() {
        return isSchedule;
    }

    public void setIsSchedule(String isSchedule) {
        this.isSchedule = isSchedule;
    }

    public String getHighlightsUrl() {
        return highlightsUrl;
    }

    public void setHighlightsUrl(String highlightsUrl) {
        this.highlightsUrl = highlightsUrl;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public String getGameTime() {
        return gameTime;
    }

    public void setGameTime(String gameTime) {
        this.gameTime = gameTime;
    }

    public String getGameRounds() {
        return gameRounds;
    }

    public void setGameRounds(String gameRounds) {
        this.gameRounds = gameRounds;
    }

    public String getGameScores() {
        return gameScores;
    }

    public void setGameScores(String gameScores) {
        this.gameScores = gameScores;
    }

    public String getGameTeam() {
        return gameTeam;
    }

    public void setGameTeam(String gameTeam) {
        this.gameTeam = gameTeam;
    }

    public String getTeam1logoUrl() {
        return team1logoUrl;
    }

    public void setTeam1logoUrl(String team1logoUrl) {
        this.team1logoUrl = team1logoUrl;
    }

    public String getTeam1logoRatio() {
        return team1logoRatio;
    }

    public void setTeam1logoRatio(String team1logoRatio) {
        this.team1logoRatio = team1logoRatio;
    }

    public String getTeam2logoUrl() {
        return team2logoUrl;
    }

    public void setTeam2logoUrl(String team2logoUrl) {
        this.team2logoUrl = team2logoUrl;
    }

    public String getTeam2logoRatio() {
        return team2logoRatio;
    }

    public void setTeam2logoRatio(String team2logoRatio) {
        this.team2logoRatio = team2logoRatio;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgRatio() {
        return imgRatio;
    }

    public void setImgRatio(String imgRatio) {
        this.imgRatio = imgRatio;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoTag() {
        return videoTag;
    }

    public void setVideoTag(String videoTag) {
        this.videoTag = videoTag;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getWatchNum() {
        return watchNum;
    }

    public void setWatchNum(String watchNum) {
        this.watchNum = watchNum;
    }

    public String getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(String updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getFlag_dead() {
        return flag_dead;
    }

    public void setFlag_dead(String flag_dead) {
        this.flag_dead = flag_dead;
    }

    public String getReserved3() {
        return reserved3;
    }

    public void setReserved3(String reserved3) {
        this.reserved3 = reserved3;
    }

    public String getReserved4() {
        return reserved4;
    }

    public void setReserved4(String reserved4) {
        this.reserved4 = reserved4;
    }

    public String getHorizontaImgurl() {
        return horizontaImgurl;
    }

    public void setHorizontaImgurl(String horizontaImgurl) {
        this.horizontaImgurl = horizontaImgurl;
    }

    public String getHorizontaImgRatio() {
        return horizontaImgRatio;
    }

    public void setHorizontaImgRatio(String horizontaImgRatio) {
        this.horizontaImgRatio = horizontaImgRatio;
    }

    public String getVerticalImgurl() {
        return verticalImgurl;
    }

    public void setVerticalImgurl(String verticalImgurl) {
        this.verticalImgurl = verticalImgurl;
    }

    public String getVerticalImgRatio() {
        return verticalImgRatio;
    }

    public void setVerticalImgRatio(String verticalImgRatio) {
        this.verticalImgRatio = verticalImgRatio;
    }
}
