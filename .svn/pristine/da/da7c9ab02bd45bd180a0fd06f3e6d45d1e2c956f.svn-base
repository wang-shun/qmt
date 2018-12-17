package com.lesports.qmt.msg.cache;

import com.alibaba.fastjson.JSONObject;
import com.lesports.qmt.msg.constant.Constants;
import com.lesports.qmt.msg.core.MsgProducerConstants;
import com.lesports.utils.http.RestTemplateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;

/**
 * lesports-projects.
 *
 * @author: pangchuanxiao
 * @since: 2015/7/10
 */
public class CdnCacheApis {
    private static final Logger LOG = LoggerFactory.getLogger(CdnCacheApis.class);
    private static final RestTemplate restTemplate = RestTemplateFactory.getTemplate(10000);

    public static boolean delete(String key) {
        if (!Constants.DELETE_CDN_CACHE_ENABLE) {
            return true;
        }
        boolean deleteSuccess = false;
        int retry = 0;
        while (retry < 2) {
            try {
                String url = MessageFormat.format(MsgProducerConstants.CDN_CLEAN_URL, key);
                CleanCacheResult result = restTemplate.getForObject(url, CleanCacheResult.class);
                if (null != result && result.isSuccess()) {
                    deleteSuccess = true;
                }

                LOG.info("delete key in cdn cache {}, result : {}. {}", key, deleteSuccess, JSONObject.toJSONString(result));
                break;
            } catch (Exception e) {
                LOG.warn("fail to delete key in cdn cache {}, result : {}. {}", key, deleteSuccess, e.getMessage(), e);
                retry++;
            }
        }


        return deleteSuccess;
    }
}
