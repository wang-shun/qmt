package com.lesports.qmt.sbd;

import com.google.common.collect.Lists;
import com.lesports.qmt.sbd.model.*;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import org.apache.commons.collections.CollectionUtils;
import org.apache.thrift.TException;

import java.util.List;

/**
 * User: qiaohongxin
 * Time: 16-12-20 : 下午18:32
 */
public class SbdLiveEventInternalApis extends SbdInternalApis {


    public static long saveLiveEvent(LiveEvent liveEvent) {
        return saveEntity(liveEvent, LiveEvent.class);
    }


    public static boolean deleteLiveEvent(Long id) {
        return deleteEntity(id, LiveEvent.class);
    }


    public static LiveEvent getLiveEventById(Long id) {
        return getEntityById(id, LiveEvent.class);
    }

    public static List<LiveEvent> getLiveEventsByIds(List<Long> ids) {
        return getEntitiesByIds(ids, LiveEvent.class);
    }

    public static List<LiveEvent> getLiveEventsByQuery(InternalQuery params) {
        return getEntitiesByQuery(params, LiveEvent.class);
    }

    public static LiveEvent getLiveEventByNameAndParentId(String name, Long parentId) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(new InternalCriteria("deleted", "is", false));
        query.addCriteria(new InternalCriteria("name", "is", name));
        query.addCriteria(new InternalCriteria("parent_type", "is", parentId));
        List<LiveEvent> liveEvents = getLiveEventsByQuery(query);
        if (CollectionUtils.isNotEmpty(liveEvents)) {
            return liveEvents.get(0);
        }
        return null;
    }

    public static LiveEvent getLiveEventByParentIdAndPartner(Partner partner, Long parentId) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(new InternalCriteria("deleted", "is", false));
        query.addCriteria(new InternalCriteria("parent_type", "is", parentId));
        InternalCriteria partnerCriteria = new InternalCriteria("partners");
        partnerCriteria.elemMatch(new InternalCriteria("_id", "eq", partner.getId()).andOperator(new InternalCriteria("type", "eq", partner.getType())));
        query.addCriteria(partnerCriteria);
        List<LiveEvent> liveEvents = getLiveEventsByQuery(query);
        if (CollectionUtils.isNotEmpty(liveEvents)) {
            return liveEvents.get(0);
        }
        return null;
    }


}
