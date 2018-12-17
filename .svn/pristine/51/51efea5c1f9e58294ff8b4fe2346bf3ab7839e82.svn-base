package com.lesports.qmt.web.api.core.cache.impl;

import com.lesports.exception.VrsAccessException;
import com.lesports.model.Album;
import com.lesports.model.Video;
import com.lesports.qmt.web.api.core.cache.AbstractCache;
import com.lesports.utils.Utf8KeyCreater;
import com.lesports.utils.MmsApis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by lufei1 on 2015/7/6.
 */
@Component
public class VrsCache extends AbstractCache {

    private static final Logger logger = LoggerFactory.getLogger(VrsCache.class);

    private static final Utf8KeyCreater<String> VRS_VIDEO_CREATE = new Utf8KeyCreater<>("V2_VRS_VIDEO_");
    private static final Utf8KeyCreater<String> VRS_ALBUM_CREATE = new Utf8KeyCreater<>("V2_VRS_ALBUM_");
    private static final int EXPIRE_TIME = 30;

    public Video getVideo(String vid)throws VrsAccessException {
        try {
            return findByKey(VRS_VIDEO_CREATE.textKey(vid), Video.class);
        } catch (Exception e) {
            logger.error("get video from cache: " + vid, e);
            return MmsApis.getVideo(vid);
        }
    }

    public void saveVideo(Video video) {
        save(VRS_VIDEO_CREATE.textKey(String.valueOf(video.getId())), video, EXPIRE_TIME);
    }

    public Album getAlbum(String pid) {
        return findByKey(VRS_ALBUM_CREATE.textKey(pid), Album.class);
    }

    public void saveAlbum(Album album) {
        save(VRS_ALBUM_CREATE.textKey(String.valueOf(album.getId())), album, EXPIRE_TIME);
    }

}
