package com.lesports.qmt.tlive.service;


import com.lesports.qmt.service.support.QmtService;
import com.lesports.qmt.tlive.api.dto.TVote;
import com.lesports.qmt.tlive.model.TextLiveMessage;
import com.lesports.qmt.tlive.model.Vote;

/**
 * Created by lufei1 on 2015/9/14.
 */
public interface VoteService extends QmtService<Vote, Long> {

    /**
     * 发送投票消息
     *
     * @param id
     * @param liveMessage
     */
    public void sendVoteMessage(long id, TextLiveMessage liveMessage);

    /**
     * 获取投票信息
     *
     * @param id
     * @return
     */
    public TVote getTVoteById(long id);

    /**
     * 增加投票
     *
     * @param voteId
     * @param optionId
     * @return
     */
    public TVote addVote(long voteId, long optionId);


    /**
     * 获取投票结果
     *
     * @param voteId
     * @return
     */
    public TVote getVote(long voteId);
}
