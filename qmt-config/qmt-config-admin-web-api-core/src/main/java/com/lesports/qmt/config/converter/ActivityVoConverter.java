package com.lesports.qmt.config.converter;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.qmt.config.model.Activity;
import com.lesports.qmt.config.param.SaveActivityParam;
import com.lesports.qmt.config.vo.ActivityVo;
import com.lesports.qmt.mvc.QmtVoConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by denghui on 2016/12/9.
 */
@Component
public class ActivityVoConverter implements QmtVoConverter<Activity, ActivityVo> {
    
    @Override
    public ActivityVo toVo(Object object) {
        SaveActivityParam param = (SaveActivityParam) object;
        ActivityVo vo = new ActivityVo();
        vo.setId(param.getId());
        vo.setName(param.getName());
        vo.setType(param.getType());
        vo.setResourceType(param.getResourceType());
        vo.setResourceId(param.getResourceId());
        vo.setResourceUrl(param.getResourceUrl());
        if (StringUtils.isNotEmpty(param.getImage())) {
            ImageUrlExt imageUrlExt = JSON.parseObject(param.getImage(), ImageUrlExt.class);
            vo.setImage(imageUrlExt);
        }
        if (StringUtils.isNotEmpty(param.getEids())) {
            List<Long> eids = JSON.parseArray(param.getEids(), Long.class);
            vo.setEids(Sets.newHashSet(eids));
        }
        return vo;
    }

    @Override
    public Activity copyEditableProperties(Activity existsEntity, ActivityVo activityVo) {
        existsEntity.setName(activityVo.getName());
        existsEntity.setType(activityVo.getType());
        existsEntity.setResourceType(activityVo.getResourceType());
        existsEntity.setResourceId(activityVo.getResourceId());
        existsEntity.setResourceUrl(activityVo.getResourceUrl());
        existsEntity.setImage(activityVo.getImage());
        existsEntity.setEids(activityVo.getEids());
        return existsEntity;
    }
}
