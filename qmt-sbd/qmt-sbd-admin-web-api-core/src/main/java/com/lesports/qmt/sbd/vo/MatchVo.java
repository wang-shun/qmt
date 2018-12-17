package com.lesports.qmt.sbd.vo;

import com.lesports.qmt.mvc.QmtVo;
import com.lesports.qmt.sbd.model.Match;
import org.apache.commons.beanutils.LeBeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by lufei1 on 2016/11/3.
 */
public class MatchVo extends Match implements QmtVo {

    //大项名称
    private String gameFName;
    //比赛状态
    private Integer matchStatus;
    //赛事详情
    private String cname;
    //节目Id
    private Long eid;

    private Boolean online;

    public Integer getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(Integer matchStatus) {
        this.matchStatus = matchStatus;
    }

    public String getGameFName() {
        return gameFName;
    }

    public void setGameFName(String gameFName) {
        this.gameFName = gameFName;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Long getEid() {
        return eid;
    }

    public void setEid(Long eid) {
        this.eid = eid;
    }

    public MatchVo() {
    }

    public MatchVo(Match match) {
        try {
            LeBeanUtils.copyProperties(this, match);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
    }

    public Match toModel() {
        //直接用类型转换得到的对象会报序列化错误
        Match match = new Match();
        try {
            LeBeanUtils.copyProperties(match, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        return match;
    }
}
