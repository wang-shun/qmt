package com.lesports.qmt.sbc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.model.AlbumCreateResult;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.msg.core.ActionType;
import com.lesports.qmt.msg.core.BusinessType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.core.LeMessageBuilder;
import com.lesports.qmt.msg.producer.SwiftMessageApis;
import com.lesports.qmt.sbc.cache.TAlbumCache;
import com.lesports.qmt.sbc.model.Album;
import com.lesports.qmt.sbc.repository.AlbumRepository;
import com.lesports.qmt.sbc.service.AlbumService;
import com.lesports.qmt.sbc.service.support.AbstractSbcService;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.MD5Util;
import com.lesports.utils.MmsApis;
import com.lesports.utils.math.LeNumberUtils;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * Created by zhangxudong@le.com on 2016/10/25.
 */
@Service
public class AlbumServiceImpl extends AbstractSbcService<Album, Long> implements AlbumService {
    private static final String MMS_LESPORTS_PLATFORM = "upload";
    private static final String MMS_LESPORTS_PRIVATE_KEY = "4567";
    @Resource
    protected AlbumRepository albumRepository;

    @Resource
    protected TAlbumCache albumCache;

    @Override
    protected MongoCrudRepository<Album, Long> getInnerRepository() {
        return albumRepository;
    }

    @Override
    public Album findOne(Long id) {
        if (null == id) return null;
        return albumRepository.findOne(id);
    }

    @Override
    protected QmtOperationType getOpreationType(Album album) {
        if (null == album || LeNumberUtils.toLong(album.getId()) <= 0) {
            return QmtOperationType.CREATE;
        }
        return QmtOperationType.UPDATE;
    }

    @Override
    protected boolean doCreate(Album album) {
        if (null == album) return false;
        Map param = toVrsCreateAlbumParam(album);
        AlbumCreateResult albumCreateResult = MmsApis.saveAlbum(param);
        if (null == albumCreateResult || null == albumCreateResult.getData()){
            LOG.error("Fail to save album in vrs : {}", JSONObject.toJSONString(param));
        } else {
            album.setLeecoAid(albumCreateResult.getData().getPid());
        }
        album.setId(LeIdApis.nextId(IdType.ALBUM));
        album.setUpdateAt(LeDateUtils.formatYYYYMMDDHHMMSS(new Date()));
        boolean result = albumRepository.save(album);
        LOG.info("[create album][album title={}, result={}]", album.getTitle(), result);
        return result;
    }

    private Map toVrsCreateAlbumParam(Album album) {
        Map<String, Object> albumUploadParam = new HashMap<>();
        //final String MMS_LESPORTS_PLATFORM = "sport";

        albumUploadParam.put("token", MD5Util.md5(MMS_LESPORTS_PLATFORM + MMS_LESPORTS_PRIVATE_KEY));
        albumUploadParam.put("platform", MMS_LESPORTS_PLATFORM);
        albumUploadParam.put("copyrightSite", "650001"); //大陆
        albumUploadParam.put("site", "");
        albumUploadParam.put("nameCn", album.getTitle());
        albumUploadParam.put("description", album.getDesc());
        albumUploadParam.put("category", "4"); //体育
        albumUploadParam.put("downloadPlatform", "29"); //
        return albumUploadParam;
    }

    private Map toVrsUpdateAlbumParam(Album album) {
        Map<String, Object> albumUploadParam = new HashMap<>();
        //final String MMS_LESPORTS_PLATFORM = "sport";
        final String MMS_LESPORTS_PLATFORM = "upload";
        final String MMS_LESPORTS_PRIVATE_KEY = "4567";
        albumUploadParam.put("token", MD5Util.md5(MMS_LESPORTS_PLATFORM + MMS_LESPORTS_PRIVATE_KEY + album.getLeecoAid()));
        albumUploadParam.put("platform", MMS_LESPORTS_PLATFORM);
        albumUploadParam.put("copyrightSite", "650001"); //大陆
        albumUploadParam.put("id", album.getLeecoAid());
        albumUploadParam.put("updateType", 1);
        albumUploadParam.put("sourceId", "20");
        albumUploadParam.put("platform", "420001");
        albumUploadParam.put("category", "4");
        albumUploadParam.put("downloadPlatform", "29");

        albumUploadParam.put("site", "");
        albumUploadParam.put("nameCn", album.getTitle());
        albumUploadParam.put("description", album.getDesc());
        return albumUploadParam;
    }

    @Override
    protected boolean doAfterCreate(Album album) {
        albumCache.delete(album.getId());
        List<BusinessType> businessTypes = Lists.newArrayList();
        if (!indexEntity(album.getId(), IdType.ALBUM.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create()
                .setEntityId(album.getId())
                .setIdType(IdType.ALBUM)
                .setBusinessTypes(ActionType.ADD, businessTypes)
                .build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doUpdate(Album album) {
//        if (null == album) return false;
//        album.setUpdateAt(LeDateUtils.formatYYYYMMDDHHMMSS(new Date()));
//        Album orig = null;
//        if (null == album.getId())
//            album.setId(LeIdApis.nextId(IdType.ALBUM));
//        else
//            orig = albumRepository.findOne(album.getId());
//
//        boolean result = false;
//        if (null != orig) {
//            LeBeanUtils.copyNotEmptyPropertiesQuietly(orig, album);
//            result = albumRepository.save(orig);
//        } else
//            result = albumRepository.save(album);
        //
        Map param = toVrsUpdateAlbumParam(album);
        AlbumCreateResult albumCreateResult = MmsApis.saveAlbum(param);
        if (null == albumCreateResult || null == albumCreateResult.getData()) {
            LOG.error("Fail to save album to vrs : {}", JSONObject.toJSONString(param));
        }

        boolean result = albumRepository.save(album);
        LOG.info("[create album][album title={}, result={}]", album.getTitle(), result);
        return result;
    }

    @Override
    protected boolean doAfterUpdate(Album album) {
        albumCache.delete(album.getId());
        List<BusinessType> businessTypes = Lists.newArrayList();
        if (!indexEntity(album.getId(), IdType.ALBUM.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create()
                .setEntityId(album.getId())
                .setIdType(IdType.ALBUM)
                .setBusinessTypes(ActionType.UPDATE, businessTypes)
                .build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doDelete(Long id) {
        if (null == id) return false;
//        AlbumCreateResult albumCreateResult = MmsApis.saveAlbum(toVrsUpdateAlbumParam(album));
//        if(null == albumCreateResult || null == albumCreateResult.getData()) return false;

        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        Update up = update("deleted", true).set("update_at", now);
        boolean result = albumRepository.update(id, up);
        LOG.info("[delete album][album id={}, result={}]", id, result);
        return result;
    }

    @Override
    protected boolean doAfterDelete(Album album) {
        if (null == album) return false;
        return albumCache.delete(album.getId());
    }

    @Override
    protected Album doFindExistEntity(Album album) {
        if (null == album || null == album.getId()) return null;
        return this.albumRepository.findOne(album.getId());
    }
}
