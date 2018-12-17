package com.lesports.qmt.sbc.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lesports.LeConstants;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PublishStatus;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.msg.core.ActionType;
import com.lesports.qmt.msg.core.BusinessType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.core.LeMessageBuilder;
import com.lesports.qmt.msg.producer.SwiftMessageApis;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.api.dto.TNews;
import com.lesports.qmt.sbc.api.service.GetRelatedNewsParam;
import com.lesports.qmt.sbc.cache.TNewsCache;
import com.lesports.qmt.sbc.converter.TNewsConverter;
import com.lesports.qmt.sbc.model.News;
import com.lesports.qmt.sbc.model.RelatedItem;
import com.lesports.qmt.sbc.model.Video;
import com.lesports.qmt.sbc.repository.NewsRepository;
import com.lesports.qmt.sbc.service.NewsService;
import com.lesports.qmt.sbc.service.support.AbstractSbcService;
import com.lesports.qmt.sbc.utils.KVCacheUtils;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.PageUtils;
import com.lesports.utils.Utf8KeyCreater;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * Created by ruiyuansheng on 2015/7/2.
 */
@Service("newsService")
public class NewsServiceImpl extends AbstractSbcService<News, Long> implements NewsService {

    protected static final Logger LOG = LoggerFactory.getLogger(NewsServiceImpl.class);
    protected static final Utf8KeyCreater<String> RELATED_NEWS_TOTAL_KEY_CREATER = new Utf8KeyCreater<>("V2_QMT_RELATED_NEWS_TOTAL_TF_");
    protected static final int RELATED_NEWS_TOTAL_CACHE_EXPIRE_TIME = 3600 * 2;

    @Resource
    protected NewsRepository newsRepository;
    @Resource
    protected TNewsConverter newsConverter;
    @Resource
    protected TNewsCache newsCache;

    @Override
    protected QmtOperationType getOpreationType(News entity) {
        if (LeNumberUtils.toLong(entity.getId()) <= 0 || findOne(entity.getId()) == null) {
            return QmtOperationType.CREATE;
        }
        return QmtOperationType.UPDATE;
    }

    @Override
    protected boolean doCreate(News entity) {
        if (entity.getId() == null) {
            entity.setId(LeIdApis.nextId(IdType.NEWS));
        }
        entity.setDeleted(false);
        entity.setOnline(LeNumberUtils.toBoolean(entity.getDraft()) ? PublishStatus.OFFLINE : PublishStatus.ONLINE);
        if (entity.getOnline() == PublishStatus.ONLINE) {
            entity.setPublishAt(entity.getCreateAt());
        }
        addRelatedTags(entity);
        return newsRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(News entity) {
        newsCache.delete(entity.getId());
        List<BusinessType> businessTypes = Lists.newArrayList();
        if (!indexEntity(entity.getId(), IdType.NEWS.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create()
                .setEntityId(entity.getId())
                .setIdType(IdType.NEWS)
                .setBusinessTypes(ActionType.ADD, businessTypes)
                .build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doUpdate(News entity) {
        News existsEntity = doFindExistEntity(entity);
        if (existsEntity.getOnline() == PublishStatus.OFFLINE && entity.getOnline() == PublishStatus.ONLINE) {
            //发布状态改了,更新发布时间
            entity.setPublishAt(entity.getUpdateAt());
        }
        addRelatedTags(entity);
        return newsRepository.save(entity);
    }

    private void addRelatedTags(News entity) {
        // 选择频道、项目、赛事时需要自动打标签
        entity.addRelatedId(entity.getCid());
        entity.addRelatedId(entity.getChannelId());
        entity.addRelatedId(entity.getSubChannelId());
    }

    @Override
    protected boolean doAfterUpdate(News entity) {
        newsCache.delete(entity.getId());
        List<BusinessType> businessTypes = Lists.newArrayList();
        if (!indexEntity(entity.getId(), IdType.NEWS.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create()
                .setEntityId(entity.getId())
                .setIdType(IdType.NEWS)
                .setBusinessTypes(ActionType.UPDATE, businessTypes)
                .build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doDelete(Long id) {
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        Update up = update("deleted", true).set("update_at", now);
        return newsRepository.update(id, up);
    }

    @Override
    protected boolean doAfterDelete(News entity) {
        newsCache.delete(entity.getId());
        List<BusinessType> businessTypes = Lists.newArrayList();
        if (!indexEntity(entity.getId(), IdType.NEWS.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create()
                .setEntityId(entity.getId())
                .setIdType(IdType.NEWS)
                .setBusinessTypes(ActionType.DELETE, businessTypes)
                .build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected News doFindExistEntity(News entity) {
        return newsRepository.findOne(entity.getId());
    }

    @Override
    protected MongoCrudRepository getInnerRepository() {
        return newsRepository;
    }

    @Override
    public boolean saveWithVideo(Video video) {
        if (video == null) {
            return false;
        }
        if (LeNumberUtils.toBoolean(video.getIsClone())) {
            LOG.warn("this video is clone : {}", video.getId());
            return false;
        }
        News news = findOneByVid(video.getId());
        if (news == null) {
            news = new News();
        }
        fillNewsWithVideo(news, video);
        boolean result = save(news, true);
        LOG.info("save news with video : {}, result: {}", video.getId(), result);
        return result;
    }

    @Override
    public TNews getTNewsById(long id, CallerParam caller) {
        TNews tNews = newsCache.findOne(id);
        if (tNews == null) {
            News news = newsRepository.findOne(id);
            if (news == null) {
                LOG.warn("fail to getTNewsById, news no exists. id : {}, caller : {}", id, caller);
                return null;
            }
            if (news.getDeleted()) {
                LOG.warn("fail to getTNewsById, news is deleted. id : {}, caller : {}", id, caller);
                return null;
            }
            if (news.getOnline().equals(PublishStatus.OFFLINE)) {
                LOG.warn("fail to getTNewsById, news is offline. id : {}, caller : {}", id, caller);
                return null;
            }
            tNews = newsConverter.toDto(news);
            if (tNews == null) {
                LOG.warn("fail to getTNewsById, toTVo fail. id : {}, caller : {}", id, caller);
                return null;
            }
            newsCache.save(tNews);
        }
        newsConverter.adaptDto(tNews, caller);
        return tNews;
    }

    @Override
    public List<TNews> getTNewsByIds(List<Long> ids, CallerParam caller) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        List<TNews> newsList = Lists.newArrayListWithExpectedSize(ids.size());
        for (final Long id : ids) {
            TNews tNews = getTNewsById(id, caller);
            if (tNews != null) {
                newsList.add(tNews);
            }
        }
        return newsList;
    }

    @Override
    public TNews getTNewsByVid(long vid, CallerParam caller) {
        Query q = query(where("vid").is(vid));
        q.addCriteria(where("type").is(NewsType.VIDEO));
        q.addCriteria(where("deleted").is(false));
        List<Long> ids = newsRepository.findIdByQuery(q);
        if (CollectionUtils.isNotEmpty(ids)) {
            return getTNewsById(ids.get(0), caller);
        }
        return null;
    }

    @Override
    public List<Long> getRelatedNewsIds(GetRelatedNewsParam p, Pageable pageable, CallerParam caller) {
        if (p == null) {
            return Collections.EMPTY_LIST;
        }
        Query q = query(where("deleted").is(false));
        addRelatedNewsCriteriaToQuery(q, p, caller);
        pageable = getValidPageable(pageable);
        q.with(pageable);
        return newsRepository.findIdByQuery(q);
    }

    @Override
    public long countRelatedNews(GetRelatedNewsParam p, CallerParam caller) {
        if (p == null) {
            return 0;
        }
        String key = "";
        if (CollectionUtils.isNotEmpty(p.getRelatedIds())) {
            key = RELATED_NEWS_TOTAL_KEY_CREATER.textKey(p.getTypes() + "_" + p.getRelatedIds() + "_" + p.getMember() + "_" + p.getStar());
        } else {
            key = RELATED_NEWS_TOTAL_KEY_CREATER.textKey(p.getTypes() + "_" + p.getMember() + "_" + p.getStar());
        }
        long total = 0;
        Object obj = KVCacheUtils.get(key);
        if (obj != null) {
            total = (long) obj;
        } else {
            Query q = query(where("deleted").is(false));
            addRelatedNewsCriteriaToQuery(q, p, caller);
            total = newsRepository.countByQuery(q);
            if (total > 0) {
                KVCacheUtils.set(key, total, RELATED_NEWS_TOTAL_CACHE_EXPIRE_TIME);
            }
        }
        return total;
    }

    private boolean needAddDayCriteria(long callerId) {
        return callerId != LeConstants.LESPORTS_PC_CALLER_CODE && callerId != LeConstants.LESPORTS_MSITE_CALLER_CODE;
    }

    private Pageable getValidPageable(Pageable pageable) {
        pageable = PageUtils.getValidPageable(pageable);
        if (pageable.getSort() == null) {
            //添加默认排序
            pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "publish_at");
        }
        return pageable;
    }

    @Override
    public List<Long> getNewsIdsRelatedWithSomeNews(long newsId, List<NewsType> types, Pageable pageable, CallerParam caller) {
        News news = newsRepository.findOne(newsId);
        if (news == null) {
            LOG.error("fail to getNewsIdsRelatedWithSomeNews. news : {} no exists", news);
            return Collections.EMPTY_LIST;
        }
        int pageSize = pageable.getPageSize();
        pageable = getValidPageable(pageable);
        Criteria typeCriteria = null;
        if (CollectionUtils.isNotEmpty(types)) {
            typeCriteria = where("type").in(types);
        }
        List<Long> newsIds = Lists.newArrayList();
        //人工运营的相关内容优先级高
        if (CollectionUtils.isNotEmpty(news.getRelatedItems())) {
            List<Long> candidateIds = Lists.newArrayList();
            for (RelatedItem relatedItem : news.getRelatedItems()) {
                if (relatedItem.getId() != null && LeIdApis.checkIdTye(relatedItem.getId()) == IdType.NEWS) {
                    candidateIds.add(relatedItem.getId());
                }
            }
            if (CollectionUtils.isNotEmpty(candidateIds)) {
                Query q = query(where("deleted").is(false));
                q.addCriteria(where("online").is(PublishStatus.ONLINE));
                if (typeCriteria != null) {
                    q.addCriteria(typeCriteria);
                }
                addCallerCriteriaToQuery(q, caller.getCallerId());
                addSupportLicenceCriteria(q, caller);
                q.addCriteria(where("_id").in(candidateIds));
                List<Long> ids = newsRepository.findIdByQuery(q);
                if (CollectionUtils.isNotEmpty(ids)) {
                    //按照人工运营的顺序重新排序
                    Collections.sort(ids, (o1, o2) -> candidateIds.indexOf(o1) - candidateIds.indexOf(o2));
                    newsIds.addAll(getPage(ids, pageable));
                }
            }
        }

        //不足再自动调取
        if (CollectionUtils.isEmpty(newsIds) || (newsIds.size() < pageSize + 1)) {
            //按相关id取
            if (CollectionUtils.isNotEmpty(news.getRelatedIds())) {
                //取近3天的
                String threeDayBefore = LeDateUtils.formatYYYYMMDD(LeDateUtils.addDays(new Date(), -3));
                Query q = query(where("deleted").is(false));
                q.addCriteria(where("online").is(PublishStatus.ONLINE));
                if (typeCriteria != null) {
                    q.addCriteria(typeCriteria);
                }
                addCallerCriteriaToQuery(q, caller.getCallerId());
                addSupportLicenceCriteria(q, caller);
                //pc和m站没有天数限制,只限制条数
                if (needAddDayCriteria(caller.getCallerId())) {
//                    q.addCriteria(where("publish_at").gte(threeDayBefore));
                }
                q.addCriteria(where("related_ids").in(news.getRelatedIds()));
                q.with(pageable);
                newsIds.addAll(newsRepository.findIdByQuery(q));
            }
        }
        //去重
        newsIds = Lists.newArrayList(Sets.newLinkedHashSet(newsIds));
        //过滤掉新闻本身
        if (newsIds.contains(newsId)) {
            newsIds.remove(newsId);
        }
        if (newsIds.size() > pageSize) {
            newsIds = newsIds.subList(0, pageSize);
        }
        return newsIds;
    }

    private List<Long> getPage(List<Long> full, Pageable pageable) {
        int fromIndex = pageable.getPageNumber() * pageable.getPageSize();
        if (fromIndex >= full.size()) {
            return Collections.EMPTY_LIST;
        }
        int toIndex = (pageable.getPageNumber() + 1) * pageable.getPageSize();
        if (toIndex >= full.size()) {
            toIndex = full.size();
        }
        return full.subList(fromIndex, toIndex);
    }

    @Override
    public Map<String, Long> getNewsIdsNearbySomeNews(long newsId, NewsType type, CallerParam caller) {
        Map<String, Long> returnMap = Maps.newHashMap();
        News news = newsRepository.findOne(newsId);
        if (news == null) {
            LOG.error("fail to getNewsIdsNearbySomeNews. newsId : {} no exist.", newsId);
            return Collections.EMPTY_MAP;
        }
        Long prev = findPrevNewsIdNearbySomeNews(news, type, caller);
        if (prev != null) {
            returnMap.put("prev", prev);
        }
        Long next = findNextNewsIdNearbySomeNews(news, type, caller);
        if (next != null) {
            returnMap.put("next", next);
        }
        return returnMap;
    }

    private void fillNewsWithVideo(News news, Video video) {
        news.setType(NewsType.VIDEO);
        news.setVid(video.getId());
        news.setLeecoVid(video.getLeecoVid());
        news.setPay(video.getIsPay());
        news.setPlatforms(video.getPlayPlatforms());
        news.setPayPlatforms(video.getPayPlatforms());
        news.setPublishAt(video.getCreateAt());
        news.setCreator(video.getCreator());
        news.setName(video.getTitle());
        news.setDesc(video.getDesc());
        news.setShareDesc(video.getShareDesc());
        news.setShareName(video.getTitle());
        news.setShortName(video.getShortTitle());
        news.setChannelId(video.getChannel());
        news.setSubChannelId(video.getSubChannel());
        news.setCid(video.getCid());
        news.setAuthor(video.getVideoAuthor());
        news.setSource(video.getSource());
        news.setStarLevel(video.getStarLevel());
        news.setAllowComment(video.getCommentFlag());
        news.setStatements(video.getStatements());
        news.setContent(video.getDesc());
        news.setRelatedIds(video.getRelatedIds());
        news.setSupportLicences(video.getSupportLicences());
        news.setDrmFlag(video.getDrmFlag());
        news.setOnline(LeNumberUtils.toBoolean(video.getDeleted()) ? PublishStatus.ONLINE : PublishStatus.OFFLINE);
    }

    private News findOneByVid(Long vid) {
        Query q = query(where("vid").is(vid));
        q.addCriteria(where("type").is(NewsType.VIDEO));
        q.addCriteria(where("deleted").is(false));
        return newsRepository.findOneByQuery(q);
    }

    /**
     * 获取该新闻id同一类型的下一个新闻
     *
     * @param news
     * @param type
     * @param caller
     * @return
     */
    private Long findNextNewsIdNearbySomeNews(News news, NewsType type, CallerParam caller) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("type").is(type));
        q.addCriteria(where("online").is(PublishStatus.ONLINE));
        q.addCriteria(where("publish_at").gt(news.getPublishAt()));
        Criteria criteria = getCallerCriteria4News(caller.getCallerId());
        if (criteria != null) {
            q.addCriteria(criteria);
        }
        addSupportLicenceCriteria(q, caller);
        Pageable pageable = new PageRequest(0, 1, new Sort(Sort.Direction.ASC, "publish_at"));
        q.with(pageable);
        return newsRepository.findOneIdByQuery(q);
    }

    /**
     * 获取该新闻id同一类型的下一个新闻
     *
     * @param news
     * @param type
     * @param caller
     * @return
     */
    private Long findPrevNewsIdNearbySomeNews(News news, NewsType type, CallerParam caller) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("type").is(type));
        q.addCriteria(where("online").is(PublishStatus.ONLINE));
        q.addCriteria(where("publish_at").lt(news.getPublishAt()));
        Criteria criteria = getCallerCriteria4News(caller.getCallerId());
        if (criteria != null) {
            q.addCriteria(criteria);
        }
        addSupportLicenceCriteria(q, caller);
        Pageable pageable = new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "publish_at"));
        q.with(pageable);
        return newsRepository.findOneIdByQuery(q);
    }

    protected void addCallerCriteriaToQuery(Query q, long callerId) {
        Criteria criteria = getCallerCriteria4News(callerId);
        if (criteria != null) {
            q.addCriteria(criteria);
        }
    }
}
