package com.lesports.qmt.cms.converter;

import com.lesports.qmt.cms.api.dto.TWidget;
import com.lesports.qmt.cms.converter.support.AbstractCmsTDtoConverter;
import com.lesports.qmt.cms.model.Widget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * User: ellios
 * Time: 16-12-9 : 上午10:28
 */
@Component("widgetConverter")
public class WidgetConverter extends AbstractCmsTDtoConverter<Widget, TWidget> {

    private static final Logger LOG = LoggerFactory.getLogger(WidgetConverter.class);

    @Override
    protected void fillDto(TWidget dto, Widget model) {
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setPageType(model.getPageType());
        dto.setPath(model.getPath());
        dto.setType(model.getType());
        dto.setVersion(model.getVersion());
        dto.setVm(model.getVm());
    }

    @Override
    protected TWidget createEmptyDto() {
        return new TWidget();
    }
}
