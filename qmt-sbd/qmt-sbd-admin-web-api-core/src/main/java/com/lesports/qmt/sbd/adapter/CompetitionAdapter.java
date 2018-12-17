package com.lesports.qmt.sbd.adapter;

import com.alibaba.fastjson.JSON;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.qmt.sbd.param.CompetitionParam;
import com.lesports.qmt.sbd.vo.CompetitionVo;
import org.springframework.stereotype.Component;

/**
 * Created by lufei1 on 2016/11/8.
 */
@Component
public class CompetitionAdapter {

    public CompetitionVo toVo(CompetitionParam param) {
        CompetitionVo vo = new CompetitionVo();
        vo.setId(param.getId());
        vo.setName(param.getName());
        vo.setGameFType(param.getGameFType());
        vo.setSystem(param.getSystem());
        vo.setAbbreviation(param.getAbbreviation());
        vo.setVs(param.getVs());
        vo.setGroundOrder(param.getGroundOrder());
        vo.setCurrentSeason(param.getCurrentSeason());
        vo.setOfficialUrl(param.getOfficialUrl());
        vo.setContinent(param.getContinent());
        vo.setNation(param.getNation());
        vo.setIntroduction(param.getIntroduction());
        vo.setChannelId(param.getChannelId());
        vo.setSubChannelId(param.getSubChannelId());
        //赛事logo
        ImageUrlExt image = JSON.parseObject(param.getLogo(), ImageUrlExt.class);
        if (image != null) {
            vo.setLogo(image);
        }
        return vo;
    }

}
