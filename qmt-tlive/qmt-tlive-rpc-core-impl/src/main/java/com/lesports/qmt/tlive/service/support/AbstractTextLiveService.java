package com.lesports.qmt.tlive.service.support;

import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.service.support.AbstractQmtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * User: ellios
 * Time: 16-3-20 : 上午11:03
 */
abstract public class AbstractTextLiveService<T extends QmtModel, ID extends Serializable> extends AbstractQmtService<T, ID> {

    protected static final Logger LOG = LoggerFactory.getLogger(AbstractTextLiveService.class);


}
