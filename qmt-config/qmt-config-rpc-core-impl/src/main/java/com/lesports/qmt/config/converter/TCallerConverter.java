package com.lesports.qmt.config.converter;

import com.lesports.qmt.config.api.dto.TCaller;
import com.lesports.qmt.config.converter.support.AbstractConfigTDtoConverter;
import com.lesports.qmt.config.model.Caller;
import org.springframework.stereotype.Component;

/**
 * Created by denghui on 2016/12/7.
 */
@Component("callerConverter")
public class TCallerConverter extends AbstractConfigTDtoConverter<Caller, TCaller> {

    @Override
    protected void fillDto(TCaller dto, Caller model) {
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setPlatform(model.getPlatform());
        dto.setSplatId(model.getSplatId());
    }

    @Override
    protected TCaller createEmptyDto() {
        return new TCaller();
    }
}
