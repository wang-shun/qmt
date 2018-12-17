package com.lesports.qmt.sbc.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.lesports.api.common.LiveShowStatus;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.sbc.api.common.EpisodeType;
import com.lesports.qmt.sbc.client.QmtSbcEpisodeInternalApis;
import com.lesports.qmt.sbc.client.QmtSbcProgramAlbumInternalApis;
import com.lesports.qmt.sbc.client.QmtSbcVideoInternalApis;
import com.lesports.qmt.sbc.converter.PeriodVoConverter;
import com.lesports.qmt.sbc.converter.ProgramAlbumVoConverter;
import com.lesports.qmt.sbc.model.Episode;
import com.lesports.qmt.sbc.model.ProgramAlbum;
import com.lesports.qmt.sbc.model.Video;
import com.lesports.qmt.sbc.service.ProgramAlbumWebService;
import com.lesports.qmt.sbc.service.support.AbstractSbcWebService;
import com.lesports.qmt.sbc.vo.ProgramAlbumPeriodVo;
import com.lesports.qmt.sbc.vo.ProgramAlbumVo;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.lesports.qmt.sbc.client.QmtSbcProgramAlbumInternalApis.*;

/**
 * Created by denghui on 2016/11/15.
 */
@Service("programAlbumWebService")
public class ProgramAlbumWebServiceImpl extends AbstractSbcWebService implements ProgramAlbumWebService {

    @Resource
    private PeriodVoConverter periodVoConverter;
    @Resource
    private ProgramAlbumVoConverter programAlbumVoConverter;

    private static final Logger LOG = LoggerFactory.getLogger(ProgramAlbumWebServiceImpl.class);
    private static final Function<ProgramAlbum, ProgramAlbumVo> ALBUM_VO_FUNCTION = new Function<ProgramAlbum, ProgramAlbumVo>() {
        @Nullable
        @Override
        public ProgramAlbumVo apply(@Nullable ProgramAlbum input) {
            if (input == null) {
                return null;
            }
            ProgramAlbumVo vo = new ProgramAlbumVo(input);
            if (input.getLatestPeriodId() != null) {
                Episode period = QmtSbcEpisodeInternalApis.getEpisodeById(input.getLatestPeriodId());
                if (period != null) {
                    vo.setLatestPeriod(period.getPeriods());
                    vo.setLatestPeriodName(period.getName());
                }
            }
            if (input.getFirstPeriodId() != null) {
                Episode episode = QmtSbcEpisodeInternalApis.getEpisodeById(input.getFirstPeriodId());
                if (episode != null) {
                    vo.setStartTime(episode.getStartTime());
                }
            }

            return vo;
        }
    };
    private static final Function<Episode, ProgramAlbumPeriodVo> PERIOD_VO_FUNCTION = new Function<Episode, ProgramAlbumPeriodVo>() {
        @Nullable
        @Override
        public ProgramAlbumPeriodVo apply(@Nullable Episode input) {
            if (input == null) {
                return null;
            }
            ProgramAlbumPeriodVo periodVo = new ProgramAlbumPeriodVo();
            periodVo.setId(input.getId());
            periodVo.setName(input.getName());
            periodVo.setPeriod(input.getPeriods());
            periodVo.setStartTime(input.getStartTime());
            periodVo.setChannelId(input.getChannelId());
            periodVo.setSubChannelId(input.getSubChannelId());
            periodVo.setCid(input.getCid());
            Video video = QmtSbcVideoInternalApis.getVideoById(input.getRecordId());
            if (video != null) {
                ProgramAlbumPeriodVo.SimpleVideoVo simpleVideoVo = new ProgramAlbumPeriodVo.SimpleVideoVo();
                simpleVideoVo.setId(video.getId());
                simpleVideoVo.setName(video.getTitle());
                periodVo.setVideo(simpleVideoVo);
            }
            periodVo.setOnline(input.getOnline());
            return periodVo;
        }
    };

    @Override
    public Long saveWithId(ProgramAlbumVo entity) {
        if (entity.getId() == null) {
            return doInsertProgramAlbum(entity);
        } else {
            return doUpdateProgramAlbum(entity);
        }
    }

    private Long doInsertProgramAlbum(ProgramAlbumVo entity) {
        entity.setCreator(getOperator());
        long id = saveProgramAlbum(entity.toModel());
        entity.setId(id);
        return id;
    }

    private Long doUpdateProgramAlbum(ProgramAlbumVo entity) {
        ProgramAlbum existsAlbum = getProgramAlbumById(entity.getId());
        programAlbumVoConverter.copyEditableProperties(existsAlbum, entity);
        existsAlbum.setUpdater(getOperator());
        long id = saveProgramAlbum(existsAlbum, true);
        entity.setId(id);
        return id;
    }

    @Override
    public ProgramAlbumVo findOne(Long id) {
        ProgramAlbum programAlbum = getProgramAlbumById(id);
        return programAlbum == null ? null : new ProgramAlbumVo(programAlbum);
    }

    @Override
    public boolean delete(Long id) {
        return deleteProgramAlbum(id);
    }

    @Override
    public QmtPage<ProgramAlbumVo> list(Map<String, Object> params, QmtPageParam pageParam) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(InternalCriteria.where("deleted").is(false));
        addParamsCriteriaToQuery(query, params, pageParam);
        //是否推荐
        Boolean recommend = MapUtils.getBoolean(params, "recommend", null);
        if (LeNumberUtils.toBoolean(recommend)) {
            query.addCriteria(InternalCriteria.where("recommend").is(true));
        }
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "recommend"), new Sort.Order(Sort.Direction.ASC, "recommend_order"));
        Pageable pageable = new PageRequest(query.getPageable().getPageNumber(), query.getPageable().getPageSize(), sort);
        query.with(pageable);

        long total = countProgramAlbumByQuery(query);
        if (total <= 0) {
            return new QmtPage(Collections.EMPTY_LIST, pageParam, 0);
        }
        List<ProgramAlbum> programAlbums = getProgramAlbumsByQuery(query);
        return new QmtPage<>(Lists.transform(programAlbums, ALBUM_VO_FUNCTION), pageParam, total);
    }

    @Override
    public QmtPage<ProgramAlbumPeriodVo> listPeriods(long aid, QmtPageParam pageParam) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(InternalCriteria.where("deleted").is(false));
        query.addCriteria(InternalCriteria.where("aid").is(aid));
        query.with(pageParam.toPageable(new Sort(Sort.Direction.DESC, "start_time")));

        long total = QmtSbcEpisodeInternalApis.countEpisodesByQuery(query);
        if (total <= 0) {
            return new QmtPage(Collections.EMPTY_LIST, pageParam, 0);
        }
        List<Episode> periods = QmtSbcEpisodeInternalApis.getEpisodesByQuery(query);
        return new QmtPage<>(Lists.transform(periods, PERIOD_VO_FUNCTION), pageParam, total);
    }

    @Override
    public ProgramAlbumPeriodVo getPeriodById(long eid) {
        List<Episode> episodes = QmtSbcEpisodeInternalApis.getEpisodesByIds(Lists.newArrayList(eid));
        if (CollectionUtils.isEmpty(episodes)) {
            LOG.error("period {} not found", eid);
            return null;
        }
        return Lists.transform(episodes, PERIOD_VO_FUNCTION).get(0);
    }

    @Override
    public Long savePeriod(long aid, ProgramAlbumPeriodVo vo) {
        if (vo.getId() == null) {
            return doInsertPeriod(aid, vo);
        } else {
            return doUpdatePeriod(aid, vo);
        }
    }

    @Override
    public QmtPage<ProgramAlbumPeriodVo.SimpleVideoVo> listVideos(long aid, QmtPageParam pageParam) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(InternalCriteria.where("deleted").is(false));
        query.addCriteria(InternalCriteria.where("online").is(true));
        query.addCriteria(InternalCriteria.where("aid").is(aid));
        query.with(pageParam.toPageable(new Sort(Sort.Direction.DESC, "start_time")));

        long total = QmtSbcEpisodeInternalApis.countEpisodesByQuery(query);
        if (total <= 0) {
            return new QmtPage(Collections.EMPTY_LIST, pageParam, 0);
        }
        List<ProgramAlbumPeriodVo.SimpleVideoVo> result = Lists.newArrayList();
        List<Episode> periods = QmtSbcEpisodeInternalApis.getEpisodesByQuery(query);
        for (Episode period : periods) {
            Video video = QmtSbcVideoInternalApis.getVideoById(period.getRecordId());
            if (video != null) {
                ProgramAlbumPeriodVo.SimpleVideoVo simpleVideoVo = new ProgramAlbumPeriodVo.SimpleVideoVo();
                simpleVideoVo.setId(video.getId());
                simpleVideoVo.setName(video.getTitle());
                result.add(simpleVideoVo);
            }
        }
        return new QmtPage<>(result, pageParam, total);
    }

    @Override
    public boolean resetRecommendOrder(List<Long> ids) {
        int order = 1;
        for (Long id : ids) {
            ProgramAlbum programAlbum = QmtSbcProgramAlbumInternalApis.getProgramAlbumById(id);
            if (programAlbum != null) {
                if (LeNumberUtils.toBoolean(programAlbum.getRecommend())) {
                    programAlbum.setRecommendOrder(order++);
                    boolean result = QmtSbcProgramAlbumInternalApis.saveProgramAlbum(programAlbum) > 0;
                    if (!result) {
                        LOG.error("reset recommend order for program album failed, id:{}", id);
                        return false;
                    }
                } else {
                    LOG.warn("program album is not recommended, id:{}", id);
                }
            } else {
                LOG.warn("not found program album, id:{}", id);
            }
        }
        return true;
    }

    private Long doInsertPeriod(long aid, ProgramAlbumPeriodVo periodVo) {
        periodVo.setAid(aid);
        Episode episode = new Episode();
        episode.setOnline(true);
        episode.setStatus(LiveShowStatus.NO_LIVE);
        episode.setOpStatus(LiveShowStatus.NO_LIVE);
        episode.setCreator(getOperator());
        fillEpisode(episode, periodVo);
        long id = QmtSbcEpisodeInternalApis.saveEpisode(episode);
        periodVo.setId(id);
        resetPeriodsInProgram(aid);
        return id;
    }

    private Long doUpdatePeriod(long aid, ProgramAlbumPeriodVo periodVo) {
        periodVo.setAid(aid);
        Episode existsPeriod = QmtSbcEpisodeInternalApis.getEpisodeById(periodVo.getId());
        fillEpisode(existsPeriod, periodVo);
        existsPeriod = periodVoConverter.copyEditableProperties(existsPeriod, periodVo);
        existsPeriod.setUpdater(getOperator());
        long id = QmtSbcEpisodeInternalApis.saveEpisode(existsPeriod, true);
        resetPeriodsInProgram(aid);
        return id;
    }

    private void fillEpisode(Episode episode, ProgramAlbumPeriodVo periodVo) {
        episode.setType(EpisodeType.PROGRAM);
        episode.setAid(periodVo.getAid());
        episode.setChannelId(periodVo.getChannelId());
        episode.setSubChannelId(periodVo.getSubChannelId());
        episode.setName(periodVo.getName());
        episode.setPeriods(periodVo.getPeriod());
        episode.setCid(periodVo.getCid());
        episode.setStartTime(periodVo.getStartTime());
        episode.setRecordId(periodVo.getVideo() == null ? null : periodVo.getVideo().getId());
    }

    private static boolean resetPeriodsInProgram(long aid) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(InternalCriteria.where("deleted").is(false));
        query.addCriteria(InternalCriteria.where("aid").is(aid));
        query.with(new Sort(Sort.Direction.DESC, "start_time"));
        List<Long> ids = QmtSbcEpisodeInternalApis.getEpisodeIdsByQuery(query);
        if (CollectionUtils.isNotEmpty(ids)) {
            ProgramAlbum programAlbum = getProgramAlbumById(aid);
            //latest period
            Episode period = QmtSbcEpisodeInternalApis.getEpisodeById(ids.get(0));
            programAlbum.setLatestPeriodId(period.getId());
            //first period
            programAlbum.setFirstPeriodId(ids.get(ids.size() - 1));
            LOG.info("resetPeriodsInProgram, program:{}", programAlbum);
            return saveProgramAlbum(programAlbum) > 0;
        }
        return true;
    }

}
