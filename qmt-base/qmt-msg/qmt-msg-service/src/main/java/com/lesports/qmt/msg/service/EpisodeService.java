package com.lesports.qmt.msg.service;

import com.google.common.collect.Lists;
import com.lesports.qmt.sbc.client.QmtSbcEpisodeInternalApis;
import com.lesports.utils.Utf8KeyCreater;
import com.lesports.utils.http.RestTemplateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * lesports-projects.
 *
 * @author: pangchuanxiao
 * @since: 2015/7/10
 */
@Service("episodeService")
public class EpisodeService extends AbstractService  {
    private static final Logger LOG = LoggerFactory.getLogger(EpisodeService.class);
    private static final RestTemplate TEMPLATE = RestTemplateFactory.getTemplate();
    private static final List<Utf8KeyCreater<Long>> KEY_CREATER_LIST = Lists.newArrayList(new Utf8KeyCreater<Long>("V2_HALL_EPISODE_"),
            new Utf8KeyCreater<Long>("V2_HALL_EPISODE_APP_"),new Utf8KeyCreater<Long>("V2_POLLING_EPISODE_"),
            new Utf8KeyCreater<Long>("V2_POLLING_EPISODE_APP_"), new Utf8KeyCreater<Long>("V2_DETAIL_EPISODE_"),new Utf8KeyCreater<Long>("V2_DETAIL_EPISODE_APP_"));

    //根据matchId同步创建节目
    public boolean syncEpisode(long matchId) {
        try {
            //根据matchid同步episode信息
            List<Long> episodeIds = QmtSbcEpisodeInternalApis.createEpisodes(matchId);
            LOG.info("create episodes : {} for match : {}.", episodeIds, matchId);
            return true;
        } catch (Exception e) {
            LOG.error("{}", e.getMessage(), e);
        }
        return false;
    }

    public boolean deleteSmsRealtimeWebEpisodeCache(long id) {
        return deleteSmsRealtimeWebCache(KEY_CREATER_LIST, id);
    }

    public boolean deleteEpisodeApiCache(long id) {

        boolean resultRedis = deleteNgxApiCache(id);
        if (!resultRedis) {
            LOG.error("fail to delete id cache key relation : {} in redis.", id);
        }
        return resultRedis;
    }

}
