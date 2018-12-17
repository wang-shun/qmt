package com.lesports.qmt.sbd.converter;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.config.client.QmtConfigDictInternalApis;
import com.lesports.qmt.config.model.DictEntry;
import com.lesports.qmt.sbd.model.TopList;
import com.lesports.qmt.sbd.vo.TopListVo;
import org.springframework.stereotype.Component;

/**
 * Created by lufei1 on 2016/11/30.
 */
@Component("topListVoConverter")
public class TopListVoConverter implements VoConverter<TopList, TopListVo> {


    @Override
    public TopListVo toTVo(TopList topList, CallerParam caller) {
        TopListVo vo = new TopListVo(topList);
        DictEntry dictEntry = QmtConfigDictInternalApis.getDictById(vo.getType());
        if (dictEntry != null) {
            vo.setTypeName(dictEntry.getName());
        }
        return vo;
    }
}
