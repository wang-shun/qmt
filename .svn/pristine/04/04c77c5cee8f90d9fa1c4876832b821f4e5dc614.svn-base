package com.lesports.qmt.config.converter;

import com.lesports.qmt.config.api.dto.TTag;
import com.lesports.qmt.config.converter.support.AbstractConfigTDtoConverter;
import com.lesports.qmt.config.model.Tag;
import org.springframework.stereotype.Component;

/**
 * Created by denghui on 2016/11/11.
 */
@Component("tagConverter")
public class TTagConverter extends AbstractConfigTDtoConverter<Tag, TTag> {

    @Override
    protected void fillDto(TTag dto, Tag model) {
        dto.setId(model.getId());
        dto.setName(model.getName());
    }

    @Override
    protected TTag createEmptyDto() {
        return new TTag();
    }

}
