package com.lesports.qmt.msg.handler.impl;

import com.google.common.base.Function;
import com.lesports.qmt.msg.constant.Constants;
import com.lesports.qmt.msg.core.AreaType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.handler.AbstractMessageHandler;
import com.lesports.qmt.msg.handler.IMessageHandler;
import com.lesports.qmt.msg.producer.SdlcSwiftMessageApis;
import com.lesports.qmt.msg.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.Resource;

/**
 * lesports-projects.
 *
 * @author: pangchuanxiao
 * @since: 2015/7/23
 */
@Component
public class NewsMessageHandler extends AbstractMessageHandler implements IMessageHandler {
    private static final Logger logger = LoggerFactory.getLogger(NewsMessageHandler.class);

    @Resource
    private NewsService newsService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public boolean handleSync(LeMessage message) {
        long newsId = message.getEntityId();
        boolean result = deleteNewsCache(newsId);
        if (result) {
            logger.info("success to handle news message {}", newsId);
        } else {
            logger.error("fail to handler news message {}", newsId);
        }
        //同步新闻到国广推荐新闻
        newsService.addNewsToCibnRecommendTv(newsId);

        if (AreaType.CN.equals(Constants.areaType)) { //只发大陆地区同步消息
            SdlcSwiftMessageApis.sendMsgAsync(message);
        }

        return true;
    }


    private boolean deleteNewsCache(long newsId) {
        return execute(newsId, "news", new Function<Long, Boolean>() {
            @Nullable
            @Override
            public Boolean apply(Long aLong) {
                return newsService.deleteNewsApiCache(aLong);
            }
        });
    }
}
