package com.lesports.qmt.sbc.converter;

import com.alibaba.fastjson.JSON;
import com.lesports.qmt.mvc.QmtVoConverter;
import com.lesports.qmt.sbc.model.TopicItem;
import com.lesports.qmt.sbc.model.TopicItemPackage;
import com.lesports.qmt.sbc.param.SaveTopicItemPackageParam;
import com.lesports.qmt.sbc.vo.TopicItemPackageVo;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Created by denghui on 2016/11/30.
 */
@Component
public class TopicItemPackageVoConverter implements QmtVoConverter<TopicItemPackage, TopicItemPackageVo> {

    @Override
    public TopicItemPackageVo toVo(Object object) {
        SaveTopicItemPackageParam param = (SaveTopicItemPackageParam) object;
        TopicItemPackageVo vo = new TopicItemPackageVo();
        vo.setTopicId(param.getTopicId());
        vo.setName(param.getName());
        vo.setId(param.getId());
        if (StringUtils.isNotEmpty(param.getItems())) {
            List<TopicItem> topicItems = JSON.parseArray(param.getItems(), TopicItem.class);
            Collections.sort(topicItems, ((o1, o2) -> LeNumberUtils.toInt(o1.getOrder()) - LeNumberUtils.toInt(o2.getOrder())));
            vo.setItems(topicItems);
        }
        return vo;
    }

    @Override
    public TopicItemPackage copyEditableProperties(TopicItemPackage existsEntity, TopicItemPackageVo vo) {
        existsEntity.setName(vo.getName());
        existsEntity.setItems(vo.getItems());
        return existsEntity;
    }
}
