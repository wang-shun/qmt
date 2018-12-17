package com.lesports.qmt.sbc.client;

import com.lesports.qmt.sbc.model.Video;
import com.lesports.qmt.sbc.model.VideoMedium;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * User: ellios
 * Time: 16-10-28 : 下午9:14
 */
public class QmtSbcVideoInternalApis extends QmtSbcInternalApis {

    public static long saveVideoMedium(VideoMedium videoMedium) {
        return saveEntity(videoMedium, VideoMedium.class);
    }

    public static long saveVideo(Video video) {
        return saveVideo(video, false);
    }

    public static long saveVideo(Video video, boolean allowEmpty) {
        return saveEntity(video, Video.class, allowEmpty);
    }

    public static Video getVideoById(Long id) {
        return getEntityById(id, Video.class);
    }

    public static Video getVideoByLeecoVid(Long id) {
        InternalQuery internalQuery = new InternalQuery();
        internalQuery.addCriteria(new InternalCriteria("leeco_vid", "is", id));
        List<Video> videos = getEntitiesByQuery(internalQuery, Video.class);
        if (CollectionUtils.isNotEmpty(videos)) {
            return videos.get(0);
        }
        return null;
    }

    public static VideoMedium getVideoMediumById(Long id) {
        return getEntityById(id, VideoMedium.class);
    }

    public static VideoMedium getVideoMediumByVideoId(Long id) {
        InternalQuery internalQuery = new InternalQuery();
        internalQuery.addCriteria(new InternalCriteria("videoId", "is", id));
        List<VideoMedium> result = getEntitiesByQuery(internalQuery, VideoMedium.class);
        if (CollectionUtils.isNotEmpty(result)) {
            return result.get(0);
        }
        return null;
    }

    public static List<Video> getVideoByIds(List<Long> ids) {
        return getEntitiesByIds(ids, Video.class);
    }

    public static List<Video> getVideoByQuery(InternalQuery query) {
//        return getEntitiesByQuery(query, Video.class);
        return getEntitiesByQuery2(query, Video.class);
    }

    public static long getVideoCountByQuery(InternalQuery query) {
        return countByQuery(query, Video.class);
    }

    public static boolean deleteVideo(Long id) {
        return deleteEntity(id, Video.class);
    }
}
