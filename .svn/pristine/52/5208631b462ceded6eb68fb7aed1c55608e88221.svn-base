package com.lesports.qmt.sbc.thrift;

import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.api.dto.*;
import com.lesports.qmt.sbc.api.service.GetRelatedNewsParam;
import com.lesports.qmt.sbc.api.service.GetRelatedVideosParam;
import com.lesports.qmt.sbc.api.service.TSbcService;
import com.lesports.qmt.sbc.service.*;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.PageUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static me.ellios.hedwig.http.mediatype.ExtendedMediaType.APPLICATION_X_THRIFT;

/**
 * Created by denghui on 2016/12/1.
 */
@Service("thriftSbcService")
@Path("/sbc")
@Produces({APPLICATION_X_THRIFT})
public class TSbcServiceAdapter implements TSbcService.Iface {
    private static final Logger LOG = LoggerFactory.getLogger(TSbcServiceAdapter.class);

    @Resource
    private NewsService newsService;
    @Resource
    private TopicService topicService;
    @Resource
    private TopicItemPackageService topicItemPackageService;
    @Resource
    private ProgramAlbumService programAlbumService;
    @Resource
    private VideoService videoService;

    @Override
    public TVideo getTVideoById(long id, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return videoService.getTVideoById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTVideoById. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }

    @Override
    public TVideo getTVideoByLeecoVid(long leecoVid, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return videoService.getTVideoByLeecoVid(leecoVid, caller);
        } catch (Exception e) {
            LOG.error("fail to getTVideoByLeecoVid. leecoVid:{}, caller : {}", leecoVid, caller, e);
        }
        return null;
    }

    @Override
    public List<TVideo> getTVideoByIds(List<Long> ids, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return videoService.getTVideoByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTVideoById. id:{}, caller : {}", ids, caller, e);
        }
        return null;
    }

    @Override
    public List<Long> getVideoIdsByRelatedId(GetRelatedVideosParam p, PageParam page, CallerParam caller) throws TException {
        try {
            Pageable pageable = PageUtils.convertToPageable(page);
            caller = CallerUtils.getValidCaller(caller);
            return videoService.getVideoIdsByRelatedId(p, pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to getVideoIdsByRelatedId. p:{}, page:{}. caller : {}", p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public long countVideosByRelatedId(GetRelatedVideosParam p, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return videoService.countVideosByRelatedIdParam(p, caller);
        } catch (Exception e) {
            LOG.error("fail to getVideoIdsByRelatedId. p:{}, caller : {}", p, caller, e);
        }
        return 0;
    }

    @Override
    public List<Long> getLatestVideoIds(PageParam page, CallerParam caller) throws TException {
        try {
            Pageable pageable = PageUtils.convertToPageable(page);
            caller = CallerUtils.getValidCaller(caller);
            return videoService.getLatestVideoIds(pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to getLatestVideoIds. page:{}. caller : {}", page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getVideoIdsRelatedWithSomeVideo(long vid, PageParam page, CallerParam caller) throws TException {
        try {
            Pageable pageable = PageUtils.convertToPageable(page);
            caller = CallerUtils.getValidCaller(caller);
            return videoService.getVideoIdsRelatedWithSomeVideo(vid, pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to getVideoIdsRelatedWithSomeVideo. vid:{}, page:{}. caller : {}", vid, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public TVideoIndexInfo getIndexOfVideoInAlbum(long vid, long aid, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return videoService.getIndexOfVideoInAlbum(vid, aid, caller);
        } catch (Exception e) {
            LOG.error("fail to getIndexOfVideoInAlbum. vid:{}, aid:{}, caller : {}", vid, aid, caller, e);
        }
        return null;
    }

    @Override
    public List<Long> getNonPositiveVideosByAid(long aid, PageParam page, CallerParam caller) throws TException {
        try {
            Pageable pageable = PageUtils.convertToPageable(page);
            caller = CallerUtils.getValidCaller(caller);
            return videoService.getNonPositiveVideosByAid(aid, pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to getNonPositiveVideosByAid. aid : {}, page : {}, caller : {}", aid, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public TVideoInfo getTVideoInfoByRelatedIdAndType(GetRelatedVideosParam p, PageParam page, CallerParam caller) throws TException {
        try {
            Pageable pageable = PageUtils.convertToPageable(page);
            caller = CallerUtils.getValidCaller(caller);
            return videoService.getTVideoInfoByRelatedId(p, pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to getTVideoInfoByRelatedIdAndType. p : {}, page : {}, caller : {}", p, page, caller, e);
        }
        return null;
    }

    @Override
    public List<TVideo> getRecordVideosInAlbum(long aid, PageParam page, CallerParam caller) throws TException {
        try {
            Pageable pageable = PageUtils.convertToPageable(page);
            caller = CallerUtils.getValidCaller(caller);
            return videoService.getRecordVideosInAlbum(aid, pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to getRecordVideosInAlbum. aid : {}, page : {}, caller : {}", aid, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public TFeatureInfo getFeatureVideosByYears(long aid, String yearAndMonth, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return videoService.getFeatureVideosByYears(aid, yearAndMonth, caller);
        } catch (Exception e) {
            LOG.error("fail to getFeatureVideosByYears. aid : {}, yearAndMonth : {}, caller : {}", aid, yearAndMonth, caller, e);
        }
        return null;
    }

    @Override
    public List<Long> getAlbumVideosByAid(long aid, PageParam page, CallerParam caller) throws TException {
        try {
            Pageable pageable = PageUtils.convertToPageable(page);
            caller = CallerUtils.getValidCaller(caller);
            return videoService.getAlbumVideosByAid(aid, pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to getAlbumVideosByAid. aid : {}, page : {}, caller : {}", aid, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public TNews getTNewsById(long id, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return newsService.getTNewsById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTNewsById. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }

    @Override
    public List<TNews> getTNewsByIds(List<Long> ids, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return newsService.getTNewsByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTNewsByIds. ids:{}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public TNews getTNewsByVid(long vid, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return newsService.getTNewsByVid(vid, caller);
        } catch (Exception e) {
            LOG.error("fail to getTNewsByVid. vid:{}, caller : {}", vid, caller, e);
        }
        return null;
    }

    @Override
    public List<Long> getNewsIdsByRelatedId(GetRelatedNewsParam p, PageParam page, CallerParam caller) throws TException {
        try {
            Pageable pageable = PageUtils.convertToPageable(page);
            caller = CallerUtils.getValidCaller(caller);
            List<Long> ids = newsService.getRelatedNewsIds(p, pageable, caller);
            LOG.info("getNewsIdsByRelatedId. p:{}, page:{}, caller:{}, ids:{}", p, page, caller, ids);
            return ids;
        } catch (Exception e) {
            LOG.error("fail to getNewsIdsByRelatedParam. p:{}, page:{}. caller : {}", p, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public long countNewsByRelatedId(GetRelatedNewsParam p, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return newsService.countRelatedNews(p, caller);
        } catch (Exception e) {
            LOG.error("fail to countNewsByRelatedId. p:{}, caller:{}", p, caller, e);
        }
        return 0;
    }

    @Override
    public List<Long> getNewsIdsRelatedWithSomeNews(long newsId, List<NewsType> types, PageParam page, CallerParam caller) throws TException {
        try {
            Pageable pageable = PageUtils.convertToPageable(page);
            caller = CallerUtils.getValidCaller(caller);
            List<Long> ids = newsService.getNewsIdsRelatedWithSomeNews(newsId, types, pageable, caller);
            LOG.info("getNewsIdsRelatedWithSomeNews. newsId:{}, types:{}, page, caller:{}, ids:{}", newsId, types, page, caller, ids);
            return ids;
        } catch (Exception e) {
            LOG.error("fail to getNewsIdsRelatedWithSomeNews. newsId:{}, type:{}, page:{}, caller:{}",
                    newsId, types, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Map<String, Long> getNewsIdsNearbySomeNews(long newsId, NewsType type, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return newsService.getNewsIdsNearbySomeNews(newsId, type, caller);
        } catch (Exception e) {
            LOG.error("fail to getNewsIdsNearbySomeNews. newsId:{}, type:{}, caller:{}",
                    newsId, type, caller, e);
        }
        return Maps.newHashMap();
    }

    @Override
    public TTopic getTTopicById(long id, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return topicService.getTTopicById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTTopicById. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }

    @Override
    public List<TTopic> getTTopicsByIds(List<Long> ids, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return topicService.getTTopicsByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTTopicsByIds. ids:{}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public TTopicItemPackage getTTopicItemPackageById(long id, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return topicItemPackageService.getTTopicItemPackageById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTTopicItemPackageById. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }

    @Override
    public List<TTopicItemPackage> getTTopicItemPackagesByIds(List<Long> ids, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return topicItemPackageService.getTTopicItemPackagesByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTTopicItemPackagesByIds. ids:{}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getTopicItemPackageIdsByTopicId(long topicId, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return topicItemPackageService.getTopicItemPackageIdsByTopicId(topicId, caller);
        } catch (Exception e) {
            LOG.error("fail to getTopicItemPackageIdsByTopicId. topicId:{}, caller : {}", topicId, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public TProgramAlbum getTProgramAlbumById(long id, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return programAlbumService.getTProgramAlbumById(id, caller);
        } catch (Exception e) {
            LOG.error("fail to getTProgramAlbumById. id:{}, caller : {}", id, caller, e);
        }
        return null;
    }

    @Override
    public List<TProgramAlbum> getTProgramAlbumsByIds(List<Long> ids, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            return programAlbumService.getTProgramAlbumsByIds(ids, caller);
        } catch (Exception e) {
            LOG.error("fail to getTProgramAlbumsByIds. ids:{}, caller : {}", ids, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Long> getTProgramAlbums(long tagId, PageParam page, CallerParam caller) throws TException {
        try {
            caller = CallerUtils.getValidCaller(caller);
            Pageable pageable = PageUtils.convertToPageable(page);
            return programAlbumService.getTProgramAlbums(tagId, pageable, caller);
        } catch (Exception e) {
            LOG.error("fail to getTProgramAlbums. tagId:{}, page:{}, caller : {}", tagId, page, caller, e);
        }
        return Collections.EMPTY_LIST;
    }

}
