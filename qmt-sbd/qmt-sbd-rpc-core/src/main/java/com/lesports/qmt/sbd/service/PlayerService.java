package com.lesports.qmt.sbd.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbd.api.dto.TPlayer;
import com.lesports.qmt.sbd.api.service.GetPlayers4SimpleSearchParam;
import com.lesports.qmt.sbd.model.Player;
import com.lesports.qmt.service.support.QmtService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by lufei on 2016/10/24.
 */
public interface PlayerService extends QmtService<Player, Long> {

    TPlayer getTPlayerById(long id, CallerParam caller);

    List<TPlayer> getTPlayersByIds(List<Long> ids, CallerParam caller);

    List<Long> getPlayerIds4SimpleSearch(GetPlayers4SimpleSearchParam p, Pageable pageable, CallerParam caller);

}
