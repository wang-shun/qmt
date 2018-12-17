package com.lesports.qmt.sbc.converter;

import com.alibaba.fastjson.JSON;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.qmt.mvc.QmtVoConverter;
import com.lesports.qmt.sbc.model.ProgramAlbum;
import com.lesports.qmt.sbc.param.SaveProgramAlbumParam;
import com.lesports.qmt.sbc.vo.ProgramAlbumVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by denghui on 2016/12/6.
 */
@Component
public class ProgramAlbumVoConverter implements QmtVoConverter<ProgramAlbum, ProgramAlbumVo> {

    @Override
    public ProgramAlbumVo toVo(Object object) {
        SaveProgramAlbumParam param = (SaveProgramAlbumParam) object;
        ProgramAlbumVo vo = new ProgramAlbumVo();
        vo.setId(param.getId());
        vo.setName(param.getName());
        if (StringUtils.isNotEmpty(param.getLogo())) {
            Map<String, ImageUrlExt> logo = JSON.parseObject(param.getLogo(), Map.class);
            vo.setLogo(logo);
        }
        vo.setRecommend(param.getRecommend());
        return vo;
    }

    @Override
    public ProgramAlbum copyEditableProperties(ProgramAlbum existsEntity, ProgramAlbumVo programAlbumVo) {
        existsEntity.setName(programAlbumVo.getName());
        existsEntity.setLogo(programAlbumVo.getLogo());
        existsEntity.setRecommend(programAlbumVo.getRecommend());
        return existsEntity;
    }
}
