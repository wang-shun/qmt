package com.lesports.qmt.sbd.client;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.sbd.api.dto.TTopList;
import com.lesports.qmt.sbd.api.service.GetSeasonTopListsParam;
import com.lesports.utils.PageUtils;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by zhonglin on 2016/12/6.
 */
public class SbdTopListApis   extends SbdApis {

    public static TTopList getTTopListById(long id, CallerParam caller) {
        try {
            return sbdService.getTTopListById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTTopListById. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }


    public static List<TTopList> getTTopListsByIds(List<Long> ids, CallerParam caller) {
        try {
            return sbdService.getTTopListsByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTTopListsByIds. ids:{}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }


    public static List<TTopList> getSeasonTopLists(GetSeasonTopListsParam p, PageParam page, CallerParam caller) {
        try {
            List<Long> ids = sbdService.getSeasonTopListIds(p, page, caller);
            if (CollectionUtils.isEmpty(ids)) {
                return Collections.EMPTY_LIST;
            }
            return getTTopListsByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getSeasonTopListIds. p : {}, page : {}, caller : {}", p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * 获取某赛事最新赛季榜单,这里只取一个，忽略掉有多个的情况
     * 如果确实有,就用 getSeasonTopLists 自己处理吧
     *
     * @param cid
     * @param type   榜单类型
     * @param group  分组类型
     * @param caller
     * @return
     */
    public static TTopList getLatestSeasonTopList(long cid, long type, long group, CallerParam caller) {
        try {
            GetSeasonTopListsParam p = new GetSeasonTopListsParam();
            p.setCid(cid);
            if (type > 0) {
                p.setType(type);
            }
            if (type > 0) {
                p.setType(type);
            }
            List<Long> ids = sbdService.getSeasonTopListIds(p, PageUtils.createPageParam(0, 1), caller);
            if (CollectionUtils.isEmpty(ids)) {
                return null;
            }
            return getTTopListById(ids.get(0), caller);
        } catch (Exception e) {
            LOG.error("fail to getLatestSeasonTopList. cid : {}, type : {}, group : {}, caller : {}",
                    cid, type, group, caller, e);
        }
        return null;
    }

    public static TTopList getSomeTypeTopListOfSeason(long cid, long csid, long type, CallerParam caller) {
        try {
            GetSeasonTopListsParam p = new GetSeasonTopListsParam();
            p.setCid(cid);
            p.setCsid(csid);
            p.setType(type);
            List<Long> ids = sbdService.getSeasonTopListIds(p, PageUtils.createPageParam(0, 1), caller);
            if (CollectionUtils.isEmpty(ids)) {
                return null;
            }
            return getTTopListById(ids.get(0), caller);
        } catch (Exception e) {
            LOG.error("fail to getSomeTypeTopListOfSeason. cid : {}, csid : {}, type : {}, caller : {}",
                    cid, csid, type, caller, e);
        }
        return null;
    }

    public static List<TTopList> getCompetitorTTopLists(GetSeasonTopListsParam p, PageParam page, CallerParam caller) {
        try {
            return sbdService.getCompetitorTTopLists(p, page, caller);
        } catch (Exception e) {
            LOG.error("fail to getTDetailMatchesByPid.", e);
            throw new RuntimeException("fail to getTDetailMatchesByPid.");
        }
    }
}
