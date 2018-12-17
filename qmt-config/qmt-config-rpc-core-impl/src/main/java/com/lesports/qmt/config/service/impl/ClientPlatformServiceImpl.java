package com.lesports.qmt.config.service.impl;

import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.config.model.ClientPlatform;
import com.lesports.qmt.config.repository.ClientPlatformRepository;
import com.lesports.qmt.config.service.ClientPlatformService;
import com.lesports.qmt.service.support.AbstractQmtService;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * Created by zhangxudong@le.com on 2015/7/2.
 */
@Service("clientPlatformService")
public class ClientPlatformServiceImpl extends AbstractQmtService<ClientPlatform, Long> implements ClientPlatformService {

//    private static final Logger LOG = LoggerFactory.getLogger(CopyrightServiceImpl.class);

    @Resource
    private ClientPlatformRepository clientPlatformRepository;

    @Override
    protected MongoCrudRepository<ClientPlatform, Long> getInnerRepository() {
        return this.clientPlatformRepository;
    }

    @Override
    protected QmtOperationType getOpreationType(ClientPlatform entity) {
        if (LeNumberUtils.toLong(entity.getId()) <= 0) {
            return QmtOperationType.CREATE;
        }
        return QmtOperationType.UPDATE;
    }

    @Override
    protected boolean doCreate(ClientPlatform entity) {
        if (null == entity) return false;
        entity.setId(LeIdApis.nextId(IdType.CLIENT_PLATFORM));
        return clientPlatformRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(ClientPlatform entity) {
        return true;
    }

    @Override
    protected boolean doUpdate(ClientPlatform entity) {

        boolean result = clientPlatformRepository.save(entity);
        return result;
    }

    @Override
    protected boolean doAfterUpdate(ClientPlatform entity) {
        return true;
    }

    @Override
    protected boolean doDelete(Long id) {
        if (null == id || id <= 0) return false;
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        Update up = update("deleted", true).set("update_at", now);
        return clientPlatformRepository.update(id, up);
    }

    @Override
    protected boolean doAfterDelete(ClientPlatform entity) {
        return true;
    }

    @Override
    protected ClientPlatform doFindExistEntity(ClientPlatform entity) {
        if (null == entity) return new ClientPlatform();
        return clientPlatformRepository.findOne(entity.getId());
    }
}
