package com.lesports.qmt.sbd.converter;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.mvc.QmtVo;

/**
 * Created by lufei1 on 2016/11/2.
 */
public interface VoConverter<M, V extends QmtVo> {

    /**
     * 将model转化为vo对象
     *
     * @param m
     * @return
     */
    V toTVo(M m, CallerParam caller);

}
