package com.lesports.qmt.sbd.adapter;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.qmt.sbd.model.Team;
import com.lesports.qmt.sbd.param.TeamParam;
import com.lesports.qmt.sbd.vo.TeamVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by lufei1 on 2016/11/16.
 */
@Component
public class TeamAdapter {

    public TeamVo toVo(TeamParam param) {
        TeamVo vo = new TeamVo();
        vo.setId(param.getId());
        vo.setGameFType(param.getGameFType());
        vo.setName(param.getName());
        vo.setAbbreviation(param.getAbbreviation());
        vo.setCity(param.getCity());
        vo.setDesc(param.getDesc());
        vo.setTeamType(param.getTeamType());
        vo.setConference(param.getConference());
        vo.setRegion(param.getRegion());
        vo.setCountryId(param.getCountryId());
        vo.setHomeGround(param.getHomeGround());
        //球队logo
        ImageUrlExt image = JSON.parseObject(param.getLogo(), ImageUrlExt.class);
        if (image != null) {
            vo.setLogo(image);
        }

        //球队logo
        ImageUrlExt bgImage = JSON.parseObject(param.getBgLogo(), ImageUrlExt.class);
        if (bgImage != null) {
            vo.setBgLogo(bgImage);
        }

        //球队logo
        ImageUrlExt superImage = JSON.parseObject(param.getSuperLogo(), ImageUrlExt.class);
        if (superImage != null) {
            vo.setSuperLogo(superImage);
        }

        List<Map> honors = JSON.parseArray(param.getHonors(), Map.class);
        if (CollectionUtils.isNotEmpty(honors)) {
            List<String> honorList = Lists.newArrayList();
            for (Map honor : honors) {
                String honorStr = MapUtils.getString(honor, "honor");
                if (StringUtils.isNotBlank(honorStr)) {
                    honorList.add(honorStr);
                }
            }
            vo.setHonors(honorList);
        }
        return vo;
    }

    public Team copyEditableProperties(Team existsEntity, TeamVo vo) {
        existsEntity.setGameFType(vo.getGameFType());
        existsEntity.setTeamType(vo.getTeamType());
        existsEntity.setName(vo.getName());
        existsEntity.setAbbreviation(vo.getAbbreviation());
        existsEntity.setConference(vo.getConference());
        existsEntity.setRegion(vo.getRegion());
        existsEntity.setCountryId(vo.getCountryId());
        existsEntity.setCity(vo.getCity());
        existsEntity.setHomeGround(vo.getHomeGround());
        existsEntity.setDesc(vo.getDesc());
        existsEntity.setHonors(vo.getHonors());
        existsEntity.setLogo(vo.getLogo());
        return existsEntity;
    }
}
