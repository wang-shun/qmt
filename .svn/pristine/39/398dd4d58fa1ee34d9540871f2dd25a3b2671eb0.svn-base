package com.lesports.qmt.sbc.client;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.api.dto.*;
import com.lesports.qmt.sbc.api.service.GetRelatedNewsParam;
import com.lesports.qmt.sbc.api.service.GetRelatedVideosParam;
import com.lesports.qmt.sbc.api.service.TSbcService;
import com.lesports.utils.LeConcurrentUtils;
import com.lesports.utils.math.LeNumberUtils;
import me.ellios.hedwig.rpc.client.ClientBuilder;
import me.ellios.hedwig.rpc.core.ServiceType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * User: ellios
 * Time: 16-10-28 : 下午9:11
 */
public class QmtSbcApis {
    private static final Logger LOG = LoggerFactory.getLogger(QmtSbcApis.class);

    private static TSbcService.Iface sbcService = new ClientBuilder<TSbcService.Iface>()
            .serviceType(ServiceType.THRIFT).serviceFace(TSbcService.Iface.class).build();

    public static TNews getTNewsById(long id, CallerParam caller) {
        try {
            return sbcService.getTNewsById(id, caller);
        } catch (TException e) {
            LOG.error("fail to getTNewsById. id : {}, caller : {}", id, caller, e);
        }
        return null;
    }

    public static TVideo getTVideoById(long id, CallerParam caller) {
        try {
            return sbcService.getTVideoById(id, caller);
        } catch (TException e) {
            LOG.error("fail to getTVideoById. id : {}, caller : {}", id, caller, e);
        }
        return null;
    }

    public static TVideo getTVideoByLeecoVid(long leecoVid, CallerParam caller) {
        try {
            return sbcService.getTVideoByLeecoVid(leecoVid, caller);
        } catch (TException e) {
            LOG.error("fail to getTVideoByLeecoVid. leecoVid : {}, caller : {}", leecoVid, caller, e);
        }
        return null;
    }

    public static List<Long> getVideoIdsByRelatedId(GetRelatedVideosParam p, PageParam page, CallerParam caller) {
        try {
            return sbcService.getVideoIdsByRelatedId(p, page, caller);
        } catch (Exception e) {
            LOG.error("fail to getVideoIdsByRelatedId. p : {}, page : {}. caller : {}", p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static long countVideosByRelatedId(GetRelatedVideosParam p, CallerParam caller) {
        try {
            return sbcService.countVideosByRelatedId(p, caller);
        } catch (Exception e) {
            LOG.error("fail to getVideoIdsByRelatedId. p:{}, caller : {}", p, caller, e);
        }
        return 0L;
    }

    public static List<Long> getLatestVideoIds(PageParam page, CallerParam caller) {
        try {
            return sbcService.getLatestVideoIds(page, caller);
        } catch (TException e) {
            LOG.error("fail to getVideoIdsByRelatedId. page : {}, caller : {}", page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static List<Long> getVideoIdsRelatedWithSomeVideo(long vid, PageParam page, CallerParam caller) {
        try {
            return sbcService.getVideoIdsRelatedWithSomeVideo(vid, page, caller);
        } catch (Exception e) {
            LOG.error("fail to getVideoIdsRelatedWithSomeVideo. vid : {}, page : {}. caller : {}", vid, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static List<TVideo> getTVideosByIds(List<Long> vids, CallerParam caller) {
        try {
            return sbcService.getTVideoByIds(vids, caller);
        } catch (TException e) {
            LOG.error("fail to getTVideosByIds. vids : {}, caller : {}", vids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static TVideoIndexInfo getIndexOfVideoInAlbum(long vid, long aid, CallerParam caller) {
        try {
            return sbcService.getIndexOfVideoInAlbum(vid, aid, caller);
        } catch (TException e) {
            LOG.error("fail to getTVideosByIds. vid : {}, aid : {}, caller : {}", vid, aid, caller, e);
        }
        return null;
    }

    public static List<TVideo> getNonPositiveVideosByAid(long aid, PageParam page, CallerParam caller) {
        try {
            List<Long> ids = sbcService.getNonPositiveVideosByAid(aid, page, caller);
            if(false == CollectionUtils.isEmpty(ids))
                return sbcService.getTVideoByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getNonPositiveVideosByAid. aid : {}, page : {}, caller : {}", aid, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static TVideoInfo getTVideoInfoByRelatedIdAndType(GetRelatedVideosParam p, PageParam page, CallerParam caller) {
        try {
            return sbcService.getTVideoInfoByRelatedIdAndType(p, page, caller);
        } catch (Exception e) {
            LOG.error("fail to getTVideoInfoByRelatedIdAndType. p : {}, page : {}, caller : {}", p, page, caller, e);
        }
        return null;
    }

    public static List<TVideo> getRecordVideosInAlbum(long aid, PageParam page, CallerParam caller) {
        try {
            return sbcService.getRecordVideosInAlbum(aid, page, caller);
        } catch (TException e) {
            LOG.error("fail to getTVideosByIds. aid : {}, page : {}, caller : {}", aid, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static TFeatureInfo getFeatureVideosByYears(long aid, String yearAndMonth, CallerParam caller) {
        try {
            return sbcService.getFeatureVideosByYears(aid, yearAndMonth, caller);
        } catch (Exception e) {
            LOG.error("fail to getFeatureVideosByYears. aid : {}, yearAndMonth : {}, caller : {}", aid, yearAndMonth, caller, e);
        }
        return null;
    }

    public static List<Long> getAlbumVideosByAid(long aid, PageParam page, CallerParam caller) {
        try {
            return sbcService.getAlbumVideosByAid(aid, page, caller);
        } catch (TException e) {
            LOG.error("fail to getAlbumVideosByAid，aid：{}， page : {}, caller : {}",aid, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static List<TNews> getTNewsByIds(List<Long> ids, CallerParam caller) {
        try {
            return sbcService.getTNewsByIds(ids, caller);
        } catch (TException e) {
            LOG.error("fail to getTNewsByIds. ids : {}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static List<TNews> getTNewsByIdsParallel(List<Long> ids, final CallerParam caller) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        return LeConcurrentUtils.parallelApplyforBatch(ids, new Function<List<Long>, List<TNews>>() {
            @Nullable
            @Override
            public List<TNews> apply(List<Long> input) {
                try {
                    return sbcService.getTNewsByIds(input, caller);
                } catch (Exception e) {
                    LOG.error("fail to getTNewsByIdsParallel. ids : {}, caller : {}",
                            input, caller, e);
                }
                return Collections.emptyList();
            }
        });
    }

    /**
     * 获取某条新闻附近的新闻
     *
     * @param newsId
     * @param type
     * @param caller
     * @return
     */
    public static Map<String, TNews> getTNewsNearbySomeNews(long newsId, NewsType type, CallerParam caller) {
        try {
            Map<String, Long> nearbyNewsIdMap = sbcService.getNewsIdsNearbySomeNews(newsId, type, caller);
            if (MapUtils.isEmpty(nearbyNewsIdMap)) {
                return Collections.EMPTY_MAP;
            }
            Map<String, TNews> results = Maps.newHashMap();
            for (Map.Entry<String, Long> entry : nearbyNewsIdMap.entrySet()) {
                TNews news = getTNewsById(LeNumberUtils.toLong(entry.getValue()), caller);
                if (news == null) {
                    continue;
                }
                results.put(entry.getKey(), news);
            }
            return results;
        } catch (Exception e) {
            LOG.error("fail to getTNewsNearbySomeNews. newsId : {}, type : {}, caller : {}", newsId, type, caller, e);
        }
        return Collections.EMPTY_MAP;
    }

    /**
     * 获取视频所对应的新闻
     *
     * @param vid
     * @return
     */
    public static TNews getTNewsByVid(long vid, CallerParam caller) {
        try {
            return sbcService.getTNewsByVid(vid, caller);
        } catch (TException e) {
            LOG.error("fail to getTNewsByVid. vid : {}, caller : {}", vid, caller, e);
        }
        return null;
    }

    public static List<Long> getNewsIdsByRelatedId(GetRelatedNewsParam p, PageParam page, CallerParam caller) {
        try {
            LOG.info("param={},page={},caller={}",p,page,caller);
            return sbcService.getNewsIdsByRelatedId(p, page, caller);
        } catch (TException e) {
            LOG.error("fail to getNewsIdsByRelatedId. p : {}, page:{},caller : {}", p, page, caller, e);
        }
        return Collections.EMPTY_LIST;

    }

    public static long countNewsByRelatedId(GetRelatedNewsParam p, CallerParam caller) {
        try {
            return sbcService.countNewsByRelatedId(p, caller);
        } catch (TException e) {
            LOG.error("fail to countNewsByRelatedId. p : {}, caller : {}", p, caller, e);
        }
        return 0;

    }

    public static List<TNews> getNewsRelatedWithSomeNews(long newsId, List<NewsType> types, PageParam page, CallerParam caller) {
        try {
            List<Long> ids = sbcService.getNewsIdsRelatedWithSomeNews(newsId, types, page, caller);
            if (CollectionUtils.isEmpty(ids)) {
                return Collections.EMPTY_LIST;
            }
            return getTNewsByIds(ids, caller);
        } catch (TException e) {
            LOG.error("fail to getNewssRelatedWithSomeNews. newsId : {}, types : {}, page: {}, caller : {}",
                    newsId, types, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static TProgramAlbum getTProgramAlbumById(long id, CallerParam caller) {
        try {
            return sbcService.getTProgramAlbumById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTProgramAlbumById. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }

    public static List<TProgramAlbum> getTProgramAlbumsByIds(List<Long> ids, CallerParam caller) {
        try {
            return sbcService.getTProgramAlbumsByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTProgramAlbumsByIds. ids:{}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static List<Long> getTProgramAlbums(long tagId, PageParam page, CallerParam caller) {
        try {
            return sbcService.getTProgramAlbums(tagId, page, caller);
        } catch (Exception e) {
            LOG.error("fail to getTProgramAlbums. tagId, page:{},caller:{]", tagId, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static TTopic getTTopicById(long id, CallerParam caller) {
        try {
            return sbcService.getTTopicById(id, caller);
        } catch (TException e) {
            LOG.error("fail to getTTopicById. id : {}, caller : {}", id, caller, e);
        }
        return null;
    }

    public static List<TTopic> getTTopicByIds(List<Long> ids, CallerParam caller) {
        try {
            return sbcService.getTTopicsByIds(ids, caller);
        } catch (TException e) {
            LOG.error("fail to getTTopicsByIds. ids : {}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static TTopicItemPackage getTTopicItemPackageById(long id, CallerParam caller) {
        try {
            return sbcService.getTTopicItemPackageById(id, caller);
        } catch (TException e) {
            LOG.error("fail to getTTopicItemPackageById. id : {}, caller : {}", id, caller, e);
        }
        return null;
    }

    public static List<TTopicItemPackage> getTTopicItemPackageByIds(List<Long> ids, CallerParam caller) {
        try {
            return sbcService.getTTopicItemPackagesByIds(ids, caller);
        } catch (TException e) {
            LOG.error("fail to getTTopicItemPackagesByIds. ids : {}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static List<TTopicItemPackage> getTTopicItemPackageByTopicId(Long topicId, CallerParam caller) {
        try {
            List<Long> ids = sbcService.getTopicItemPackageIdsByTopicId(topicId, caller);
            if (CollectionUtils.isEmpty(ids)) {
                return Collections.EMPTY_LIST;
            }
            return sbcService.getTTopicItemPackagesByIds(ids, caller);
        } catch (TException e) {
            LOG.error("fail to getTTopicItemPackageByTopicId. topicId : {}, caller : {}", topicId, caller, e);
        }
        return Collections.EMPTY_LIST;
    }
}
