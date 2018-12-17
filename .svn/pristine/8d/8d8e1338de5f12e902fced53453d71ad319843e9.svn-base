package com.lesports.qmt.msg.context;

import com.lesports.id.api.IdType;
import com.lesports.qmt.msg.handler.IMessageHandler;
import com.lesports.qmt.msg.handler.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhangdeqiang on 2016/11/10.
 */
@Component
public class HandlerContext implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(HandlerContext.class);
    private static Map<Integer, IMessageHandler> strategyMap = new ConcurrentHashMap<>();

    @Resource
    private DefaultMessageHandler defaultMessageHandler;
    @Resource
    private ActionMessageHandler actionMessageHandler;
    @Resource
    private CompetitionMessageHandler competitionMessageHandler;
    @Resource
    private CompetitionSeasonHandler competitionSeasonHandler;
    @Resource
    private EpisodeMessageHandler episodeMessageHandler;
    @Resource
    private MatchMessageHandler matchMessageHandler;
    @Resource
    private NewsMessageHandler newsMessageHandler;
    @Resource
    private ScopeTopListHandler scopeTopListHandler;
    @Resource
    private TeamSeasonMessageHandler teamSeasonMessageHandler;
    @Resource
    private TextLiveHandler textLiveHandler;
    @Resource
    private MmsVideoMessageHandler mmsVideoMessageHandler;

    @Override
    public void run(String... strings) throws Exception {
        this.initStrategyMap(); //初始化
        LOG.info("init strategy map ok");
    }

    private void initStrategyMap() {
        strategyMap.put(IdType.ACTION.getValue(), actionMessageHandler);
        strategyMap.put(IdType.COMPETITION.getValue(), competitionMessageHandler);
        strategyMap.put(IdType.COMPETITION_SEASON.getValue(), competitionSeasonHandler);
        strategyMap.put(IdType.EPISODE.getValue(), episodeMessageHandler);
        strategyMap.put(IdType.MATCH.getValue(), matchMessageHandler);
        strategyMap.put(IdType.NEWS.getValue(), newsMessageHandler);
        strategyMap.put(IdType.TOP_LIST.getValue(), scopeTopListHandler);
        strategyMap.put(IdType.TEAM_SEASON.getValue(), teamSeasonMessageHandler);
        strategyMap.put(IdType.TEXT_LIVE.getValue(), textLiveHandler);
        strategyMap.put(IdType.MMS_VIDEO.getValue(), mmsVideoMessageHandler);

    }

    public IMessageHandler get(Integer type) {
        IMessageHandler result = null;
        if (type != null) {
            result = strategyMap.get(type);
        }
        if (result == null) {
            result = defaultMessageHandler;
        }
        return result;
    }
}
