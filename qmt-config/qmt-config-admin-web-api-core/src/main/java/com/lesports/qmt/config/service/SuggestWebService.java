package com.lesports.qmt.config.service;

import com.lesports.qmt.config.vo.SuggestVo;
import com.lesports.qmt.mvc.QmtWebService;

import java.util.List;

/**
 * Created by denghui on 2016/12/9.
 */
public interface SuggestWebService extends QmtWebService<SuggestVo, Long> {
    /**
     * 搜索词排序
     * @param ids
     * @return
     */
    boolean resetOrder(List<Long> ids);
}
