package com.lesports.qmt.userinfo.repository;

import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.userinfo.model.Workbench;

/**
 * Created by denghui on 2017/2/15.
 */
public interface WorkbenchRepository extends MongoCrudRepository<Workbench, Long> {

    Workbench getByCreator(String creator);
}
