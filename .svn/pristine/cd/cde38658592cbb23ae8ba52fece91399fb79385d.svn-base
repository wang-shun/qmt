package com.lesports.qmt.tlive.service.impl;

import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.tlive.api.dto.TOption;
import com.lesports.qmt.tlive.api.dto.TVote;
import com.lesports.qmt.tlive.cache.TVoteCache;
import com.lesports.qmt.tlive.converter.VoteVoCreator;
import com.lesports.qmt.tlive.helper.TextLiveMessageHelper;
import com.lesports.qmt.tlive.model.TextLiveMessage;
import com.lesports.qmt.tlive.model.Vote;
import com.lesports.qmt.tlive.repository.VoteRepository;
import com.lesports.qmt.tlive.service.TextLiveMessageService;
import com.lesports.qmt.tlive.service.VoteService;
import com.lesports.qmt.tlive.service.support.AbstractTextLiveService;
import com.lesports.utils.math.LeNumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Created by lufei1 on 2015/9/14.
 */
@Service("voteService")
public class VoteServiceImpl extends AbstractTextLiveService<Vote, Long> implements VoteService {

    private static final Logger LOG = LoggerFactory.getLogger(VoteServiceImpl.class);

    @Resource
    private VoteRepository voteRepository;
    @Resource
    private TextLiveMessageHelper textLiveMessageHelper;
    @Resource
    private TextLiveMessageService textLiveMessageService;
    @Resource
    private VoteVoCreator voteVoCreator;
    @Resource
    private TVoteCache voteCache;

    @Override
    protected QmtOperationType getOpreationType(Vote entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return QmtOperationType.UPDATE;
        }
        return QmtOperationType.CREATE;
    }

    @Override
    protected boolean doCreate(Vote entity) {
        entity.setId(LeIdApis.nextId(IdType.VOTE));
        entity.setDeleted(false);
        return voteRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(Vote entity) {
        return false;
    }

    @Override
    protected boolean doUpdate(Vote entity) {
        return voteRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(Vote entity) {
        return voteCache.delete(entity.getId());
    }

    @Override
    protected boolean doDelete(Long aLong) {
        return false;
    }

    @Override
    protected boolean doAfterDelete(Vote entity) {
        return false;
    }

    @Override
    protected Vote doFindExistEntity(Vote entity) {
        return null;
    }

    @Override
    public void sendVoteMessage(long id, TextLiveMessage liveMessage) {
        LOG.info("send voteMessage.id:{},liveMessage:{}", id, liveMessage);
        liveMessage = textLiveMessageHelper.buildWithVote(id, liveMessage);
        textLiveMessageService.save(liveMessage);
    }

    @Override
    public TVote getTVoteById(long id) {
        TVote tVote = voteCache.findOne(id);
        if (tVote == null) {
            tVote = voteVoCreator.createTVote(id);
            if (tVote != null) {
                voteCache.save(tVote);
            }
        }
        return tVote;
    }

    @Override
    public TVote addVote(long voteId, long optionId) {
        Query q = query(where("deleted").is(false));
        q.addCriteria(where("id").is(voteId));
        q.addCriteria(where("options.optionId").is(optionId));
        Vote vote = voteRepository.findOneByQuery(q);
        if (vote == null) {
            LOG.warn("add vote fail,vote illegal. voteId:{},optionId:{}", voteId, optionId);
            return null;
        }
        voteCache.incrVoteNum(voteId, optionId);
        return getVote(voteId);
    }


    @Override
    public TVote getVote(long voteId) {
        TVote tVote = getTVoteById(voteId);
        if (tVote == null) {
            LOG.warn("get vote fail. voteId:{}", voteId);
            return null;
        }
        for (TOption tOption : tVote.getOptions()) {
            tOption.setNum(voteCache.getVoteNum(voteId, tOption.getOptionId()));
        }
        return tVote;
    }

    @Override
    protected MongoCrudRepository<Vote, Long> getInnerRepository() {
        return voteRepository;
    }
}
