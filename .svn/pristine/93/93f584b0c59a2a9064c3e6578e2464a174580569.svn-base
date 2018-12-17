package com.lesports.qmt.config.service;

import com.lesports.qmt.config.api.dto.TSuggest;
import com.lesports.qmt.config.model.Suggest;
import com.lesports.qmt.service.support.QmtService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by ruiyuansheng on 2015/10/19.
 */
public interface SuggestService extends QmtService<Suggest, Long> {

    List<Long> getTSuggestIds(Pageable pageable);

    List<TSuggest> getTSuggestsByIds(List<Long> ids);
}
