package com.lesports.qmt.sbd.vo;

import com.lesports.qmt.mvc.QmtVo;
import com.lesports.qmt.sbd.model.Competition;
import org.apache.commons.beanutils.LeBeanUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lufei1 on 2016/10/31.
 */
public class CompetitionVo extends Competition implements QmtVo {

    //赛事名称
    @NotBlank(message = "name is required")
    private String name;
    //大项
    @NotNull(message = "gameFType is required")
    private Long gameFType;
    //大项名称
    private String gameFName;


    public String getGameFName() {
        return gameFName;
    }

    public void setGameFName(String gameFName) {
        this.gameFName = gameFName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getGameFType() {
        return gameFType;
    }

    public void setGameFType(Long gameFType) {
        this.gameFType = gameFType;
    }

    public CompetitionVo() {
    }

    public CompetitionVo(Competition competition) {
        try {
            LeBeanUtils.copyProperties(this, competition);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
    }

    public Competition toModel() {
        //直接用类型转换得到的对象会报序列化错误
        Competition competition = new Competition();
        try {
            LeBeanUtils.copyProperties(competition, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        return competition;
    }

}
