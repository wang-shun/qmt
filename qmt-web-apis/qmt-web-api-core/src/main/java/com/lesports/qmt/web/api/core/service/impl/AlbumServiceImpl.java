package com.lesports.qmt.web.api.core.service.impl;

import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.sbc.api.dto.TProgramAlbum;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.web.api.core.creater.AlbumVoCreater;
import com.lesports.qmt.web.api.core.service.AlbumService;
import com.lesports.qmt.web.api.core.service.VideoService;
import com.lesports.qmt.web.api.core.vo.AlbumVo;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ruiyuansheng on 2015/7/30.
 */
@Service("albumService")
public class AlbumServiceImpl implements AlbumService {
    @Resource
    private AlbumVoCreater albumVoCreater;
    @Resource
    private VideoService videoService;

    @Override
    public List<AlbumVo> getAllAlbums(PageParam pageParam, CallerParam callerParam) {
        List<AlbumVo> returnList = Lists.newArrayList();
        List<Long> ids = QmtSbcApis.getTProgramAlbums(0L,pageParam, callerParam);
        List<TProgramAlbum> albums = QmtSbcApis.getTProgramAlbumsByIds(ids, callerParam);

        if (CollectionUtils.isNotEmpty(albums)) {
            for (TProgramAlbum album : albums) {
                AlbumVo albumVo = albumVoCreater.createAlbumVo(album, callerParam);
                if(null != albumVo) {
                    returnList.add(albumVo);
                }
            }
        }
        Collections.sort(returnList,new Comparator<AlbumVo>() {
            @Override
            public int compare(AlbumVo o1, AlbumVo o2) {
                int order = o2.getOrder() - o1.getOrder();
                if (0 != order) {
                    return order;
                } else {
                    if (LeNumberUtils.toLong(o1.getLatestTime()) - LeNumberUtils.toLong(o2.getLatestTime()) > 0)
                        return -1;
                    else {
                        return 1;
                    }
                }

            }
        });
        return returnList;
    }

    @Override
    public AlbumVo getAlbumById(long id, CallerParam callerParam) {

        TProgramAlbum album = QmtSbcApis.getTProgramAlbumById(id, callerParam);
        AlbumVo albumVo = albumVoCreater.createAlbumVo(album, callerParam);

        return albumVo;
    }

    @Override
    public List<AlbumVo> getTAlbums(long tagId, PageParam pageParam, CallerParam callerParam) {
        List<AlbumVo> returnList = Lists.newArrayList();
        List<Long> ids = QmtSbcApis.getTProgramAlbums(tagId, pageParam, callerParam);
        List<TProgramAlbum> albums = QmtSbcApis.getTProgramAlbumsByIds(ids, callerParam);

        if (CollectionUtils.isNotEmpty(albums)) {
            for (TProgramAlbum album : albums) {
                AlbumVo albumVo = albumVoCreater.createAlbumVo(album, callerParam);
                returnList.add(albumVo);
            }
        }
        return returnList;
    }

}
