package com.lesports.qmt.sbc.service.impl;

import com.google.common.collect.Lists;
import com.lesports.qmt.config.client.QmtConfigTagInternalApis;
import com.lesports.qmt.config.model.Tag;
import com.lesports.qmt.sbc.api.common.VideoContentType;
import com.lesports.qmt.sbc.client.QmtSbcLiveInternalApis;
import com.lesports.qmt.sbc.client.QmtSbcVideoInternalApis;
import com.lesports.qmt.sbc.converter.VideoVoConverter;
import com.lesports.qmt.sbc.model.Album;
import com.lesports.qmt.sbc.model.Live;
import com.lesports.qmt.sbc.model.Video;
import com.lesports.qmt.sbc.param.ReuploadVideoParam;
import com.lesports.qmt.sbc.param.VideoListParam;
import com.lesports.qmt.sbc.param.VideoParam;
import com.lesports.qmt.sbc.service.AlbumService;
import com.lesports.qmt.sbc.service.VideoService;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxudong@le.com on 2016/10/25.
 */
@Service
public class VideoServiceImpl implements VideoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoServiceImpl.class);

    @Resource
    private VideoVoConverter videoVoConverter;

    @Resource
    private AlbumService albumService;

    @Override
    public long save(VideoParam videoParam) {
        if (null == videoParam) return 0L;
        Video video = videoVoConverter.toVideo(videoParam);
        return QmtSbcVideoInternalApis.saveVideo(video, true);
    }

    @Override
    public Video getVideo(long id) {
        Video video = QmtSbcVideoInternalApis.getVideoById(id);
        Live live = QmtSbcLiveInternalApis.getLiveByPlaybackId(id);
        if (null != live) {
            video.setRecordId(live.getId());
        }
        return video;
    }

    @Override
    public boolean deleteBatch(String ids) {
        if (StringUtils.isEmpty(ids)) return false;
        String[] idPieces = ids.split(",");
        for (String idTmp : idPieces) {
            try {
                long id = Long.parseLong(idTmp);
                if (0 != id)
                    QmtSbcVideoInternalApis.deleteVideo(id);
            } catch (NumberFormatException efe) {
                LOGGER.error("failed to delete video id: {}", idTmp, efe);
            } catch (Exception e) {
                LOGGER.error("failed to delete video : {}", e);
            }
        }
        return true;
    }

    @Override
    public boolean retranscodingBatch(ReuploadVideoParam videoParam) {
        if (null == videoParam || StringUtils.isEmpty(videoParam.getIds())) return false;
        String[] idPieces = videoParam.getIds().split(",");
        for (String idTmp : idPieces) {
            long id = 0L;
            try {
                id = Long.parseLong(idTmp);
            } catch (Exception e) {
                continue;
            }
            if (0 == id) continue;
            Video video = this.getVideo(id);
            if (null == video) continue;
            video.setDesc(videoParam.getDescription());
            video.setChannel(videoParam.getGameFType());
            video.setCid(videoParam.getCid());
            video.setIsPay(videoParam.getPay());
            video.setShieldAreaType(videoParam.getDisableType());
            QmtSbcVideoInternalApis.saveVideo(video);
        }
        return true;
    }

    @Override
    public Page<VideoListParam> getVideoPage(int pageNumber, int pageSize, long id, String name, int type, long channel, long cid) { //FIXME:可能调整为es
        InternalQuery query = new InternalQuery();
        if (0 != id)
            query.addCriteria(new InternalCriteria("_id").is(id));
        if (false == StringUtils.isEmpty(name))
            query.addCriteria(new InternalCriteria("title").is(name));
        if (-1 != type)
            query.addCriteria(new InternalCriteria("type").is(VideoContentType.findByValue(type)));
        if (0 != channel)
            query.addCriteria(new InternalCriteria("channel").is(channel));
        if (0 != cid)
            query.addCriteria(new InternalCriteria("cid").is(cid));
        query.with(new PageRequest(0, Integer.MAX_VALUE));
        long count = QmtSbcVideoInternalApis.getVideoCountByQuery(query);
        if (count <= 0) return null;

        Pageable pageable = new PageRequest(pageNumber, pageSize, new Sort(new Sort.Order(Sort.Direction.DESC, "create_at")));
        query.with(pageable);
        List<VideoListParam> result = videoVoConverter.toVideoListParamList(QmtSbcVideoInternalApis.getVideoByQuery(query));
        if (false == CollectionUtils.isEmpty(result)) return new PageImpl<>(result, pageable, count);
        return null;
    }

    @Override
    public Page<VideoListParam> getVideoPage(int pageNumber, int pageSize, long aid) { //FIXME:可能调整为es
        InternalQuery query = new InternalQuery();
        query.addCriteria(new InternalCriteria("aid").is(aid)); // 没有关联专辑的视频 aid 默认是0
        query.addCriteria(new InternalCriteria("is_clone").is(false));
        query.with(new PageRequest(0, Integer.MAX_VALUE));
        long count = QmtSbcVideoInternalApis.getVideoCountByQuery(query);
        if (count <= 0) return new PageImpl<>(new ArrayList<>(), new PageRequest(pageNumber, pageSize), count);

        Pageable pageable = new PageRequest(pageNumber, pageSize, new Sort(new Sort.Order(Sort.Direction.DESC, "create_at")));
        query.with(pageable);
        List<VideoListParam> result = videoVoConverter.toVideoListParamList(QmtSbcVideoInternalApis.getVideoByQuery(query));
        if (false == CollectionUtils.isEmpty(result)) return new PageImpl<>(result, pageable, count);
        return new PageImpl<>(new ArrayList<>(), pageable, count);
    }

    @Override
    public boolean moveVideoFromAlbum(long id) {
        if (0 == id) return false;
        Video video = QmtSbcVideoInternalApis.getVideoById(id);
        if (null == video) return false;
        video.setAid(0L);
        long videoId = QmtSbcVideoInternalApis.saveVideo(video);
        return videoId > 0;
    }

    @Override
    public boolean addVideoToAlbum(long aid, String ids) {
        Album album = albumService.getAlbumById(aid);
        if(null == album) return false;
        List<Long> idss = getLongListFromString(ids);
        List<Video> res = QmtSbcVideoInternalApis.getVideoByIds(idss);
        if(CollectionUtils.isEmpty(res)) return false;
        for(Video video : res) {
            video.setAid(aid);
            QmtSbcVideoInternalApis.saveVideo(video);
        }
        return true;
    }

    private List<Long> getLongListFromString(String ids) {
        List<Long> idss = Lists.newArrayList();
        if (StringUtils.isEmpty(ids)) return idss;
        String[] idPieces = ids.split(",");
        for (String idTmp : idPieces) {
            try {
                long id = Long.parseLong(idTmp);
                idss.add(id);
            }catch (Exception e){
                continue;
            }
        }
        return idss;
    }

    @Override
    public boolean cloneVideoToAlbum(long videoId, String albumIds) { //克隆视频到专辑
        List<Long> albumIdList = getLongListFromString(albumIds);
        return QmtSbcVideoInternalApis.cloneVideoToAlbum(videoId, albumIdList);
    }

    @Override
    public String getCloneVideoAlbumNames(long videoId) {
        if (0 == videoId) return "";
        InternalQuery query = new InternalQuery().with(new PageRequest(0, Integer.MAX_VALUE));
        query.getCriterias().add(new InternalCriteria("clone_id").is(videoId));
        query.getCriterias().add(new InternalCriteria("is_clone").is(true));

        List<Video> videos = QmtSbcVideoInternalApis.getVideoByQuery(query);
        if (CollectionUtils.isEmpty(videos)) return "";
        StringBuilder result = new StringBuilder();
        for (Video video : videos) {
            if (null == video || null == video.getAid() || true == video.getDeleted()) continue;
            Album album = albumService.getAlbumById(video.getAid());
            if (null == album || null == album.getTitle()) continue;
            result.append("||||");
            result.append(album.getTitle());
        }
        return result.toString();
    }

    @Override
    public List<Tag> getTagsByVideoId(long videoId) {
        if (0 == videoId) return Lists.newArrayList();
        Video video = QmtSbcVideoInternalApis.getVideoById(videoId);
        if (null == video || CollectionUtils.isEmpty(video.getRelatedIds())) return Lists.newArrayList();
        List<Tag> result = QmtConfigTagInternalApis.getTagsByIds(new ArrayList(video.getRelatedIds()));
        if (CollectionUtils.isEmpty(result)) return Lists.newArrayList();
        return result;
    }
}
