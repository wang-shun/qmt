package com.lesports.qmt.sbc.service.support;

import com.lesports.qmt.exception.NoServiceException;
import com.lesports.qmt.sbc.model.*;
import com.lesports.qmt.sbc.service.*;
import com.lesports.qmt.service.support.QmtService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * trunk.
 *
 * @author pangchuanxiao
 * @since 2016/3/29
 */
@Component("sbcServiceFactory")
public class SbcServiceFactory {
    @Resource
    private NewsService newsService;
    @Resource
    private NewsImageService newsImageService;
    @Resource
    private ResourceService resourceService;
    @Resource
    private ResourceContentService resourceContentService;
    @Resource
    private ResourceOnlineService resourceOnlineService;
    @Resource
    private ResourceContentOnlineService resourceContentOnlineService;
    @Resource
    private LiveService liveService;
    @Resource
    private VideoService videoService;
    @Resource
    private VideoMediumService videoMediaService;
    @Resource
    private AlbumService albumService;
    @Resource
    private TopicService topicService;
    @Resource
    private TopicItemPackageService topicItemPackageService;
    @Resource
    private EpisodeService episodeService;
    @Resource
    private ProgramAlbumService programAlbumService;

    public QmtService getService(Class clazz) throws NoServiceException {
        if (News.class == clazz) {
            return newsService;
        } else if (NewsImage.class == clazz) {
            return newsImageService;
        } else if (QmtResource.class == clazz) {
            return resourceService;
        } else if (Live.class == clazz) {
            return liveService;
        } else if (Video.class == clazz) {
            return videoService;
        }else if (VideoMedium.class == clazz) {
            return videoMediaService;
        } else if (Album.class == clazz) {
            return albumService;
        } else if (Topic.class == clazz) {
            return topicService;
        } else if (TopicItemPackage.class == clazz) {
            return topicItemPackageService;
        } else if (Episode.class == clazz) {
            return episodeService;
        } else if (ProgramAlbum.class == clazz) {
            return programAlbumService;
        } else if (ResourceContent.class == clazz) {
            return resourceContentService;
        } else if (ResourceContentOnline.class == clazz) {
            return resourceContentOnlineService;
        } else if (QmtResourceOnline.class == clazz) {
            return resourceOnlineService;
        }
        throw new NoServiceException("No service for class : " + clazz);
    }
}
