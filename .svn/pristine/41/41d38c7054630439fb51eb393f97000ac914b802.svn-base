package com.lesports.qmt.op.web.api.core.service.impl;

import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.op.web.api.core.creater.CompetitionVoCreater;
import com.lesports.qmt.op.web.api.core.service.CompetitionService;
import com.lesports.qmt.op.web.api.core.util.Constants;
import com.lesports.qmt.op.web.api.core.vo.baidu.CompetitionVo;
import com.lesports.qmt.op.web.api.core.vo.baidu.*;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.service.GetEpisodesOfSeasonByMetaEntryIdParam;
import com.lesports.qmt.sbc.client.QmtSbcEpisodeApis;
import com.lesports.qmt.sbd.api.dto.TCompetition;
import com.lesports.qmt.sbd.api.dto.TCompetitionSeason;
import com.lesports.qmt.sbd.client.SbdCompetitionApis;
import com.lesports.qmt.sbd.client.SbdCompetitonSeasonApis;
import com.lesports.utils.LeDateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by lufei1 on 2015/8/20.
 */
@Service("competitionService")
public class CompetitionServiceImpl implements CompetitionService {

    private static final Logger LOG = LoggerFactory.getLogger(CompetitionServiceImpl.class);

    @Resource
    private CompetitionVoCreater competitionVoCreater;

    @Override
    public CompetitionVo getCompetitionVo(long id,CallerParam caller) {
        CompetitionVo competitionVo = new CompetitionVo();
        TCompetition competition = SbdCompetitionApis.getTCompetitionById(id, caller);
        if (competition == null) {
            LOG.warn("competition is null,id:{}", id);
            return competitionVo;
        }
        TCompetitionSeason competitionSeason = SbdCompetitonSeasonApis.getLatestTCompetitionSeasonsByCid(competition.getId(), caller);
        if (competitionSeason == null) {
            LOG.warn("competitionSeason is null,id:{}", id);
            return competitionVo;
        }

        PageParam pageParam = new PageParam();
        int page = 1;
        pageParam.setCount(20);
        List<VideoVo> videoVos = Lists.newArrayList();
        while (true) {
            pageParam.setPage(page);
            GetEpisodesOfSeasonByMetaEntryIdParam p = new GetEpisodesOfSeasonByMetaEntryIdParam();
            p.setCsid(0);
            List<TComboEpisode> tComboEpisodeList = QmtSbcEpisodeApis.getCurrentEpisodesByCompetitorId(id, p, pageParam, caller);
            if (tComboEpisodeList.isEmpty()) {
                break;
            }
            for (TComboEpisode comboEpisode : tComboEpisodeList) {
                competitionVoCreater.createVideoVO(videoVos, comboEpisode, competitionSeason);
            }
            page++;
        }
        CompetitionData competitionData = new CompetitionData();

        List<Serialinfo> serialinfos = Lists.newArrayList();
        Serialinfo serialinfo = competitionVoCreater.createSerialinfo(competition, competitionSeason);
        serialinfo.setVideo(videoVos);
        serialinfos.add(serialinfo);
        competitionData.setSerialinfo(serialinfos);

        Site site = competitionVoCreater.createSite(id);
        site.setData(competitionData);
        competitionVo.setUrl(site);

        return competitionVo;
    }



    @Override
    public SiteMapVo getSiteMapVo(long id){
        SiteMapVo siteMapVo = new SiteMapVo();
        List<SiteMap> siteMaps = Lists.newArrayList();
        for(int i = 1;i < 11;i++) {
            SiteMap siteMap = new SiteMap();
            String loc = Constants.BAIDU_SITE_MAP_URL + id + "?caller=2002&amp;currentPage="+ i;
            siteMap.setLoc(loc);
            siteMap.setLastmod(LeDateUtils.formatYYYY_MM_DD(new Date()));
            siteMaps.add(siteMap);
        }
        siteMapVo.setSitemap(siteMaps);
        return  siteMapVo;
    }

    @Override
    public CompetitionVo getCompetitionVo(long id,int currentPage,int perPage,CallerParam caller) {
        CompetitionVo competitionVo = new CompetitionVo();
        TCompetition competition = SbdCompetitionApis.getTCompetitionById(id,caller);
        if (competition == null) {
            LOG.warn("competition is null,id:{}", id);
            return competitionVo;
        }
        TCompetitionSeason competitionSeason = SbdCompetitonSeasonApis.getLatestTCompetitionSeasonsByCid(competition.getId(),caller);
        if (competitionSeason == null) {
            LOG.warn("competitionSeason is null,id:{}", id);
            return competitionVo;
        }

        PageParam pageParam = new PageParam();
        int page = (currentPage - 1) * perPage + 1;
        pageParam.setCount(20);
        List<VideoVo> videoVos = Lists.newArrayList();
        while (page <= currentPage * perPage) {
            pageParam.setPage(page);
            GetEpisodesOfSeasonByMetaEntryIdParam p = new GetEpisodesOfSeasonByMetaEntryIdParam();
            p.setCsid(0);
            List<TComboEpisode> tComboEpisodeList = QmtSbcEpisodeApis.getCurrentEpisodesByCompetitorId(id,p,pageParam,caller);
            if (tComboEpisodeList.isEmpty()) {
                break;
            }
            for (TComboEpisode comboEpisode : tComboEpisodeList) {
                competitionVoCreater.createVideoVO(videoVos, comboEpisode, competitionSeason);
            }
            page++;
        }

        CompetitionData competitionData = new CompetitionData();

        List<Serialinfo> serialinfos = Lists.newArrayList();
        Serialinfo serialinfo = competitionVoCreater.createSerialinfo(competition, competitionSeason);
        serialinfo.setVideo(videoVos);
        serialinfos.add(serialinfo);
        competitionData.setSerialinfo(serialinfos);

        Site site = competitionVoCreater.createSite(id);
        site.setData(competitionData);
        competitionVo.setUrl(site);

        return competitionVo;
    }
}
