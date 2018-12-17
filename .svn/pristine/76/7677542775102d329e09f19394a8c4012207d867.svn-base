package com.lesports.qmt.cms.converter;

import com.lesports.qmt.cms.api.common.PageType;
import com.lesports.qmt.cms.api.dto.TColumn;
import com.lesports.qmt.cms.api.dto.TColumnPage;
import com.lesports.qmt.cms.api.dto.TWidget;
import com.lesports.qmt.cms.converter.support.AbstractCmsTDtoConverter;
import com.lesports.qmt.cms.model.Column;
import com.lesports.qmt.cms.model.ColumnPage;
import com.lesports.qmt.cms.model.Widget;
import com.lesports.qmt.cms.repository.ColumnPageRepository;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * User: ellios
 * Time: 16-12-9 : 上午10:31
 */
@Component("columnConverter")
public class ColumnConverter extends AbstractCmsTDtoConverter<Column, TColumn> {

    private static final Logger LOG = LoggerFactory.getLogger(ColumnConverter.class);

    @Resource
    private ColumnPageRepository columnPageRepository;

    @Resource
    private ColumnPageConverter columnPageConverter;

    @Override
    protected void fillDto(TColumn dto, Column model) {
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setTitle(model.getTitle());
        dto.setChannelId(model.getChannelId());
        dto.setKeyword(model.getKeyword());
        dto.setDesc(model.getDesc());
        dto.setFullPath(model.getFullPath());
        dto.setPath(model.getPath());
        if(model.getSubChannelId() !=null){
            dto.setSubChannelId(model.getSubChannelId());
        }
        dto.setMTemplateUrl(model.getmTemplateUrl());
        List<ColumnPage> pages = columnPageRepository.findPagesByColumnId(model.getId());
        if(CollectionUtils.isNotEmpty(pages)){
            for(ColumnPage page : pages){
                if(dto.getMPage() == null){
                    if(page.getType() == PageType.MOBILE){
                        TColumnPage tPage = columnPageConverter.toDto(page);
                        dto.setMPage(tPage);
                    }
                }
                if(dto.getPcPage() == null){
                    if(page.getType() == PageType.PC){
                        TColumnPage tPage = columnPageConverter.toDto(page);
                        dto.setPcPage(tPage);
                    }
                }
                if(dto.getDummyPage() == null){
                    if(page.getType() == PageType.DUMMY){
                        TColumnPage tPage = columnPageConverter.toDto(page);
                        dto.setDummyPage(tPage);
                    }
                }

            }
        }
    }

    @Override
    protected TColumn createEmptyDto() {
        return new TColumn();
    }
}
