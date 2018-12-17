package com.lesports.qmt.web.api.core.service;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.web.api.core.vo.AppCompetitionSeasonTopListVo;
import com.lesports.qmt.web.api.core.vo.TopListVo;
import com.lesports.qmt.sbd.api.service.GetSeasonTopListsParam;

import java.util.List;
import java.util.Map;

/**
 * Created by gengchengliang on 2015/6/29.
 */
public interface TopListService {



    TopListVo getTopListById(long id, CallerParam callerParam);

    /**
	 * 获取某赛事的榜单数据
	 * @param p
	 * @param callerParam
	 * @return
	 */
	List<TopListVo> getSeasonTopLists(GetSeasonTopListsParam p, PageParam pageParam, CallerParam callerParam);

    AppCompetitionSeasonTopListVo getSeasonTopLists4App(GetSeasonTopListsParam p, CallerParam callerParam);

    /**
     * 获取某种类型的赛季榜单
     * @param p
     * @param callerParam
     * @return
     */
    TopListVo getSeasonTopList(GetSeasonTopListsParam p, PageParam pageParam, CallerParam callerParam);

    TopListVo getSeasonTopList4TV(GetSeasonTopListsParam p, PageParam pageParam, CallerParam callerParam);

	Map<String, Map<String, List<TopListVo>>> getNbaTopList(long csid, CallerParam callerParam);

}
