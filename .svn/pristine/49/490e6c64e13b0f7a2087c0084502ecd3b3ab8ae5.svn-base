package com.lesports.qmt.sbc.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbc.api.dto.TTopicItemPackage;
import com.lesports.qmt.sbc.model.TopicItemPackage;
import com.lesports.qmt.service.support.QmtService;

import java.util.List;

/**
 * Created by denghui on 2016/11/2.
 */
public interface TopicItemPackageService extends QmtService<TopicItemPackage, Long> {

    TTopicItemPackage getTTopicItemPackageById(long id, CallerParam caller);

    List<TTopicItemPackage> getTTopicItemPackagesByIds(List<Long> ids, CallerParam caller);

    List<Long> getTopicItemPackageIdsByTopicId(long topicId, CallerParam caller);
}
