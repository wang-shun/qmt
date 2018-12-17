package com.lesports.qmt.web.api.core.vo;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * app赛季榜单
 * User: ellios
 * Time: 15-7-15 : 下午4:12
 */
public class AppCompetitionSeasonTopListVo implements Serializable{
    private static final long serialVersionUID = -3909662789879137565L;

    //赛事id
    private Long cid;
    //赛事名称
    private String cname;
    //赛季id
    private Long csid;
    //赛季名称
    private String season;
    //赛事logo
    private String clogo;
    private List<AppTopListVo> toplists = Lists.newArrayList();

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Long getCsid() {
        return csid;
    }

    public void setCsid(Long csid) {
        this.csid = csid;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getClogo() {
        return clogo;
    }

    public void setClogo(String clogo) {
        this.clogo = clogo;
    }

    public List<AppTopListVo> getToplists() {
        return toplists;
    }

    public void setToplists(List<AppTopListVo> toplists) {
        this.toplists = toplists;
    }

    public void addTopList(AppTopListVo appTopListVo){
        this.toplists.add(appTopListVo);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AppCompetitionSeasonTopListVo{");
        sb.append("cid=").append(cid);
        sb.append(", cname='").append(cname).append('\'');
        sb.append(", csid=").append(csid);
        sb.append(", season='").append(season).append('\'');
        sb.append(", clogo='").append(clogo).append('\'');
        sb.append(", toplists=").append(toplists);
        sb.append('}');
        return sb.toString();
    }

    public static class AppTopListVo implements Serializable{
        private static final long serialVersionUID = -7054262978535562828L;

        private Long toplistId;
        private String type;
        private Long typeId;
        private String h5Url;
        private String desc;

        public Long getToplistId() {
            return toplistId;
        }

        public void setToplistId(Long toplistId) {
            this.toplistId = toplistId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Long getTypeId() {
            return typeId;
        }

        public void setTypeId(Long typeId) {
            this.typeId = typeId;
        }

        public String getH5Url() {
            return h5Url;
        }

        public void setH5Url(String h5Url) {
            this.h5Url = h5Url;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("AppTopListVo{");
            sb.append("toplistId=").append(toplistId);
            sb.append(", type='").append(type).append('\'');
            sb.append(", typeId=").append(typeId);
            sb.append(", h5Url='").append(h5Url).append('\'');
            sb.append(", desc='").append(desc).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}



