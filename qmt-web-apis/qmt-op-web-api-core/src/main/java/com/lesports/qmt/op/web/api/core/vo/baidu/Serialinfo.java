package com.lesports.qmt.op.web.api.core.vo.baidu;

import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Created by lufei1 on 2015/8/20.
 */
@XmlType(propOrder = {"site", "leagueUrl", "leaguelogoUrl", "leaguelogoRatio", "title",
        "shortTitle", "nation", "brief", "lastgame", "sport", "state", "resourceTime",
        "is_delete", "reserved1", "reserved2","video"})
public class Serialinfo {
    private String site = "sports.letv.com";
    private String leagueUrl;
    private String leaguelogoUrl;
    private String leaguelogoRatio = "0";
    private String title;
    private String shortTitle;
    private String nation;
    private String brief;
    private String lastgame;
    private String sport;
    private String state;
    private String resourceTime;
    private String is_delete;
    private String reserved1 = "预留";
    private String reserved2 = "预留";

    private List<VideoVo> video;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getLeagueUrl() {
        return leagueUrl;
    }

    public void setLeagueUrl(String leagueUrl) {
        this.leagueUrl = leagueUrl;
    }

    public String getLeaguelogoUrl() {
        return leaguelogoUrl;
    }

    public void setLeaguelogoUrl(String leaguelogoUrl) {
        this.leaguelogoUrl = leaguelogoUrl;
    }

    public String getLeaguelogoRatio() {
        return leaguelogoRatio;
    }

    public void setLeaguelogoRatio(String leaguelogoRatio) {
        this.leaguelogoRatio = leaguelogoRatio;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getLastgame() {
        return lastgame;
    }

    public void setLastgame(String lastgame) {
        this.lastgame = lastgame;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getResourceTime() {
        return resourceTime;
    }

    public void setResourceTime(String resourceTime) {
        this.resourceTime = resourceTime;
    }

    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }

    public String getReserved1() {
        return reserved1;
    }

    public void setReserved1(String reserved1) {
        this.reserved1 = reserved1;
    }

    public String getReserved2() {
        return reserved2;
    }

    public void setReserved2(String reserved2) {
        this.reserved2 = reserved2;
    }

    public List<VideoVo> getVideo() {
        return video;
    }

    public void setVideo(List<VideoVo> video) {
        this.video = video;
    }
}
