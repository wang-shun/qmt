package com.lesports.qmt.sbc.service;

import com.lesports.qmt.config.model.Tag;
import com.lesports.qmt.sbc.model.Video;
import com.lesports.qmt.sbc.param.ReuploadVideoParam;
import com.lesports.qmt.sbc.param.VideoListParam;
import com.lesports.qmt.sbc.param.VideoParam;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by zhangxudong@le.com on 2016/10/25.
 */
public interface VideoService {

    long save(VideoParam videoParam);

    boolean deleteBatch(String ids);

    boolean retranscodingBatch(ReuploadVideoParam videoParam);

    boolean cloneVideoToAlbum(long videoId, String albumIds);

    String getCloneVideoAlbumNames(long videoId);

    Video getVideo(long id);

    Page<VideoListParam> getVideoPage(int pageNumber, int pageSize, long id, String name, int type, long channel, long cid);

    Page<VideoListParam> getVideoPage(int pageNumber, int pageSize, long aid);

    boolean moveVideoFromAlbum(long id);

    boolean addVideoToAlbum(long aid, String ids);

    List<Tag> getTagsByVideoId(long videoId);
}
