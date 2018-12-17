package com.lesports.qmt.resource.converter;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.resource.converter.support.AbstractCvoConverter;
import com.lesports.qmt.resource.cvo.PostCvo;
import com.lesports.qmt.sbc.api.dto.TResourceContent;
import com.lesports.utils.PostApis;
import com.lesports.utils.pojo.Post;

/**
 * User: ellios
 * Time: 16-12-28 : 下午6:51
 */
public class PostCvoConverter extends AbstractCvoConverter<PostCvo, Post.TopicThread> {
    @Override
    public PostCvo doToCvo(TResourceContent content, Post.TopicThread dto) {
        PostCvo cvo = new PostCvo();
        if(dto != null){
            cvo.setId(dto.getId());
            cvo.setName(dto.getTitle());
            cvo.setUname(dto.getUname());
            cvo.setAllReply(dto.getAllReply());
            cvo.setReply(dto.getReply());
            cvo.setUp(dto.getUp());
            cvo.setIsBigV(dto.getIsBigV());
            cvo.setTag(dto.getTag());
            cvo.setTagname(dto.getTagname());
            cvo.setFigurl(dto.getFigurl());
            cvo.setUrl("http://z.lesports.com/post/"+dto.getId());
        }
        return cvo;
    }

    @Override
    public PostCvo doToCvo(TResourceContent content, CallerParam caller) {
        Post.TopicThread dto = PostApis.postInfo(content.getItemId(), caller.getCallerId());
        if (dto == null) {
            LOG.warn("fail to doToCvo. dto is null. itemId : {}, caller : {}", content.getItemId(), caller);
//            return null;
        }

        return toCvo(content, dto);
    }

    @Override
    protected boolean supportContent(TResourceContent content) {
        switch (content.getType()) {
            case POST:
                return true;
            default:
                return false;
        }
    }
}
