package com.lesports.qmt.sbc.converter;

import com.lesports.qmt.sbc.api.dto.TTopicItem;
import com.lesports.qmt.sbc.api.dto.TTopicItemPackage;
import com.lesports.qmt.sbc.converter.support.AbstractSbcTDtoConverter;
import com.lesports.qmt.sbc.helper.RelatedHelper;
import com.lesports.qmt.sbc.model.TopicItem;
import com.lesports.qmt.sbc.model.TopicItemPackage;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * Created by denghui on 2016/12/18.
 */
@Component("topicItemPackageConverter")
public class TTopicItemPackageConverter extends AbstractSbcTDtoConverter<TopicItemPackage, TTopicItemPackage> {

    @Resource
    private RelatedHelper relatedHelper;

    @Override
    protected void fillDto(TTopicItemPackage dto, TopicItemPackage model) {
        dto.setId(model.getId());
        dto.setTopicId(LeNumberUtils.toLong(model.getTopicId()));
        dto.setName(model.getName());
        dto.setOrder(LeNumberUtils.toInt(model.getOrder()));

        if (CollectionUtils.isNotEmpty(model.getItems())) {
            for (TopicItem topicItem : model.getItems()) {
                TTopicItem tTopicItem = new TTopicItem();
                fillTTopicItem(tTopicItem, topicItem);
                dto.addToItems(tTopicItem);
            }
            //对专题包内容通过order进行排序
            Collections.sort(dto.getItems(), (o1, o2) -> LeNumberUtils.toInt(o1.getOrder()) - LeNumberUtils.toInt(o2.getOrder()));
        }
    }

    private void fillTTopicItem(TTopicItem dto, TopicItem model) {
        dto.setItemId(model.getItemId());
        dto.setName(model.getName());
        dto.setType(model.getType());
        dto.setOrder(LeNumberUtils.toInt(model.getOrder()));
        dto.setSubName(model.getSubName());
        String name = relatedHelper.getNameByEntityId(model.getItemId(), CallerUtils.getDefaultCaller());
        dto.setOriginalName(name);
    }

    @Override
    protected TTopicItemPackage createEmptyDto() {
        return new TTopicItemPackage();
    }
}
