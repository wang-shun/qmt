package com.lesports.qmt.web.api.core.cache.impl;

import com.lesports.qmt.web.api.core.cache.AbstractCache;
import com.lesports.utils.Utf8KeyCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by lufei1 on 2016/6/12.
 */
@Component
public class SsoCache extends AbstractCache {

    private static final Logger logger = LoggerFactory.getLogger(SsoCache.class);

    private static final Utf8KeyCreater<String> SSO_TK_CREATE = new Utf8KeyCreater<>("SSO_TOKEN_");

    private static final int EXPIRE_TIME = 3600 * 12;

    public String getUid(String ssoTk) {
        try {
            return findByKey(SSO_TK_CREATE.textKey(ssoTk), String.class);
        } catch (Exception e) {
            logger.error("get uid error from cache: " + ssoTk, e);
        }
        return null;
    }

    public void saveUid(String ssoTk, String uid) {
        save(SSO_TK_CREATE.textKey(ssoTk), uid, EXPIRE_TIME);
    }

}
