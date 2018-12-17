package com.lesports.qmt.config.service;

import com.lesports.qmt.config.api.dto.TTag;
import com.lesports.qmt.config.model.Tag;
import com.lesports.qmt.service.support.QmtService;

import java.util.List;

/**
 * User: ellios
 * Time: 15-7-20 : 下午5:30
 */
public interface TagService extends QmtService<Tag, Long> {

    TTag getTTagById(long id);

    List<TTag> getTTagsByIds(List<Long> ids);

    TTag getTTagByName(String name) ;
}
