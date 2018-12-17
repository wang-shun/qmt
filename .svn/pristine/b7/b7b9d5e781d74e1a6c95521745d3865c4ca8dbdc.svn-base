package com.lesports.qmt.msg.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Predicate;
import com.lesports.id.api.IdType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.util.IndexResult;
import com.lesports.qmt.msg.util.LeExecutors;
import com.lesports.utils.http.RestTemplateFactory;
import org.apache.commons.lang3.StringUtils;
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

/**
 * Created by zhangdeqiang on 2016/11/14.
 */
@Service
public class DefaultService extends AbstractService {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultService.class);
    private static final RestTemplate TEMPLATE = RestTemplateFactory.getTemplate();
    @Value("${search.index.url}")
    private String searchIndexUrl;
    public boolean handleIndex(Long id, IdType idType) {
        if (id == null || id < 1) {
            return false;
        }

        try {
            MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("content-type", "application/x-www-form-urlencoded");
            HttpEntity httpEntity = new HttpEntity(param, httpHeaders);

            Predicate<Long> function = entryId -> {
                ResponseEntity<IndexResult> result = TEMPLATE.exchange(searchIndexUrl, HttpMethod.PUT, httpEntity, IndexResult.class, StringUtils.lowerCase(idType.toString()), entryId);
                LOG.info("searchIndexUrl:{}", searchIndexUrl);
                LOG.info("indexing {}: {}, result : {}.", idType, entryId, JSONObject.toJSONString(result));
                return isIndexSuccess(result);
            };
            return LeExecutors.executeWithRetry(3, 1000, function, id, "indexing");
        } catch (Exception e) {
            LOG.error("{}", e.getMessage(), e);
            return false;
        }
    }

    public boolean handleSync(LeMessage message) {
        return true;
        // TODO: 2016/11/14 根据idtype 调用对应的系统接口
        // TODO: 2017/1/16 暂时都返回true，后续根据具体接口做适配
    }

    public static void main(String[] args) {
        System.out.println(StringUtils.lowerCase(IdType.EPISODE.toString()));
    }
}
