package com.lesports.qmt.web.api.core.service;

import com.lesports.api.common.CallerParam;
import com.lesports.jersey.support.model.CallerBean;
import com.lesports.qmt.web.api.core.vo.BlockContentVo;
import com.lesports.qmt.web.api.core.vo.NewsVo;

import java.util.List;

/**
 * Created by ruiyuansheng on 2016/4/11.
 */
public interface CmsService {

    List<BlockContentVo> getBlockContents(long cmsId, CallerBean caller);

    List<BlockContentVo> createBlockContents(List<NewsVo> newsVos, CallerParam caller);


}
