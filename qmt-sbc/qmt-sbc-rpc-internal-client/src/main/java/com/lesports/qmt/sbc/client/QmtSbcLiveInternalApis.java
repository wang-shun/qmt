package com.lesports.qmt.sbc.client;

import com.lesports.qmt.sbc.api.service.SaveResult;
import com.lesports.qmt.sbc.model.Live;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * User: ellios
 * Time: 16-10-28 : 下午9:14
 */
public class QmtSbcLiveInternalApis extends QmtSbcInternalApis {

    public static List<Live> getLiveByIds(List<Long> liveIds) {
        return getEntitiesByIds(liveIds, Live.class);
    }

    public static SaveResult saveLive(Live live) {
        return saveEntityWithResult(live, Live.class, false);
    }

    public static Live getLiveById(Long liveId) {
        return getEntityById(liveId, Live.class);
    }

    public static boolean deleteLive(Long liveId) {
        return deleteEntity(liveId, Live.class);
    }

    public static Live getLiveByPlaybackId(Long playbackId) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(new InternalCriteria("playback_id", "is", playbackId));
        List<Live> lives = getEntitiesByQuery(query, Live.class);
        if (CollectionUtils.isNotEmpty(lives)) {
            return lives.get(0);
        }
        return null;
    }
}
