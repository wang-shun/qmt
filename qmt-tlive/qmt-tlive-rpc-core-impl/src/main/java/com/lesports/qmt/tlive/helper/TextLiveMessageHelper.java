package com.lesports.qmt.tlive.helper;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.lesports.model.tlive.ImageContext;
import com.lesports.model.tlive.VoteContext;
import com.lesports.qmt.tlive.api.common.TextLiveMessageType;
import com.lesports.qmt.tlive.model.TextLiveImage;
import com.lesports.qmt.tlive.model.TextLiveMessage;
import com.lesports.qmt.tlive.model.Vote;
import com.lesports.qmt.tlive.repository.TextLiveImageRepository;
import com.lesports.qmt.tlive.repository.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Created by lufei1 on 2015/9/16.
 */
@Component("textLiveMessageHelper")
public class TextLiveMessageHelper {
    private static final Logger LOG = LoggerFactory.getLogger(TextLiveMessageHelper.class);

    @Resource
    private VoteRepository voteRepository;
    @Resource
    private TextLiveImageRepository textLiveImageRepository;


    public TextLiveMessage buildWithTextLiveImage(long textLiveImageId, TextLiveMessage liveMessage) {
        Assert.notNull(liveMessage);
        TextLiveImage textLiveImage = textLiveImageRepository.findOne(textLiveImageId);
        Assert.notNull(textLiveImage);
        liveMessage.setContent(JSON.toJSONString(buildImageContext(textLiveImage)));
        liveMessage.setType(TextLiveMessageType.IMAGE);
        return liveMessage;
    }

    public TextLiveMessage buildWithVote(long voteId, TextLiveMessage liveMessage) {
        Assert.notNull(liveMessage);
        Vote vote = voteRepository.findOne(voteId);
        Assert.notNull(vote);
        liveMessage.setContent(JSON.toJSONString(buildVoteContext(vote)));
        liveMessage.setType(TextLiveMessageType.VOTE);
        return liveMessage;
    }

    public ImageContext buildImageContext(TextLiveImage image) {
        ImageContext imageContext = new ImageContext();
        imageContext.setName(image.getName());
        imageContext.setImageUrl(image.getImageUrl());
        imageContext.setDesc(image.getDesc());
        return imageContext;
    }

    private VoteContext buildVoteContext(Vote vote) {
        VoteContext voteContext = new VoteContext();
        voteContext.setVoteId(vote.getId());
        voteContext.setTopic(vote.getTopic());
        Set<Vote.Option> options = vote.getOptions();
        List<VoteContext.Option> optionList = Lists.newArrayList();
        for (Vote.Option option : options) {
            VoteContext.Option option1 = new VoteContext.Option();
            option1.setOptionId(option.getOptionId());
            option1.setTitle(option.getTitle());
			option1.setOrder(option.getOrder());
            option1.setNum(option.getNum());
            optionList.add(option1);
        }
        voteContext.setOptions(optionList);
        return voteContext;
    }


}
