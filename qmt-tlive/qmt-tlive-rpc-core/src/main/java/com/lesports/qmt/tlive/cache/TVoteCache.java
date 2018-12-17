package com.lesports.qmt.tlive.cache;

import com.lesports.qmt.tlive.api.dto.TVote;
import com.lesports.repository.LeCrudRepository;

/**
 * Created by lufei1 on 2015/9/16.
 */
public interface TVoteCache extends LeCrudRepository<TVote, Long> {

    /**
     * 增加投票
     *
     * @param voteId
     * @param optionId
     * @return
     */
    public int incrVoteNum(long voteId, long optionId);

    /**
     * 获取投票数
     *
     * @param voteId
     * @param optionId
     * @return
     */
    public int getVoteNum(long voteId, long optionId);
}
