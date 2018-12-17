package com.lesports.qmt.op.web.api.core.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.op.web.api.core.vo.baidu.CompetitionVo;
import com.lesports.qmt.op.web.api.core.vo.baidu.SiteMapVo;

/**
 * Created by lufei1 on 2015/8/20.
 */
public interface CompetitionService {

    /**
     * 通过赛事id查询赛事信息
     * @param id
     * @return
     */
    public CompetitionVo getCompetitionVo(long id, CallerParam caller);

    /**
     * 分页通过赛事id查询赛事信息
     * @param id
     * @param currentPage
     * @param perPage
     * @return
     */
    public CompetitionVo getCompetitionVo(long id, int currentPage, int perPage, CallerParam caller);

    public SiteMapVo getSiteMapVo(long id);
}
