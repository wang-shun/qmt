package com.lesports.qmt.sbc.service;

import com.lesports.qmt.sbc.model.Album;
import com.lesports.qmt.sbc.param.AlbumParam;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by zhangxudong@le.com on 2016/10/25.
 */
public interface AlbumService {

    long save(AlbumParam albumParam);

    Album getAlbumById(long id);

    List<Album> getAlbumByIds(String ids);

    List<Album> getAlbumByIds(List<Long> ids);

    Page<Album> getAlbumPage(int pageNumber, int pageSize, long id, String name, long gameFType, long cid);
}
