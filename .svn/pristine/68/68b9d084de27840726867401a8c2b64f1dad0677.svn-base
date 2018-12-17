package com.lesports.qmt.sbd.vo;

import com.lesports.qmt.mvc.QmtVo;
import com.lesports.qmt.sbd.model.Team;
import org.apache.commons.beanutils.LeBeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by lufei1 on 2016/10/31.
 */
public class TeamVo extends Team implements QmtVo {

    //大项名称
    private String gameFName;

    private String country;

    private Boolean online;


    public String getGameFName() {
        return gameFName;
    }

    public void setGameFName(String gameFName) {
        this.gameFName = gameFName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public TeamVo() {
    }

    public TeamVo(Team team) {
        try {
            LeBeanUtils.copyProperties(this, team);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
    }

    public Team toModel() {
        //直接用类型转换得到的对象会报序列化错误
        Team team = new Team();
        try {
            LeBeanUtils.copyProperties(team, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        return team;
    }
}
