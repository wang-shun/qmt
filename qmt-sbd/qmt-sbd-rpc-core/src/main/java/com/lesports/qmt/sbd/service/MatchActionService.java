package com.lesports.qmt.sbd.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbd.api.dto.TMatchAction;
import com.lesports.qmt.sbd.api.service.GetMacthActionsParam;
import com.lesports.qmt.sbd.model.MatchAction;
import com.lesports.qmt.service.support.QmtService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * User: ellios
 * Time: 15-6-7 : 上午10:27
 */
public interface MatchActionService extends QmtService<MatchAction, Long> {


    /**
     * 通过比赛id获取该厂比赛的事实赛况
     *
     * @param mid
     * @return
     */
    List<MatchAction> findByMid(Long mid, Pageable pageable);


    List<TMatchAction> getTMatchActionsByIds(List<Long> ids, CallerParam caller);

    TMatchAction getTMatchActionById(long id, CallerParam caller);

    List<MatchAction> findMatchActionByMid(Long mid);

    List<Long> getMatchActionsOfMatch(GetMacthActionsParam p, CallerParam caller);
}
