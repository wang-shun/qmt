package com.lesports.qmt.msg.service;

import com.google.common.collect.Lists;
import com.lesports.id.client.LeIdApis;
import com.lesports.qmt.msg.cache.CdnCacheApis;
import com.lesports.qmt.msg.constant.Constants;
import com.lesports.utils.Utf8KeyCreater;
import com.lesports.utils.http.RestTemplateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.List;

/**
 * lesports-projects.
 *
 * @author: pangchuanxiao
 * @since: 2015/7/10
 */
@Service
public class MatchService extends AbstractService {
    private static final Logger LOG = LoggerFactory.getLogger(MatchService.class);
    private final static List<MessageFormat> MATCH_PAGE_URIS = Lists.newArrayList(new MessageFormat("http://sports.letv.com/match/{0}.html"), new MessageFormat("http://www.lesports.com/match/{0}.html"),
            new MessageFormat("http://sports.le.com/match/{0}.html"), new MessageFormat("http://m.lesports.com/match/{0}.html"),
            new MessageFormat("http://m.sports.letv.com/match/{0}.html"), new MessageFormat("http://m.sports.le.com/match/{0}.html"));

    private static final RestTemplate TEMPLATE = RestTemplateFactory.getTemplate();
    private static final List<Utf8KeyCreater<Long>> KEY_CREATE_LIST = Lists.newArrayList(new Utf8KeyCreater<Long>("V2_MATCH_SQUADS_"), new Utf8KeyCreater<Long>("V2_MATCH_COMPETITOR_STATS_"));


    public boolean deleteMatchApiCache(long matchId) {
        return deleteNgxApiCache(matchId);
    }

    public boolean deleteSmsRealtimeWebMatch(long id) {
        return deleteSmsRealtimeWebCache(KEY_CREATE_LIST, id);
    }

    public boolean deleteSisMatch(long matchId) {
        long oldId = LeIdApis.getOldId(matchId);
        return deleteMemCacheAndCdnCache(Constants.SIS_WEB_HOST, Constants.SIS_WEB_MATCH_URI, oldId, true);
    }

    public boolean deleteMatchPage(long matchId) {
        boolean result = true;
        for (MessageFormat messageFormat : MATCH_PAGE_URIS) {
            String url = messageFormat.format(new Object[]{String.valueOf(matchId)});
            result = CdnCacheApis.delete(url) & result;
        }
        return deleteNgxPageCache(matchId) & result;
    }
}
