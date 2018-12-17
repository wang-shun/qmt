package com.lesports.qmt.sbd.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.utils.LeProperties;
import com.lesports.utils.http.RestTemplateFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lufei1 on 2016/12/2.
 */
public class QmtSearchApis<T extends QmtModel> {
    private static final Logger LOG = LoggerFactory.getLogger(QmtSearchApis.class);

    private static final RestTemplate REST_TEMPLATE = RestTemplateFactory.getTemplate();

    private static final String INNER_SEARCH_URL = LeProperties.getString("qmt.search.url",
            "http://t1.qmt.lesports.com/search/v2/s/qmt/{0}?");


    public static <T extends QmtModel<Long>> QmtSearchData<T> searchData(Map params, Class<T> tClass) {
        String paramStr = buildSearchParam(params);
        try {
            LOG.info("begin to searchData. url : {}", INNER_SEARCH_URL + paramStr);
            Map response = REST_TEMPLATE.getForObject(INNER_SEARCH_URL + paramStr, Map.class, tClass.getSimpleName().toLowerCase());
            if (MapUtils.getInteger(response, "code") == 200) {
                Map map = MapUtils.getMap(response, "data");
                return buildQmtSearchData(map, tClass);
            }
        } catch (RestClientException e) {
            LOG.error("fail to searchData. url : {},error:", INNER_SEARCH_URL + paramStr, e);
        }
        return null;

    }

    private static <T extends QmtModel<Long>> QmtSearchData<T> buildQmtSearchData(Map responseMap, Class<T> tClass) {
        QmtSearchData<T> qmtSearchData = new QmtSearchData<T>();
        qmtSearchData.setTotal(MapUtils.getLongValue(responseMap, "total"));
        List<T> list = Lists.newArrayList();
        List rows = (List) MapUtils.getObject(responseMap, "rows");
        if (CollectionUtils.isNotEmpty(rows)) {
            String json = JSON.toJSONString(rows);
//            if (json.contains("\"id\":")) {
//                //fix fastjson error
//                json = json.replaceAll("\"id\":", "\"helperId\":");
//            }
            list = JSON.parseArray(json, tClass);
        }
        qmtSearchData.setRows(list);
        return qmtSearchData;
    }

    private static String buildSearchParam(Map params) {
        StringBuilder sb = new StringBuilder();
        if (MapUtils.isNotEmpty(params)) {
            Set<Map.Entry> set = params.entrySet();
            for (Map.Entry entry : set) {
                if (entry.getKey() != null) {
                    if (entry.getKey().equals("page")) {
                        sb.append("page=").append(MapUtils.getIntValue(params, "page") - 1).append("&");
                    } else {
                        sb.append(entry.getKey()).append("=").append(entry.getValue().toString()).append("&");
                    }
                }
            }
        }
        sb.append("deleted=").append("false");
        return sb.toString();
    }
}
