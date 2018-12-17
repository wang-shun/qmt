package com.lesports.qmt.sbd.repository;

import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.sbd.model.LiveEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by qiaohongxin on 2015/9/22.
 */
public interface LiveEventRepository extends MongoCrudRepository<LiveEvent, Long> {

    /**
     * 分页查找所有直播时间类型
     *
     * @param pageable
     * @return
     */
    Page<LiveEvent> findAll(Pageable pageable);

}