package com.lesports.qmt.web.api.core.service;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.web.api.core.vo.AlbumVo;

import java.util.List;

/**
 * Created by ruiyuansheng on 2015/7/30.
 */
public interface AlbumService {

    List<AlbumVo> getAllAlbums(PageParam pageParam, CallerParam callerParam);

    AlbumVo getAlbumById(long id, CallerParam callerParam);

    List<AlbumVo> getTAlbums(long tagId, PageParam pageParam, CallerParam callerParam);
}
