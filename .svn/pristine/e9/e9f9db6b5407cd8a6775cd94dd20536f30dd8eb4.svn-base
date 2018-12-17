package com.lesports.qmt.userinfo.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.userinfo.model.Workbench;
import com.lesports.qmt.userinfo.repository.WorkbenchRepository;
import com.lesports.qmt.userinfo.service.WorkbenchService;
import com.lesports.qmt.userinfo.service.support.AbstractUserinfoService;
import com.lesports.qmt.userinfo.web.vo.SubscriptionVo;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Created by denghui on 2017/2/15.
 */
@Service("workbenchService")
public class WorkbenchServiceImpl extends AbstractUserinfoService<Workbench, Long> implements WorkbenchService {

    @Resource
    private WorkbenchRepository workbenchRepository;

    @Override
    protected MongoCrudRepository<Workbench, Long> getInnerRepository() {
        return workbenchRepository;
    }

    @Override
    protected QmtOperationType getOpreationType(Workbench entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return QmtOperationType.UPDATE;
        }
        return QmtOperationType.CREATE;
    }

    @Override
    protected boolean doCreate(Workbench entity) {
        entity.setId(LeIdApis.nextId(IdType.USERINFO_WORKBENCH));
        return workbenchRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(Workbench entity) {
        return true;
    }

    @Override
    protected boolean doUpdate(Workbench entity) {
        return workbenchRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(Workbench entity) {
        return true;
    }

    @Override
    protected boolean doDelete(Long id) {
        return workbenchRepository.delete(id);
    }

    @Override
    protected boolean doAfterDelete(Workbench entity) {
        return true;
    }

    @Override
    protected Workbench doFindExistEntity(Workbench entity) {
        return findOne(entity.getId());
    }

    @Override
    public boolean subscribe(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return true;
        }
        String creator = getCreator();
        Workbench workbench = workbenchRepository.getByCreator(creator);
        if (workbench == null) {
            workbench = new Workbench();
            workbench.setCreator(creator);
        }
        for (Long id : ids) {
            if (LeIdApis.checkIdTye(id) == IdType.RESOURCE) {
                workbench.addResourceId(id);
            }
        }
        boolean result = save(workbench);
        LOG.info("subscribe ids:{}, user:{}, result:{}", ids, creator, result);
        return result;
    }

    @Override
    public boolean unsubscribe(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return true;
        }
        String creator = getCreator();
        Workbench workbench = workbenchRepository.getByCreator(creator);
        if (workbench == null) {
            return true;
        }
        for (Long id : ids) {
            if (LeIdApis.checkIdTye(id) == IdType.RESOURCE) {
                workbench.getResourceIds().remove(id);
            }
        }
        boolean result = save(workbench, true);
        LOG.info("unsubscribe ids:{}, user:{}, result:{}", ids, creator, result);
        return result;
    }

    @Override
    public QmtPage<SubscriptionVo> listSubscription(QmtPageParam pageParam) {
        String creator = getCreator();
        Workbench workbench = workbenchRepository.getByCreator(creator);
        if (workbench == null) {
            return new QmtPage<>(Collections.EMPTY_LIST, pageParam, 0);
        }

        List<Long> resourceIds = getPage(workbench.getResourceIds(), pageParam.toPageable());
        return new QmtPage<>(Lists.transform(resourceIds, new Function<Long, SubscriptionVo>() {
            @Nullable
            @Override
            public SubscriptionVo apply(@Nullable Long input) {
                SubscriptionVo vo = new SubscriptionVo();
                vo.setResourceId(input);
                return vo;
            }
        }), pageParam, workbench.getResourceIds().size());
    }

    private List<Long> getPage(List<Long> full, Pageable pageable) {
        int fromIndex = pageable.getPageNumber() * pageable.getPageSize();
        if (fromIndex >= full.size()) {
            return Collections.EMPTY_LIST;
        }
        int toIndex = (pageable.getPageNumber() + 1) * pageable.getPageSize();
        if (toIndex >= full.size()) {
            toIndex = full.size();
        }
        return full.subList(fromIndex, toIndex);
    }
}
