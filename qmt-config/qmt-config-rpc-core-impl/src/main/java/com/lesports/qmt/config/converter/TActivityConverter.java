package com.lesports.qmt.config.converter;

import com.lesports.qmt.config.api.dto.TActivity;
import com.lesports.qmt.config.converter.support.AbstractConfigTDtoConverter;
import com.lesports.qmt.config.model.Activity;
import com.lesports.utils.math.LeNumberUtils;
import org.springframework.stereotype.Component;

/**
 * Created by denghui on 2016/12/9.
 */
@Component("activityConverter")
public class TActivityConverter extends AbstractConfigTDtoConverter<Activity, TActivity> {

    @Override
    protected void fillDto(TActivity dto, Activity model) {
        dto.setId(model.getId());
        dto.setName(model.getName());
        if (model.getImage() != null) {
            dto.setImageUrl(model.getImage().getUrl());
        }
        dto.setResourceId(model.getResourceId());
        dto.setResourceType(model.getResourceType());
        dto.setResourceUrl(model.getResourceUrl());
        dto.setStatus(LeNumberUtils.toBoolean(model.getOnline()) ? 1 : 0);
        dto.setType(model.getType());
    }

    @Override
    protected TActivity createEmptyDto() {
        return new TActivity();
    }
}
