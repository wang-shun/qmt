package com.lesports.qmt.sbd.client;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbd.api.dto.TMatchReview;

/**
 * Created by zhonglin on 2017/1/4.
 */
public class SbdMatchReviewApis extends SbdApis {
    public static TMatchReview getTMatchReviewById(long mid, CallerParam caller) {
        try {
            return sbdService.getTMatchReviewById(mid, caller);
        } catch (Exception e) {
            LOG.error("fail to getTMatchReviewById. mid : {}, caller : {}", mid, caller, e);
        }
        return null;
    }
}
