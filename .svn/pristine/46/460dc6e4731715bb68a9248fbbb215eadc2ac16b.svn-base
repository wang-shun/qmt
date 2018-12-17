package com.lesports.qmt.tlive.api.cache;

import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.jersey.annotation.cache.LogUrlProcessor;
import com.lesports.jersey.annotation.cache.SingleLogUrlProcessor;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * aa.
 *
 * @author pangchuanxiao
 * @since 2015/10/27
 */
public class TextLiveLogUrlProcessor extends SingleLogUrlProcessor implements LogUrlProcessor {
    @Override
    protected boolean valid(String value) {
        if (!StringUtils.isNumeric(value)) {
            return false;
        }
        long id = LeNumberUtils.toLong(value);
        if (id < 1000) {
            return false;
        }
        if (LeIdApis.checkIdTye(id) != IdType.TEXT_LIVE) {
            return false;
        }
        return true;
    }
}
