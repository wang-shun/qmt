package com.lesports.qmt.msg.service;

import com.alibaba.fastjson.JSON;
import com.lesports.qmt.msg.util.CacheParamUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by lufei1 on 2015/10/15.
 */
@Service
public class TextLiveService extends AbstractService {
    private static final Logger logger = LoggerFactory.getLogger(TextLiveService.class);

    public boolean deleteTextLiveApiCache(Map<String, String> paramMap) {
        String cacheKey = CacheParamUtil.buildCacheKey(paramMap);
        if (StringUtils.isBlank(cacheKey)) {
            logger.warn("cache key is null,paramMap:{}", JSON.toJSON(paramMap));
            return false;
        }
        return deleteNgxCache(cacheKey, -1);
    }

    public boolean deleteTextLiveApiCache(long textLiveId) {
        return deleteNgxApiCache(textLiveId);
    }

}
