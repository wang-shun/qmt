package com.lesports.qmt.op.web.api.core.vo.baidu;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by lufei1 on 2015/8/20.
 */
@XmlType(propOrder = { "loc", "lastmod", "changefreq","priority","data"})
public class Site {
    // 必填，必填字段，站长注意：此字段用于唯一标示此赛事
    private String loc;
    //必填，指该条数据的最新一次更新时间
    private String lastmod;
    //必填，指该条数据的更新频率
    private String changefreq;
    // 必填，用来指定此链接相对于其他链接的优先权比值，此值定于0.0-1.0之间
    private String priority;
    //<!-- 视频相关扩展,赛事记录，此主键为site + shortTitle -->
    private CompetitionData data;




    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getLastmod() {
        return lastmod;
    }

    public void setLastmod(String lastmod) {
        this.lastmod = lastmod;
    }

    public String getChangefreq() {
        return changefreq;
    }

    public void setChangefreq(String changefreq) {
        this.changefreq = changefreq;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }


    public CompetitionData getData() {
        return data;
    }

    public void setData(CompetitionData data) {
        this.data = data;
    }
}
