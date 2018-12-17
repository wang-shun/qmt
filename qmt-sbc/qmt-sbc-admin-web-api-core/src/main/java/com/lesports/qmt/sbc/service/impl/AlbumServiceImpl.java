package com.lesports.qmt.sbc.service.impl;

import com.lesports.qmt.sbc.client.QmtSbcAlbumInternalApis;
import com.lesports.qmt.sbc.converter.AlbumVoConverter;
import com.lesports.qmt.sbc.model.Album;
import com.lesports.qmt.sbc.param.AlbumParam;
import com.lesports.qmt.sbc.service.AlbumService;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxudong@le.com on 2016/10/25.
 */
@Service
public class AlbumServiceImpl implements AlbumService {

    @Resource
    private AlbumVoConverter albumVoConverter;

    @Override
    public long save(AlbumParam albumParam) {
        if (null == albumParam) return 0L;
        //todo 同步到媒资
        Album album = albumVoConverter.toAlbum(albumParam);
        if(null == album.getId() || 0 == album.getId()) {
            //TODO:这是创建专辑，需要调用大乐视的创建专辑接口
        } else {
            //TODO:这是修改专辑，需要调用大乐视的修改专辑接口
        }
        //TODO:回写大乐视的专辑ID
        return QmtSbcAlbumInternalApis.saveAlbum(album);
    }

    @Override
    public Album getAlbumById(long id) {
        if (0 == id) return null;
        return QmtSbcAlbumInternalApis.getAlbumById(id);
    }

    @Override
    public List<Album> getAlbumByIds(String ids) {
        if(true == StringUtils.isEmpty(ids)) return null;
        List<Long> idss = new ArrayList<>();
        try {
            String[] idpieces = ids.split(",");
            for (String idpiece : idpieces) {
                Long id = Long.parseLong(idpiece);
                idss.add(id);
            }
            if(true == CollectionUtils.isEmpty(idss)) return null;
            return QmtSbcAlbumInternalApis.getAlbumByIds(idss);
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public List<Album> getAlbumByIds(List<Long> ids) {
        if(true == CollectionUtils.isEmpty(ids)) return null;
        return QmtSbcAlbumInternalApis.getAlbumByIds(ids);
    }

    @Override
    public Page<Album> getAlbumPage(int pageNumber, int pageSize, long id, String name, long gameFType, long cid) {
        InternalQuery query = new InternalQuery();
        if(id > 0)
            query.addCriteria(new InternalCriteria("_id").is(id));
        if(false == StringUtils.isEmpty(name))
            query.addCriteria(new InternalCriteria("title").regex(name));
        if(gameFType > 0)
            query.addCriteria(new InternalCriteria("game_f_type").is(gameFType));
        if(cid > 0)
            query.addCriteria(new InternalCriteria("cid").is(cid));
        query.with(new PageRequest(0, Integer.MAX_VALUE));
        long count = QmtSbcAlbumInternalApis.getAlbumCountByQuery(query);
        if(count <= 0) return null;

        Pageable pageable = new PageRequest(pageNumber, pageSize, new Sort(new Sort.Order(Sort.Direction.DESC, "_id")));
        query.with(pageable);
        List<Album> res = QmtSbcAlbumInternalApis.getAlbumByQuery(query);
        if (false == CollectionUtils.isEmpty(res))
            return new PageImpl<>(res, pageable, count);
        return null;
    }
}
