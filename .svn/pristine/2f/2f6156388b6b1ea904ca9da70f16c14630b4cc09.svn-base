package com.lesports.qmt.config.vo;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.config.model.DictEntry;
import com.lesports.qmt.mvc.QmtVo;
import com.lesports.utils.CallerUtils;
import org.apache.commons.beanutils.LeBeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by denghui on 2016/11/7.
 */
public class DictEntryVo extends DictEntry implements QmtVo {

    public DictEntryVo() {
    }

    public DictEntryVo(DictEntry dictEntry, CallerParam caller) {
        setId(dictEntry.getId());
        String name = CallerUtils.getValueOfLanguage(dictEntry.getMultiLangNames(), caller.getLanguage());
        setName(StringUtils.isEmpty(name) ? dictEntry.getName() : name);
        setParentId(dictEntry.getParentId());
        setTopParentId(dictEntry.getTopParentId());
        setCreateAt(dictEntry.getCreateAt());
        setUpdateAt(dictEntry.getUpdateAt());
        setGameFTypes(dictEntry.getGameFTypes());
    }

    public DictEntry toDict() {
        //直接用类型转换得到的对象会报序列化错误
        DictEntry dictEntry = new DictEntry();
        try {
            LeBeanUtils.copyProperties(dictEntry, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        return dictEntry;
    }
}
