package com.lesports.qmt.web.api.core.vo;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.qmt.sbd.api.common.CompetitorType;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 榜单vo
 * Created by gengchengliang on 2015/6/29.
 */
public class TopListVo implements Serializable{

    private Long id;
    //赛事id
    private Long cid;
    //赛事名称
    private String cname;
    //赛季id
    private Long csid;
    //赛季id
    private Long typeId;
    //赛季
    private String season;
    //榜单类型
    private String listType;
    //榜单类型id
    private Long listTypeId;
    //赛事logo
    private String logo;
    //分组
    private String group;
    //榜单数据
    private List<TopListVoItem> lists = Lists.newArrayList();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getListTypeId() {
        return listTypeId;
    }

    public void setListTypeId(Long listTypeId) {
        this.listTypeId = listTypeId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<TopListVoItem> getLists() {
        return lists;
    }

    public void setLists(List<TopListVoItem> lists) {
        this.lists = lists;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TopListVo{");
        sb.append("id=").append(id);
        sb.append(", cid=").append(cid);
        sb.append(", cname='").append(cname).append('\'');
        sb.append(", csid=").append(csid);
        sb.append(", typeId=").append(typeId);
        sb.append(", season='").append(season).append('\'');
        sb.append(", listType='").append(listType).append('\'');
        sb.append(", listTypeId=").append(listTypeId);
        sb.append(", logo='").append(logo).append('\'');
        sb.append(", group='").append(group).append('\'');
        sb.append(", lists=").append(lists);
        sb.append('}');
        return sb.toString();
    }

    public class TopListVoItem {
        //参赛者id
        private Long competitorId;
        //参赛者类型
        private CompetitorType competitorType;
        //参赛者头像
        private String logo;
        //参赛者所属球队logo
        private String teamLogo;
        //参赛者名称
        private String competitorName;
        //参赛者所属球队id
        private Long competitorTeamId;
        //参赛者所属球队名称
        private String competitorTeamName;
        //参赛者排名
        private Integer competitorOrder;
        //显示排名
        private Integer showOrder;
        //分组
        private String group;
        //场上位置ID
        private Long positionId;
        //场上位置
        private String position;
        //号码
        private Integer number;
        //球队全称
        private String officialName;

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        private Map<String, String> dataMap = Maps.newHashMap();

        public Long getCompetitorId() {
            return competitorId;
        }

        public void setCompetitorId(Long competitorId) {
            this.competitorId = competitorId;
        }

        public CompetitorType getCompetitorType() {
            return competitorType;
        }

        public void setCompetitorType(CompetitorType competitorType) {
            this.competitorType = competitorType;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getCompetitorName() {
            return competitorName;
        }

        public void setCompetitorName(String competitorName) {
            this.competitorName = competitorName;
        }

        public Long getCompetitorTeamId() {
            return competitorTeamId;
        }

        public void setCompetitorTeamId(Long competitorTeamId) {
            this.competitorTeamId = competitorTeamId;
        }

        public String getCompetitorTeamName() {
            return competitorTeamName;
        }

        public void setCompetitorTeamName(String competitorTeamName) {
            this.competitorTeamName = competitorTeamName;
        }

        public Integer getCompetitorOrder() {
            return competitorOrder;
        }

        public void setCompetitorOrder(Integer competitorOrder) {
            this.competitorOrder = competitorOrder;
        }

        public Integer getShowOrder() {
            return showOrder;
        }

        public void setShowOrder(Integer showOrder) {
            this.showOrder = showOrder;
        }

        public Map<String, String> getDataMap() {
            return dataMap;
        }

        public void setDataMap(Map<String, String> dataMap) {
            this.dataMap = dataMap;
        }

        public String getTeamLogo() {
            return teamLogo;
        }

        public void setTeamLogo(String teamLogo) {
            this.teamLogo = teamLogo;
        }

        public Long getPositionId() {
            return positionId;
        }

        public void setPositionId(Long positionId) {
            this.positionId = positionId;
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

        public String getOfficialName() {
            return officialName;
        }

        public void setOfficialName(String officialName) {
            this.officialName = officialName;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("TopListVoItem{");
            sb.append("competitorId=").append(competitorId);
            sb.append(", competitorType=").append(competitorType);
            sb.append(", logo='").append(logo).append('\'');
            sb.append(", teamLogo='").append(teamLogo).append('\'');
            sb.append(", competitorName='").append(competitorName).append('\'');
            sb.append(", competitorTeamId=").append(competitorTeamId);
            sb.append(", competitorTeamName='").append(competitorTeamName).append('\'');
            sb.append(", competitorOrder=").append(competitorOrder);
            sb.append(", showOrder=").append(showOrder);
            sb.append(", group='").append(group).append('\'');
            sb.append(", positionId=").append(positionId);
            sb.append(", position='").append(position).append('\'');
            sb.append(", number=").append(number);
            sb.append(", officialName='").append(officialName).append('\'');
            sb.append(", dataMap=").append(dataMap);
            sb.append('}');
            return sb.toString();
        }
    }
}
