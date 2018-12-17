package com.lesports.qmt.sbd.repository;

import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.sbd.model.MatchAction;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by yangyu on 2015/6/1.
 */
public interface MatchActionRepository extends MongoCrudRepository<MatchAction, Long> {

    /**
     * 通过比赛id获取该厂比赛的事实赛况
     * @param mid
     * @return
     */
    List<MatchAction> findByMid(Long mid, Pageable pageable);

    List<MatchAction> findMatchActionByMid(Long mid);
}
