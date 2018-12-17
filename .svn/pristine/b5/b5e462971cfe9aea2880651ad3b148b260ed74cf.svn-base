package com.lesports.qmt.sbc.vo;

import com.lesports.qmt.mvc.QmtVo;
import com.lesports.qmt.sbc.model.ProgramAlbum;
import com.lesports.utils.LeDateUtils;
import org.apache.commons.beanutils.LeBeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by denghui on 2016/11/15.
 */
public class ProgramAlbumVo extends ProgramAlbum implements QmtVo {
    //专辑首播时间
    private String startTime;
    //最新选集名称
    private String latestPeriodName;
    //最新期数
    private String latestPeriod;

    public ProgramAlbumVo() {
    }

    public ProgramAlbumVo(ProgramAlbum programAlbum) {
        try {
            LeBeanUtils.copyProperties(this, programAlbum);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
    }

    public ProgramAlbum toModel() {
        //直接用类型转换得到的对象会报序列化错误
        ProgramAlbum programAlbum = new ProgramAlbum();
        try {
            LeBeanUtils.copyProperties(programAlbum, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        return programAlbum;
    }

    @Override
    public ProgramAlbumVo pretty() {
        this.setStartTime(LeDateUtils.tansYYYYMMDDHHMMSSPretty(this.getStartTime()));
        return (ProgramAlbumVo) QmtVo.super.pretty();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getLatestPeriodName() {
        return latestPeriodName;
    }

    public void setLatestPeriodName(String latestPeriodName) {
        this.latestPeriodName = latestPeriodName;
    }

    public String getLatestPeriod() {
        return latestPeriod;
    }

    public void setLatestPeriod(String latestPeriod) {
        this.latestPeriod = latestPeriod;
    }
}
