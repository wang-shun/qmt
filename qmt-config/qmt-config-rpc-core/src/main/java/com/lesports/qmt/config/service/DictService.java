package com.lesports.qmt.config.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.config.api.dto.DictEntryTopType;
import com.lesports.qmt.config.api.dto.TDictEntry;
import com.lesports.qmt.config.api.service.GetDictEntriesParam;
import com.lesports.qmt.config.model.DictEntry;
import com.lesports.qmt.service.support.QmtService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * User: ellios
 * Time: 15-6-5 : 上午1:04
 */
public interface DictService extends QmtService<DictEntry, Long> {

    long CHANNEL = 3642401000l;

    TDictEntry getTDictEntryById(Long id, CallerParam caller);

    List<TDictEntry> getTDictEntriesByIds(List<Long> ids, CallerParam caller);

    // 上限限制获取1000条数据
    List<TDictEntry> getDictListByParentId(long parentId, CallerParam caller);

    List<Long> getDictEntryIds4SimpleSearch(GetDictEntriesParam p, Pageable pageable);

    /**
     * 获取字典所属的顶级分类
     * @param id
     * @return
     */
    DictEntryTopType getDictEntryTopType(long id);
}
