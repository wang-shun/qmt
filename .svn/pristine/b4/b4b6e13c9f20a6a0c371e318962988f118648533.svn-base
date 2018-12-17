package com.lesports.qmt.msg.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Predicate;
import com.lesports.qmt.msg.util.LeExecutors;
import com.lesports.qmt.msg.util.WebResult;
import com.lesports.utils.LeProperties;
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

import javax.annotation.Nullable;

/**
 * lesports-projects.
 *
 * @author: pangchuanxiao
 * @since: 2015/7/23
 */
@Service
public class NewsService extends AbstractService {
    private static final Logger LOG = LoggerFactory.getLogger(NewsService.class);
    @Value("${add.cibn.recommend.tv.url}")
    private String ADD_CIBN_RECOMMEND_TV_URL;

    private static final RestTemplate TEMPLATE = RestTemplateFactory.getTemplate();

    public boolean addNewsToCibnRecommendTv(long id) {
        try {
            MultiValueMap<String, String> param = new LinkedMultiValueMap<>();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("content-type", "application/x-www-form-urlencoded");
            final HttpEntity httpEntity = new HttpEntity(param, httpHeaders);

            Predicate<Long> function = new Predicate<Long>() {
                @Override
                public boolean apply(@Nullable Long aLong) {
                    ResponseEntity<WebResult> result = TEMPLATE.exchange(ADD_CIBN_RECOMMEND_TV_URL, HttpMethod.PUT, httpEntity, WebResult.class, aLong);
                    LOG.info("add news to cibn recommend tv {}, result : {}.", aLong, JSONObject.toJSONString(result));
                    return isWebResultSuccess(result);
                }
            };
            return LeExecutors.executeWithRetry(3, 1000, function, id, "add news to cibn recommend tv");
        } catch (Exception e) {
            LOG.error("{}", e.getMessage(), e);
        }
        return false;
    }


    public boolean deleteNewsApiCache(long id) {
        return deleteNgxApiCache(id);
    }
}
