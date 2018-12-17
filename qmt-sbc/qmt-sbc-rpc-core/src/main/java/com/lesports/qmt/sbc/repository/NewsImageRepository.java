package com.lesports.qmt.sbc.repository;

import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.sbc.model.NewsImage;

import java.util.List;

/**
 * Created by ruiyuansheng on 2015/7/7.
 */
public interface NewsImageRepository extends MongoCrudRepository<NewsImage,Long> {
    
    List<Long> findIdsByNewsId(Long newsId);

    List<NewsImage> findByNewsId(Long newsId);
}
