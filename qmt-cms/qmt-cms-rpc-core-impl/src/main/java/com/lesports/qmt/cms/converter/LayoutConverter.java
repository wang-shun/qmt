package com.lesports.qmt.cms.converter;

import com.lesports.qmt.cms.api.dto.TLayout;
import com.lesports.qmt.cms.converter.support.AbstractCmsTDtoConverter;
import com.lesports.qmt.cms.model.Layout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * User: ellios
 * Time: 16-12-6 : 下午7:24
 */
@Component("layoutConverter")
public class LayoutConverter extends AbstractCmsTDtoConverter<Layout, TLayout>{

    private static final Logger LOG = LoggerFactory.getLogger(LayoutConverter.class);

    @Override
    protected void fillDto(TLayout dto, Layout model) {
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setPath(model.getPath());
        dto.setDesc(model.getDesc());
        dto.setType(model.getType());
        dto.setVm(model.getVm());
    }

    @Override
    protected TLayout createEmptyDto() {
        return new TLayout();
    }
}