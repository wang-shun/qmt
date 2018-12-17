package com.lesports.qmt.web.datacenter.dataprocess;

import com.alibaba.fastjson.JSONObject;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.dto.TResourceContent;
import com.lesports.qmt.sbc.client.QmtSbcEpisodeApis;
import com.lesports.qmt.web.datacenter.vo.EpisodeVo;
import com.lesports.qmt.web.datacenter.vo.PostVo;
import com.lesports.utils.PostApis;
import com.lesports.utils.pojo.Post;

import java.util.List;

/**
 * create by wangjichuan  date:16-12-20 time:17:50
 * 社区帖子Vo
 */
public class PostDataProcess implements DataProcess<Post.TopicThread,PostVo> {

    @Override
    public PostVo getEntity() {
        return new PostVo();
    }

    @Override
    public void constructManualRemoteData(PostVo postVo, String itemId, CallerParam callerParam) {
        Post.TopicThread thread = PostApis.postInfo(itemId,callerParam.getCallerId());
        postVo.setId(thread.getId());
        postVo.setName(thread.getTitle());
        postVo.setUname(thread.getUname());
        postVo.setAllReply(thread.getAllReply());
        postVo.setReply(thread.getReply());
        postVo.setUp(thread.getUp());
        postVo.setIsBigV(thread.getIsBigV());
        postVo.setTag(thread.getTag());
        postVo.setTagname(thread.getTagname());
        postVo.setFigurl(thread.getFigurl());
        postVo.setUrl("http://z.lesports.com/post/"+thread.getId());
    }
}
