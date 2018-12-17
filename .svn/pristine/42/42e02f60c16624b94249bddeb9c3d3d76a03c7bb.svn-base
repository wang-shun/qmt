package com.lesports.qmt.web.api.core.service;

import com.lesports.api.common.CallerParam;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.qmt.web.api.core.vo.ReCommendTheme;
import com.lesports.qmt.web.api.core.vo.TvSuggestVo;

import java.util.List;
import java.util.Map;

/**
 * Created by ruiyuansheng on 2016/1/11.
 */
public interface TvSuggestService {

    Map<String,Object> getTvHomePageVo(CallerParam callerParam);

    Map<String,Object> getTvDesktopVo(CallerParam callerParam);

    List<TvSuggestVo> getTvMatchHallVo(CallerParam callerParam);
    ReCommendTheme getTvSuggestByThemeId(String themeId);

    List getRecommends(String uid, String lc, int type, int page, CallerParam callerParam);
}
