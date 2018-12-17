package com.lesports.qmt.sbc.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PublishStatus;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.sbc.adapter.EpisodeVoAdapter;
import com.lesports.qmt.sbc.client.QmtSbcEpisodeInternalApis;
import com.lesports.qmt.sbc.converter.EpisodeVoConverter;
import com.lesports.qmt.sbc.model.Episode;
import com.lesports.qmt.sbc.service.EpisodeWebService;
import com.lesports.qmt.sbc.vo.EpisodeVo;
import com.lesports.qmt.tlive.TextLiveInternalApis;
import com.lesports.qmt.tlive.model.TextLive;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import com.lesports.spring.security.authentication.LeCasAuthenticationToken;
import com.lesports.utils.LeDateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by lufei1 on 2016/11/4.
 */
@Service("episodeWebService")
public class EpisodeWebServiceImpl implements EpisodeWebService {

    private static final Logger LOG = LoggerFactory.getLogger(EpisodeWebServiceImpl.class);

    @Resource
    private EpisodeVoAdapter episodeVoAdapter;
    @Resource
    private EpisodeVoConverter episodeVoConverter;

    @Override
    public Long saveWithId(EpisodeVo entity) {
        if (entity == null) {
            return -1l;
        }
        if (entity.getId() == null) {
            return doInsert(entity);
        } else {
            return doUpdate(entity);
        }
    }

    private Long doInsert(EpisodeVo entity) {
        entity.setCreator(getOperator());
        return QmtSbcEpisodeInternalApis.saveEpisode(entity.toModel());
    }

    private Long doUpdate(EpisodeVo vo) {
        Episode existsEpisode = QmtSbcEpisodeInternalApis.getEpisodeById(vo.getId());
        episodeVoConverter.copyEditableProperties(existsEpisode, vo);
        existsEpisode.setUpdater(getOperator());
        return QmtSbcEpisodeInternalApis.saveEpisode(existsEpisode, true);
    }

    /**
     * 更新图文直播信息
     *
     * @param eid
     */
    private void updateTextLive(long eid) {
        Episode episode = QmtSbcEpisodeInternalApis.getEpisodeById(eid);
        if (CollectionUtils.isNotEmpty(episode.getTextLives())) {
            for (Episode.SimpleTextLive simpleTextLive : episode.getTextLives()) {
                TextLive textLive = TextLiveInternalApis.getTextLiveById(simpleTextLive.getTextLiveId());
                if (textLive == null) {
                    LOG.info("the textLive not exists.eid:{},textLiveId:{}", episode.getId(), simpleTextLive.getTextLiveId());
                }
                textLive.setEid(episode.getId());
                textLive.setMid(episode.getMid());
                TextLiveInternalApis.saveTextLive(textLive);
            }
        }
    }

    @Override
    public EpisodeVo findOne(Long aLong) {
        return null;
    }

    @Override
    public EpisodeVo findOne(Long id, CallerParam caller) {
        Episode episode = QmtSbcEpisodeInternalApis.getEpisodeById(id);
        return episodeVoAdapter.adapterVo(episode, caller);
    }

    @Override
    public boolean delete(Long id) {
        return QmtSbcEpisodeInternalApis.deleteEpisode(id);
    }

    protected void setEntityId(EpisodeVo entity, long id) {
        if (id <= 0) {
            return;
        }
        if (entity.getId() == null) {
            entity.setId(id);
        }
    }

    @Override
    public QmtPage<EpisodeVo> list(Map<String, Object> params, QmtPageParam pageParam, CallerParam caller) {
        long id = params.get("id") != null ? Long.valueOf(params.get("id").toString()) : 0L;
        String name = params.get("name") != null ? params.get("name").toString() : null;
        long mid = params.get("mid") != null ? Long.valueOf(params.get("mid").toString()) : 0L;
        long aid = params.get("aid") != null ? Long.valueOf(params.get("aid").toString()) : 0L;
        String startTime = params.get("startTime") != null ? LeDateUtils.tansYMDHMS2YYYYMMDDHHMMSS(params.get("startTime").toString()) : null;
        String endTime = params.get("endTime") != null ? LeDateUtils.tansYMDHMS2YYYYMMDDHHMMSS(params.get("endTime").toString()) : null;
        String type = params.get("type") != null ? params.get("type").toString() : null;
        InternalQuery query = new InternalQuery();
        if (id > 0) {
            query.addCriteria(InternalCriteria.where("id").is(id));
        }
        if (StringUtils.isNotBlank(name)) {
            query.addCriteria(InternalCriteria.where("name").regex(name));
        }
        if (StringUtils.isNotBlank(type)) {
            query.addCriteria(InternalCriteria.where("type").is(type));
        }
        if (mid > 0) {
            query.addCriteria(InternalCriteria.where("mid").is(mid));
        }
        if (aid > 0) {
            query.addCriteria(InternalCriteria.where("aid").is(aid));
        }
        if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
            query.addCriteria(InternalCriteria.where("start_time").gte(startTime).lte(endTime));
        } else if (StringUtils.isNotBlank(startTime)) {
            query.addCriteria(InternalCriteria.where("start_time").gte(startTime));
        } else if (StringUtils.isNotBlank(endTime)) {
            query.addCriteria(InternalCriteria.where("start_time").lte(endTime));
        }
        query.with(pageParam.toPageable());

        long total = QmtSbcEpisodeInternalApis.countEpisodesByQuery(query);
        if (total <= 0) {
            return new QmtPage(Collections.EMPTY_LIST, pageParam, 0);
        }
        List<Episode> episodes = QmtSbcEpisodeInternalApis.getEpisodesByQuery(query);

        List<EpisodeVo> episodeVos = Lists.transform(episodes, new Function<Episode, EpisodeVo>() {
            @Nullable
            @Override
            public EpisodeVo apply(@Nullable Episode input) {
                return episodeVoAdapter.adapterVo(input, caller);
            }
        });

        return new QmtPage(episodeVos, pageParam, total);
    }

    @Override
    public boolean updateOnlineStatus(long id, PublishStatus publishStatus) {
        Episode episode = QmtSbcEpisodeInternalApis.getEpisodeById(id);
        episode.setOnline(publishStatus == PublishStatus.ONLINE ? true : false);
        episode.setUpdater(getOperator());
        return QmtSbcEpisodeInternalApis.saveEpisode(episode) > 0;
    }

    protected String getOperator() {
        LeCasAuthenticationToken token = (LeCasAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return token.getName();
    }
}
