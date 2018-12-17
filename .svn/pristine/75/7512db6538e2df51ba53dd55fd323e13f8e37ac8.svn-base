package com.lesports.qmt.msg.service;

import com.alibaba.fastjson.JSONObject;
import com.lesports.id.api.IdType;
import com.lesports.qmt.msg.cache.CdnCacheApis;
import com.lesports.qmt.msg.cache.NgxCmsMemCache;
import com.lesports.qmt.msg.cache.NgxCmsMemCacheTDXY;
import com.lesports.qmt.msg.cache.SisWebMemCache;
import com.lesports.qmt.msg.core.MsgProducerConstants;
import com.lesports.qmt.msg.util.IndexResult;
import com.lesports.utils.LeProperties;
import com.lesports.utils.Utf8KeyCreater;
import com.lesports.utils.http.RestTemplateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * lesports-projects.
 *
 * @author: pangchuanxiao
 * @since: 2015/7/23
 */
@Service("videoService")
public class VideoService extends AbstractService {
    private static final Logger LOG = LoggerFactory.getLogger(VideoService.class);

    private static final Utf8KeyCreater<Long> VRS_VIDEO_CREATE = new Utf8KeyCreater<>("V2_VRS_VIDEO_");
    private static final Utf8KeyCreater<Long> OLD_VRS_VIDEO_CREATE = new Utf8KeyCreater<>("V2_VRS_VIDEO_");

    private final static MessageFormat format = new MessageFormat("http://sports.letv.com/video/{0}.html");

    private static final RestTemplate TEMPLATE = RestTemplateFactory.getTemplate();

    @Value("${search.index.url}")
    private String searchIndexUrl;

    @Resource
    private NgxCmsMemCache ngxCmsMemCache;

    @Resource
    private NgxCmsMemCacheTDXY ngxCmsMemCacheTDXY;

    @Resource
    private SisWebMemCache sisWebMemCache;

    public boolean deleteVideoInSis(long id) {
        return sisWebMemCache.delete(VRS_VIDEO_CREATE.textKey(id), id) & sisWebMemCache.delete(OLD_VRS_VIDEO_CREATE.textKey(id), id);
    }

    public boolean deleteVideoApiCache(long id) {
        return deleteNgxApiCache(id);
    }

    public boolean deletePageInMemCached(String url) {
        return ngxCmsMemCache.delete(url, -1) & ngxCmsMemCacheTDXY.delete(url, -1);
    }

    public boolean deletePageInMemCached(long id) {
        return ngxCmsMemCache.delete(format.format(new Object[]{String.valueOf(id)}), id) & ngxCmsMemCacheTDXY.delete(format.format(new Object[]{String.valueOf(id)}), id);
    }

    public boolean deletePageInCdn(String url) {
        return CdnCacheApis.delete(url);
    }

    public boolean indexVideo(long id) {
        try {
            MultiValueMap<String, String> param = new LinkedMultiValueMap<>();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("content-type", "application/x-www-form-urlencoded");
            HttpEntity httpEntity = new HttpEntity(param, httpHeaders);

            ResponseEntity<IndexResult> result = TEMPLATE.exchange(searchIndexUrl, HttpMethod.PUT, httpEntity, IndexResult.class, IdType.EPISODE, id);
            LOG.info("indexing video {}, result {}", id, JSONObject.toJSONString(result.getBody()));
            if (!isIndexSuccess(result)) {
                result = TEMPLATE.exchange(searchIndexUrl, HttpMethod.PUT, httpEntity, IndexResult.class, IdType.EPISODE, id);
                LOG.info("reIndexing video {}, result {}", id, JSONObject.toJSONString(result.getBody()));
            }
            return true;
        } catch (Exception e) {
            LOG.error("{}", e.getMessage(), e);
        }
        return false;
    }

    public boolean deletePageInCdn(long id) {
        return CdnCacheApis.delete(format.format(new Object[]{String.valueOf(id)}));
    }
}
