package com.lesports.qmt.config.repository;

import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.config.model.Tag;

/**
 * User: ellios
 * Time: 15-7-20 : 下午5:29
 */
public interface TagRepository extends MongoCrudRepository<Tag, Long> {
}
