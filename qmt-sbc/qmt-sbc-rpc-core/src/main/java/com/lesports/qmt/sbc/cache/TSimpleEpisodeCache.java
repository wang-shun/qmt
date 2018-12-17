package com.lesports.qmt.sbc.cache;

import com.lesports.qmt.sbc.api.dto.TSimpleEpisode;
import com.lesports.repository.LeCrudRepository;

/**
 * Created by ruiyuansheng on 2015/11/11.
 */
public interface TSimpleEpisodeCache extends LeCrudRepository<TSimpleEpisode, Long> {

    boolean save(TSimpleEpisode entity, int expire);

}
