package com.lesports.qmt.config.converter;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.config.api.dto.TDictEntry;
import com.lesports.qmt.config.converter.support.AbstractConfigTDtoConverter;
import com.lesports.qmt.config.model.DictEntry;
import com.lesports.utils.CallerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by denghui on 2016/11/11.
 */
@Component("dictEntryConverter")
public class TDictEntryConverter extends AbstractConfigTDtoConverter<DictEntry, TDictEntry> {

    @Override
    protected void fillDto(TDictEntry dto, DictEntry model) {
        dto.setId(model.getId());
        dto.setName(model.getReadableName());
        dto.setMultiLangNames(model.getMultiLangReadableNames());
        dto.setParentId(model.getParentId());
        //兼容一下,现在后台没有添加 topParentId
        if (model.getTopParentId() != null) {
            dto.setTopParentId(model.getTopParentId());
        }
    }

    @Override
    protected TDictEntry createEmptyDto() {
        return new TDictEntry();
    }

    @Override
    public TDictEntry adaptDto(TDictEntry dto, CallerParam caller) {
        Preconditions.checkNotNull(dto);
        Preconditions.checkNotNull(caller);
        String name = CallerUtils.getValueOfLanguage(dto.getMultiLangNames(), caller.getLanguage());
        if (StringUtils.isNotEmpty(name)) {
            dto.setName(name);
        }
        dto.setMultiLangNamesIsSet(false);
        return dto;
    }

    public List<TDictEntry> toDtoList(List<DictEntry> dictEntries) {
        List<TDictEntry> resultList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(dictEntries)) {
            for (DictEntry entry : dictEntries) {
                resultList.add(toDto(entry));
            }
        }
        return resultList;
    }
}
