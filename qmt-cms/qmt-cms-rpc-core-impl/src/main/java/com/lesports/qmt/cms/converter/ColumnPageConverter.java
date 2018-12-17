package com.lesports.qmt.cms.converter;

import com.lesports.qmt.cms.api.dto.TColumnPage;
import com.lesports.qmt.cms.converter.support.AbstractCmsTDtoConverter;
import com.lesports.qmt.cms.model.ColumnPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * User: ellios
 * Time: 16-12-9 : 上午10:32
 */
@Component("columnPageConverter")
public class ColumnPageConverter extends AbstractCmsTDtoConverter<ColumnPage, TColumnPage> {

    private static final Logger LOG = LoggerFactory.getLogger(ColumnConverter.class);

    @Override
    protected void fillDto(TColumnPage dto, ColumnPage model) {
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setColumnId(model.getColumnId());
        dto.setData(model.getData());
        dto.setMData(model.getmData());
        dto.setLayoutId(model.getLayoutId());
        dto.setType(model.getType());
        dto.setVm(model.getVm());
        dto.setWPaths(model.getWidgetPaths());
    }

    @Override
    protected TColumnPage createEmptyDto() {
        return new TColumnPage();
    }
}
