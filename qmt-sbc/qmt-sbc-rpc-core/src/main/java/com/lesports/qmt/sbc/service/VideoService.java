package com.lesports.qmt.sbc.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbc.api.dto.TFeatureInfo;
import com.lesports.qmt.sbc.api.dto.TVideo;
import com.lesports.qmt.sbc.api.dto.TVideoIndexInfo;
import com.lesports.qmt.sbc.api.dto.TVideoInfo;
import com.lesports.qmt.sbc.api.service.GetRelatedVideosParam;
import com.lesports.qmt.sbc.model.Video;
import com.lesports.qmt.service.support.QmtService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by zhangxudong@le.com on 2016/10/26.
 */
public interface VideoService extends QmtService<Video, Long> {

    boolean cloneVideoToAlbum(long videoId, List<Long> albumIds);

    TVideo getTVideoById(long id, CallerParam caller);

    List<TVideo> getTVideoByIds(List<Long> ids, CallerParam caller);

    List<Long> getVideoIdsByRelatedId(GetRelatedVideosParam p, Pageable pageable, CallerParam caller);

    long countVideosByRelatedIdParam(GetRelatedVideosParam p, CallerParam caller);

    List<Long> getLatestVideoIds(Pageable pageable, CallerParam caller);

    List<Long> getVideoIdsRelatedWithSomeVideo(long vid, Pageable pageable, CallerParam caller);

    TVideoIndexInfo getIndexOfVideoInAlbum(long vid, long aid, CallerParam caller);

    List<Long> getNonPositiveVideosByAid(long aid, Pageable pageable, CallerParam caller);

    TVideoInfo getTVideoInfoByRelatedId(GetRelatedVideosParam p, Pageable pageable, CallerParam caller);

    List<TVideo> getRecordVideosInAlbum(long aid, Pageable pageable, CallerParam caller);

    TFeatureInfo getFeatureVideosByYears(long aid, String yearAndMonth, CallerParam caller);

    List<Long> getAlbumVideosByAid(long aid, Pageable pageable, CallerParam caller);

    Video findLatestVideoByAid(long aid,CallerParam caller);

    TVideo getTVideoByLeecoVid(long leecoVid, CallerParam caller);

}
