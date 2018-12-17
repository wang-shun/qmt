package com.lesports.qmt.config.repository;


import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.config.model.Caller;

/**
 * Created by gengchengliang on 2015/9/18.
 */
public interface CallerRepository extends MongoCrudRepository<Caller, Long> {

	Caller findByCallerId(long callerId);
}
