package com.lesports.qmt.config.converter;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.lesports.qmt.config.model.DictEntry;
import com.lesports.qmt.config.param.SaveDictParam;
import com.lesports.qmt.config.vo.DictEntryVo;
import com.lesports.qmt.mvc.QmtVoConverter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by denghui on 2016/12/8.
 */
@Component
public class DictVoConverter implements QmtVoConverter<DictEntry, DictEntryVo> {

    @Override
    public DictEntryVo toVo(Object object) {
        SaveDictParam param = (SaveDictParam) object;
        DictEntryVo vo = new DictEntryVo();
        vo.setId(param.getId());
        vo.setName(param.getName());
        vo.setParentId(param.getParentId());
        List<Long> gameFTypes = JSON.parseArray(param.getGameFTypes(),Long.class);
        if (CollectionUtils.isNotEmpty(gameFTypes)) {
            vo.setGameFTypes(Sets.newHashSet(gameFTypes));
        }
        return vo;
    }

    @Override
    public DictEntry copyEditableProperties(DictEntry existsEntity, DictEntryVo vo) {
        existsEntity.setName(vo.getName());
        existsEntity.setGameFTypes(vo.getGameFTypes());
        return existsEntity;
    }
}
