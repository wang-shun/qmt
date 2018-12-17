package com.lesports.qmt.search.client;

import com.lesports.LeConstants;
import com.lesports.qmt.search.response.support.SearchPage;
import com.lesports.qmt.search.response.support.SearchResponse;
import com.lesports.utils.LeProperties;
import com.lesports.utils.http.RestTemplateFactory;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Created by denghui on 2016/10/28.
 */
public class SearchInternalApis {
    private static final Logger LOG = LoggerFactory.getLogger(SearchInternalApis.class);

    private static final RestTemplate REST_TEMPLATE = RestTemplateFactory.getTemplate();

    private static final String INNER_SEARCH_URL = LeProperties.getString("inner.search.url",
            "http://internal.api.lesports.com/search/v1/s/sms/{0}/?");

    private static SearchPage doSearch(String urlParams, String type) {
        SearchResponse response = null;
        String url = INNER_SEARCH_URL + urlParams;
        int retry = 0;
        while (retry < LeConstants.MAX_TRY_COUNT) {
            try {
                LOG.info("begin to search {}. url : {}", type, url);
                response = REST_TEMPLATE.getForObject(url, SearchResponse.class, type);
                break;
            } catch (RestClientException e) {
                LOG.info("fail to search {}. url : {}", type, url);
                retry++;
            }
        }
        if (response == null || response.getCode() != 200) {
            return SearchPage.EMPTY_PAGE;
        }
        return response.getData();
    }

    public static SearchPage searchNews(Map<String,Object> params) {
        return doSearch(convertNewsParams(params), "news");
    }

    private static String convertNewsParams(Map<String, Object> params) {
        // TODO: 2016/10/28 添加参数
        StringBuilder sb = new StringBuilder();
        if (params.get("startTime") != null && params.get("endTime") != null){
            sb.append("publishAt=").append(params.get("startTime")).append("&").append("publishAt=").append(params.get("endTime")).append("&");
        }
        if (params.get("startTime") == null && params.get("endTime") != null){
            sb.append("publishAt=").append(-1).append("&").append("publishAt=").append(params.get("endTime")).append("&");
        }
        if (params.get("startTime") != null && params.get("endTime") == null){
            sb.append("publishAt=").append(params.get("startTime")).append("&");
        }
        if (params.get("name") != null && StringUtils.isNotBlank(params.get("name").toString())){
            sb.append("name=").append(params.get("name")).append("&");
        }
        if (params.get("id") != null){
            sb.append("id=").append(params.get("id")).append("&");
        }
        if (params.get("page") != null){
            int page = LeNumberUtils.toInt(params.get("page").toString()) - 1;
            sb.append("page=").append(page).append("&");
        }
        if (params.get("count") != null){
            sb.append("count=").append(params.get("count")).append("&");
        }
        sb.append("caller=").append(1001);
        return sb.toString();
    }
}
