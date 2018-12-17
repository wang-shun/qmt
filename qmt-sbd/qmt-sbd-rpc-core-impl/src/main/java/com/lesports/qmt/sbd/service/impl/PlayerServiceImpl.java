package com.lesports.qmt.sbd.service.impl;

import com.alibaba.fastjson.JSON;
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
import com.lesports.qmt.sbd.api.dto.TPlayer;
import com.lesports.qmt.sbd.api.service.GetPlayers4SimpleSearchParam;
import com.lesports.qmt.sbd.cache.TPlayerCache;
import com.lesports.qmt.sbd.cache.TTeamSeasonCache;
import com.lesports.qmt.sbd.converter.TPlayerVoConverter;
import com.lesports.qmt.sbd.model.Player;
import com.lesports.qmt.sbd.repository.PlayerRepository;
import com.lesports.qmt.sbd.repository.TeamSeasonRepository;
import com.lesports.qmt.sbd.service.PlayerService;
import com.lesports.qmt.sbd.service.support.AbstractSbdService;
import com.lesports.qmt.sbd.utils.PinyinUtils;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.PageUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * User: lufei
 * Time: 2016-10-24 : 下午12:04
 */
@Service("playerService")
public class PlayerServiceImpl extends AbstractSbdService<Player, Long> implements PlayerService {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerServiceImpl.class);

    @Resource
    private PlayerRepository playerRepository;
    @Resource
    private TPlayerCache playerCache;
    @Resource
    private TPlayerVoConverter tPlayerVoConverter;
    @Resource
    private TeamSeasonRepository teamSeasonRepository;
    @Resource
    private TTeamSeasonCache teamSeasonCache;


    @Override
    public Player findOne(Long id) {
        return playerRepository.findOne(id);
    }

    @Override
    protected QmtOperationType getOpreationType(Player entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return QmtOperationType.UPDATE;
        }
        return QmtOperationType.CREATE;
    }

    @Override
    protected boolean doCreate(Player entity) {
        entity.setId(LeIdApis.nextId(IdType.PLAYER));
        entity.setDeleted(false);
        //设置首字母
        entity.setFirstLetter(PinyinUtils.getPinYin(entity.getName()).substring(0, 1));
        return playerRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(Player entity) {
        List<BusinessType> businessTypes = Lists.newArrayList(BusinessType.DATA_SYNC);
        if (!indexEntity(entity.getId(), IdType.PLAYER.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.PLAYER).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.ADD, businessTypes).
                build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doUpdate(Player entity) {
        //未设置首字母的话，设置首字母
        if(StringUtils.isNotEmpty(entity.getName()) && StringUtils.isEmpty(entity.getFirstLetter())){
            entity.setFirstLetter(PinyinUtils.getPinYin(entity.getName().toUpperCase()).substring(0, 1));
        }
        return playerRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(Player entity) {
        List<BusinessType> businessTypes = Lists.newArrayList(BusinessType.DATA_SYNC);
        if (!indexEntity(entity.getId(), IdType.PLAYER.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.PLAYER).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.UPDATE, businessTypes).
                build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected boolean doDelete(Long id) {
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        Update up = update("deleted", true).set("update_at", now);
        return playerRepository.update(id, up);
    }

    @Override
    protected boolean doAfterDelete(Player entity) {
        List<BusinessType> businessTypes = Lists.newArrayList(BusinessType.DATA_SYNC);
        if (!indexEntity(entity.getId(), IdType.PLAYER.toString())) {
            businessTypes.add(BusinessType.SEARCH_INDEX);
        }
        LeMessage message = LeMessageBuilder.create().
                setEntityId(entity.getId()).
                setIdType(IdType.PLAYER).
                setData(JSON.toJSONString(entity)).
                setBusinessTypes(ActionType.DELETE, businessTypes).
                build();
        SwiftMessageApis.sendMsgAsync(message);
        return true;
    }

    @Override
    protected Player doFindExistEntity(Player entity) {
        return playerRepository.findOne(entity.getId());
    }


    @Override
    protected MongoCrudRepository getInnerRepository() {
        return playerRepository;
    }

    @Override
    public TPlayer getTPlayerById(long id, CallerParam caller) {
        TPlayer tPlayer = playerCache.findOne(id);
        if (tPlayer == null) {
            Player player = playerRepository.findOne(id);
            if (player == null) {
                LOG.warn("fail to getTPlayerById, player no exists. id : {}, caller : {}", id, caller);
                return null;
            }
            tPlayer = tPlayerVoConverter.toDto(player);
            if (tPlayer == null) {
                LOG.warn("fail to getTPlayerById, toTVo fail. id : {}, caller : {}", id, caller);
                return null;
            }
            playerCache.save(tPlayer);
        }
        tPlayerVoConverter.adaptDto(tPlayer, caller);
        return tPlayer;
    }

    @Override
    public List<TPlayer> getTPlayersByIds(List<Long> ids, CallerParam caller) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        List<TPlayer> players = Lists.newArrayListWithExpectedSize(ids.size());
        for (Long id : ids) {
            TPlayer player = getTPlayerById(id, caller);
            if (player != null) {
                players.add(player);
            }
        }
        return players;
    }

    @Override
    public List<Long> getPlayerIds4SimpleSearch(GetPlayers4SimpleSearchParam p, Pageable pageable, CallerParam caller) {
        Query q = query(where("deleted").is(false));
        if (p != null) {
            if (StringUtils.isNotBlank(p.getFirstLetter())) {
                q.addCriteria(where("first_letter").is(p.getFirstLetter()));
            }
            if (StringUtils.isNotBlank(p.getName())) {
                q.addCriteria(where("multi_lang_names.value").regex(p.getName()));
            }
            if(p.getLeciId() > 0) {
                q.addCriteria(where("leci.id").is(p.getLeciId()));
            }
            if(p.getCid() > 0 ){
                q.addCriteria(where("cids").is(p.getCid()));
            }
        }
        addInternationalCriteriaToQuery(q, caller);
        pageable = PageUtils.getValidPageable(pageable);
        q.with(pageable);
        return playerRepository.findIdByQuery(q);
    }

}