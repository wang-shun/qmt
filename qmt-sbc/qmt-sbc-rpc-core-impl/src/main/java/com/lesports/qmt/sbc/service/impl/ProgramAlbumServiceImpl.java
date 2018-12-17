package com.lesports.qmt.sbc.service.impl;

import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.msg.core.ActionType;
import com.lesports.qmt.msg.core.BusinessType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.core.LeMessageBuilder;
import com.lesports.qmt.msg.producer.SwiftMessageApis;
import com.lesports.qmt.sbc.api.dto.TProgramAlbum;
import com.lesports.qmt.sbc.cache.TProgramAlbumCache;
import com.lesports.qmt.sbc.converter.TProgramAlbumConverter;
import com.lesports.qmt.sbc.model.ProgramAlbum;
import com.lesports.qmt.sbc.repository.ProgramAlbumRepository;
import com.lesports.qmt.sbc.service.ProgramAlbumService;
import com.lesports.qmt.sbc.service.support.AbstractSbcService;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.PageUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * Created by denghui on 2016/11/15.
 */
@Service("programAlbumService")
public class ProgramAlbumServiceImpl extends AbstractSbcService<ProgramAlbum, Long> implements ProgramAlbumService {

    @Resource
    private ProgramAlbumRepository programAlbumRepository;
    @Resource
    private TProgramAlbumCache programAlbumCache;
    @Resource
    private TProgramAlbumConverter programAlbumConverter;

    @Override
    protected MongoCrudRepository<ProgramAlbum, Long> getInnerRepository() {
        return programAlbumRepository;
    }

    @Override
    protected QmtOperationType getOpreationType(ProgramAlbum entity) {
        if (LeNumberUtils.toLong(entity.getId()) <= 0) {
            return QmtOperationType.CREATE;
        }
        return QmtOperationType.UPDATE;
    }

    @Override
    protected boolean doCreate(ProgramAlbum entity) {
        entity.setId(LeIdApis.nextId(IdType.PROGRAM_ALBUM));
        entity.setDeleted(false);
        return programAlbumRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(ProgramAlbum entity) {
        programAlbumCache.delete(entity.getId());
        List<BusinessType> businessTypes = Lists.newArrayList();
        if (!indexEntity(entity.getId(), IdType.PROGRAM_ALBUM.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create()
                .setEntityId(entity.getId())
                .setIdType(IdType.PROGRAM_ALBUM)
                .setBusinessTypes(ActionType.ADD, businessTypes)
                .build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doUpdate(ProgramAlbum entity) {
        return programAlbumRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(ProgramAlbum entity) {
        programAlbumCache.delete(entity.getId());
        List<BusinessType> businessTypes = Lists.newArrayList();
        if (!indexEntity(entity.getId(), IdType.PROGRAM_ALBUM.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create()
                .setEntityId(entity.getId())
                .setIdType(IdType.PROGRAM_ALBUM)
                .setBusinessTypes(ActionType.UPDATE, businessTypes)
                .build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doDelete(Long id) {
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        Update up = update("deleted", true).set("update_at", now);
        return programAlbumRepository.update(id, up);
    }

    @Override
    protected boolean doAfterDelete(ProgramAlbum entity) {
        programAlbumCache.delete(entity.getId());
        List<BusinessType> businessTypes = Lists.newArrayList();
        if (!indexEntity(entity.getId(), IdType.PROGRAM_ALBUM.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create()
                .setEntityId(entity.getId())
                .setIdType(IdType.PROGRAM_ALBUM)
                .setBusinessTypes(ActionType.DELETE, businessTypes)
                .build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected ProgramAlbum doFindExistEntity(ProgramAlbum entity) {
        return findOne(entity.getId());
    }

    @Override
    public List<TProgramAlbum> getTProgramAlbumsByIds(List<Long> ids, CallerParam caller) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        List<TProgramAlbum> vos = Lists.newArrayListWithExpectedSize(ids.size());
        for (Long id : ids) {
            TProgramAlbum vo = getTProgramAlbumById(id, caller);
            if (vo != null) {
                vos.add(vo);
            }
        }
        return vos;
    }

    @Override
    public TProgramAlbum getTProgramAlbumById(long id, CallerParam caller) {
        TProgramAlbum vo = programAlbumCache.findOne(id);
        if (vo == null) {
            ProgramAlbum album = programAlbumRepository.findOne(id);
            if (album == null) {
                LOG.warn("fail to getTProgramAlbumById, album no exists. id : {}, caller : {}", id, caller);
                return null;
            }
            vo = programAlbumConverter.toDto(album);
            if (vo == null) {
                LOG.warn("fail to getTProgramAlbumById, convert vo fail. id : {}, caller : {}", id, caller);
                return null;
            }
            programAlbumCache.save(vo);
        }
        return vo;
    }

    @Override
    public List<Long> getTProgramAlbums(long tagId, Pageable pageable, CallerParam caller) {
        Query q = query(where("deleted").is(false));
        Pageable validPageable = PageUtils.getValidPageable(pageable);
        PageRequest pageRequest = new PageRequest(validPageable.getPageNumber(), validPageable.getPageSize(),
                new Sort(Sort.Direction.DESC, "order","start_time"));
        q.with(pageRequest);
        if (tagId > 0) {
            q.addCriteria(where("related_ids").is(tagId));
        }
        List<Long> ids = programAlbumRepository.findIdByQuery(q);
        return ids;
    }
}
