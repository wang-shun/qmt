package com.lesports.qmt.tlive.converter;

import com.google.common.collect.Lists;
import com.lesports.qmt.tlive.api.dto.TOption;
import com.lesports.qmt.tlive.api.dto.TVote;
import com.lesports.qmt.tlive.model.Vote;
import com.lesports.qmt.tlive.repository.VoteRepository;
import com.lesports.utils.math.LeNumberUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Created by lufei1 on 2015/9/16.
 */
@Component("voteVoCreator")
public class VoteVoCreator {

    @Resource
    private VoteRepository voteRepository;


    public TVote createTVote(long id) {
        Vote vote = voteRepository.findOne(id);
        if (vote == null) {
            return null;
        }
        TVote tVote = new TVote();
        fillTVote(tVote, vote);
        return tVote;
    }

    public void fillTVote(TVote tVote, Vote vote) {
        tVote.setId(vote.getId());
        tVote.setTopic(vote.getTopic());
        Set<Vote.Option> options = vote.getOptions();
        List<TOption> tOptions = Lists.newArrayList();
        for (Vote.Option option : options) {
            TOption tOption = new TOption();
            tOption.setOptionId(option.getOptionId());
            tOption.setTitle(option.getTitle());
            tOption.setNum(LeNumberUtils.toInt(option.getNum()));
            if (option.getOrder() != null) {
                tOption.setOrder(option.getOrder());
            }
            tOptions.add(tOption);
        }
        tVote.setOptions(tOptions);
    }

}
