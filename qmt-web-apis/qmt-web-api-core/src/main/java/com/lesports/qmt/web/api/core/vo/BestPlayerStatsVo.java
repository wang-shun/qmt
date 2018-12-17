package com.lesports.qmt.web.api.core.vo;

import java.util.Map;

/**
 * Created by lufei1 on 2015/10/9.
 */
public class BestPlayerStatsVo {
    //id
    private Long pid;
    //球员名称
    private String name;
    //是否首发
    private Boolean starting;
    //位置
    private String position;
    //球员号码
    private Integer number;
    //球员logo
    private String logo;
    //技术统计
    private Map<String, String> stats;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStarting() {
        return starting;
    }

    public void setStarting(Boolean starting) {
        this.starting = starting;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Map<String, String> getStats() {
        return stats;
    }

    public void setStats(Map<String, String> stats) {
        this.stats = stats;
    }

    @Override
    public String toString() {
        return "BestPlayerStatsVo{" +
                "pid=" + pid +
                ", name='" + name + '\'' +
                ", starting=" + starting +
                ", position='" + position + '\'' +
                ", number=" + number +
                ", logo='" + logo + '\'' +
                ", stats=" + stats +
                '}';
    }
}
