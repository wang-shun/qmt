package com.lesports.qmt.sbd.utils;

import com.lesports.utils.CampApis;
import com.lesports.utils.Utf8KeyCreater;
import com.lesports.utils.pojo.Camp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhonglin on 2016/5/12.
 */
public class CampUtil {

    private static final Logger LOG = LoggerFactory.getLogger(CampUtil.class);

    protected static final Utf8KeyCreater<String> CAMP_KEY_CREATER = new Utf8KeyCreater<>("V2_SMS_CAMP_TF_");

    //缓存一天
    protected static final int CAMP_CACHE_EXPIRE_TIME = 3600 * 24;

    /**
     * 获取阵营信息
     * @param campId
     * @return
     */
    public static Camp getCamp(String campId) {
        String key = CAMP_KEY_CREATER.textKey(campId);
        Camp camp = null;
        Object obj = KVCacheUtils.get(key);
        if (obj != null) {
            camp = (Camp) obj;
        } else {
            LOG.warn("camp not find in cache. key : {} try to get from redis", key);
            camp = CampApis.getCamp(campId);
            if (camp != null) {
                KVCacheUtils.set(key, camp, CAMP_CACHE_EXPIRE_TIME);
                return camp;
            }
        }
        return camp;
    }
}
