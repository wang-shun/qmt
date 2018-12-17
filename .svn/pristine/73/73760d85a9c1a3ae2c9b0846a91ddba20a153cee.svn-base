package com.lesports.qmt.config.service.impl;

import com.lesports.qmt.config.client.QmtClientPlatformInternalApis;
import com.lesports.qmt.config.model.ClientPlatform;
import com.lesports.qmt.config.service.ClientPlatformWebService;
import com.lesports.qmt.config.vo.ClientPlatformVo;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.query.InternalQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangxudong@le.com on 2016/10/29.
 */
@Service("clientPlatformWebService")
public class ClientPlatformWebServiceImpl implements ClientPlatformWebService {

    private static final Logger LOG = LoggerFactory.getLogger(ClientPlatformWebServiceImpl.class);

    @Override
    public QmtPage<ClientPlatformVo> getAll() {
        InternalQuery internalQuery = new InternalQuery();

        long count = QmtClientPlatformInternalApis.countClientPlatformByQuery(internalQuery);
        List<ClientPlatform> data = QmtClientPlatformInternalApis.getClientPlatformsByQuery(internalQuery);
        return new QmtPage(data, new QmtPageParam(), count);
    }


    @Override
    public boolean save(ClientPlatformVo entity) {
//        long id = QmtClientPlatformCopyrightInternalApis.s(entity.toCopyright());
//        return id > 0;
        return false;
    }

    @Override
    public Long saveWithId(ClientPlatformVo entity) {
        return null;
    }

    @Override
    public ClientPlatformVo findOne(Long id) {
        return new ClientPlatformVo(QmtClientPlatformInternalApis.getClientPlatformById(id));
    }

    @Override
    public boolean delete(Long id) {
        return QmtClientPlatformInternalApis.deleteClientPlatform(id);
    }

}
