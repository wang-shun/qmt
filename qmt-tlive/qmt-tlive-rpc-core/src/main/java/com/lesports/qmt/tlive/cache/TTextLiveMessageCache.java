package com.lesports.qmt.tlive.cache;

import com.lesports.qmt.tlive.api.dto.TTextLiveMessage;
import com.lesports.repository.LeCrudRepository;

import java.util.Set;

/**
 * Created by lufei1 on 2015/9/15.
 */
public interface TTextLiveMessageCache extends LeCrudRepository<TTextLiveMessage, Long> {

    public boolean zadd(Long textLiveId, Long section, Long id);


    public Set<String> zrange(Long textLiveId, Long section);

}
