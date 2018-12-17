package com.lesports.qmt.sbd.service;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.sbd.api.dto.TTopList;
import com.lesports.qmt.sbd.api.service.GetSeasonTopListsParam;
import com.lesports.qmt.sbd.model.TopList;
import com.lesports.qmt.service.support.QmtService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by lufei on 2016/10/24.
 */
public interface TopListService extends QmtService<TopList, Long> {

    public void deleteTopListItem(long id, TopList.TopListItem item);

    TTopList getTTopListById(long id, CallerParam caller);

    List<TTopList> getTTopListsByIds(List<Long> ids, CallerParam caller);

    List<Long> getSeasonTopListIds(GetSeasonTopListsParam p, Pageable pageable, CallerParam caller);

    List<TTopList> getCompetitorTTopLists(GetSeasonTopListsParam p, PageParam pageParam, CallerParam caller);
}
