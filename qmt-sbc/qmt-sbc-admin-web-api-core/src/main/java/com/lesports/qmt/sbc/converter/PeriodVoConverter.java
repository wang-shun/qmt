package com.lesports.qmt.sbc.converter;

import com.alibaba.fastjson.JSON;
import com.lesports.qmt.sbc.model.Episode;
import com.lesports.qmt.sbc.param.SavePeriodParam;
import com.lesports.qmt.sbc.vo.ProgramAlbumPeriodVo;
import com.lesports.utils.LeDateUtils;
import org.springframework.stereotype.Component;

/**
 * Created by denghui on 2016/11/28.
 */
@Component
public class PeriodVoConverter  {

    public ProgramAlbumPeriodVo toVo(Object object) {
        SavePeriodParam savePeriodParam = (SavePeriodParam) object;
        ProgramAlbumPeriodVo vo = new ProgramAlbumPeriodVo();
        vo.setId(savePeriodParam.getId());
        vo.setName(savePeriodParam.getName());
        vo.setPeriod(savePeriodParam.getPeriod());
        vo.setStartTime(LeDateUtils.tansYMDHMS2YYYYMMDDHHMMSS(savePeriodParam.getStartTime()));
        vo.setChannelId(savePeriodParam.getChannelId());
        vo.setSubChannelId(savePeriodParam.getSubChannelId());
        vo.setCid(savePeriodParam.getCid());
        vo.setVideo(JSON.parseObject(savePeriodParam.getVideo(), ProgramAlbumPeriodVo.SimpleVideoVo.class));
        return vo;
    }

    public Episode copyEditableProperties(Episode existsEntity, ProgramAlbumPeriodVo vo) {
        existsEntity.setName(vo.getName());
        existsEntity.setPeriods(vo.getPeriod());
        existsEntity.setStartTime(vo.getStartTime());
        existsEntity.setChannelId(vo.getChannelId());
        existsEntity.setSubChannelId(vo.getSubChannelId());
        existsEntity.setCid(vo.getCid());
        existsEntity.setRecordId(vo.getVideo() == null ? null : vo.getVideo().getId());
        return existsEntity;
    }
}
