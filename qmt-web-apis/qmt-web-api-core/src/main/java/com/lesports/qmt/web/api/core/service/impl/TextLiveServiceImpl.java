package com.lesports.qmt.web.api.core.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.web.api.core.cache.impl.TextLiveCache;
import com.lesports.qmt.web.api.core.creater.TextLiveMessageVoCreater;
import com.lesports.qmt.web.api.core.service.TextLiveService;
import com.lesports.qmt.web.api.core.util.CollectionTools;
import com.lesports.qmt.web.api.core.vo.GetRealtimeCacheParam;
import com.lesports.qmt.web.api.core.vo.TextLiveMessageVo;
import com.lesports.sms.api.vo.TTextLiveMessage;
import com.lesports.sms.client.TextLiveApis;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
* Created by lufei1 on 2015/9/20.
*/
@Service("textLiveService")
public class TextLiveServiceImpl extends AbstractService implements TextLiveService {
    private static final Logger LOG = LoggerFactory.getLogger(TextLiveServiceImpl.class);

    @Resource
    private TextLiveMessageVoCreater liveMessageVoCreater;
    @Resource
    private TextLiveCache textLiveCache;

    private LoadingCache<GetRealtimeCacheParam, TextLiveMessageVo> textLiveMessageVoLoadingCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build(new CacheLoader<GetRealtimeCacheParam, TextLiveMessageVo>() {
                @Override
                public TextLiveMessageVo load(GetRealtimeCacheParam key) throws Exception {
                    LOG.info("save text live message : {} in memory cache.", key);
                    TextLiveMessageVo textLiveMessageVo = textLiveCache.getTextLiveMessageVo(key.getEpisodeId());
                    if (null == textLiveMessageVo) {
                        List<TTextLiveMessage> textLiveMessages = TextLiveApis.getLiveMessageByIds(Lists.newArrayList(key.getEpisodeId()));
                        if (CollectionUtils.isNotEmpty(textLiveMessages)) {
                            textLiveMessageVo = liveMessageVoCreater.createLiveMessageVo(textLiveMessages.get(0));
                        }
                        if (null != textLiveMessageVo) {
                            textLiveCache.saveTextLiveMessageVo(key.getEpisodeId(), textLiveMessageVo);
                        }
                    }

                    return textLiveMessageVo;
                }
            });

    @Override
    public List<TextLiveMessageVo> getLiveMessageByIdsRealtime(String ids,CallerParam caller) {
        return get(textLiveMessageVoLoadingCache, ids, caller);
    }

    @Override
    public List<TextLiveMessageVo> getLiveMessageByIds(String ids) {
        List<TextLiveMessageVo> liveMessageVos = Lists.newArrayList();
        List<TTextLiveMessage> tLiveMessages = TextLiveApis.getLiveMessageByIds(CollectionTools.string2List(ids));
        if (CollectionUtils.isNotEmpty(tLiveMessages)) {
            for (TTextLiveMessage tLiveMessage : tLiveMessages) {
                liveMessageVos.add(liveMessageVoCreater.createLiveMessageVo(tLiveMessage));
            }
        }
        return liveMessageVos;
    }

    @Override
    public List<TextLiveMessageVo> getLiveMessageByPage(long textLiveId, long section, int page) {
        List<TextLiveMessageVo> liveMessageVos = Lists.newArrayList();
        List<TTextLiveMessage> tLiveMessages = TextLiveApis.getLiveMessageByPage(textLiveId, section, page);
        if (CollectionUtils.isNotEmpty(tLiveMessages)) {
            for (TTextLiveMessage tLiveMessage : tLiveMessages) {
                liveMessageVos.add(liveMessageVoCreater.createLiveMessageVo(tLiveMessage));
            }
        }
        return liveMessageVos;
    }
}
