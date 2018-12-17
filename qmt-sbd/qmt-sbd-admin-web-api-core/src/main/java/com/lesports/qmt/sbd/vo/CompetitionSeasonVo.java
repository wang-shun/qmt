package com.lesports.qmt.sbd.vo;

import com.lesports.qmt.mvc.QmtVo;
import com.lesports.qmt.sbd.model.CompetitionSeason;
import org.apache.commons.beanutils.LeBeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by lufei1 on 2016/10/31.
 */
public class CompetitionSeasonVo extends CompetitionSeason implements QmtVo {

    //赛事名称
    private String cName;
    //赛季teamSeason信息
    private List<TeamSeasonVo> teamSeasons;
    //球队id
    private String tids;


    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public List<TeamSeasonVo> getTeamSeasons() {
        return teamSeasons;
    }

    public void setTeamSeasons(List<TeamSeasonVo> teamSeasons) {
        this.teamSeasons = teamSeasons;
    }

    public String getTids() {
        return tids;
    }

    public void setTids(String tids) {
        this.tids = tids;
    }

    public CompetitionSeasonVo() {
    }

    public CompetitionSeasonVo(CompetitionSeason competitionSeason) {
        try {
            LeBeanUtils.copyProperties(this, competitionSeason);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
    }

    public CompetitionSeason toModel() {
        //直接用类型转换得到的对象会报序列化错误
        CompetitionSeason competitionSeason = new CompetitionSeason();
        try {
            LeBeanUtils.copyProperties(competitionSeason, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        return competitionSeason;
    }
}
