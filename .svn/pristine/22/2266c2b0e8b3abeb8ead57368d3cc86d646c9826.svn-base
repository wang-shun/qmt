package com.lesports.qmt.sbd.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.sbd.SbdPlayerInternalApis;
import com.lesports.qmt.sbd.adapter.PlayerAdapter;
import com.lesports.qmt.sbd.api.common.TeamType;
import com.lesports.qmt.sbd.converter.PlayerVoConverter;
import com.lesports.qmt.sbd.model.Player;
import com.lesports.qmt.sbd.service.PlayerWebService;
import com.lesports.qmt.sbd.service.support.AbstractSbdWebService;
import com.lesports.qmt.sbd.utils.QmtSearchApis;
import com.lesports.qmt.sbd.utils.QmtSearchData;
import com.lesports.qmt.sbd.vo.PlayerVo;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import com.lesports.utils.LeStringUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by lufei on 2016/10/27.
 */
@Component("playerWebService")
public class PlayerWebServiceImpl extends AbstractSbdWebService<PlayerVo, Long> implements PlayerWebService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerWebServiceImpl.class);

    @Resource
    private PlayerVoConverter playerVoConverter;

    @Resource
    private PlayerAdapter playerAdapter;

    @Override
    public PlayerVo findOne(Long id, CallerParam caller) {
        Player player = SbdPlayerInternalApis.getPlayerById(id);
        if (player == null) {
            LOGGER.warn("fail to findOne, not exists, id:{}, caller:{}", id, caller);
            return null;
        }
        return playerVoConverter.toTVo(player, caller);
    }

    @Override
    public boolean save(PlayerVo vo, CallerParam caller) {
        boolean isCreate = isCreateOp(vo);
        Player entity = vo.toModel();
        if (!isCreate) {
            Player old = SbdPlayerInternalApis.getPlayerById(entity.getId());
            if (old != null) {
                //防止刷掉national team number
                if (old.getTeamNumber() != null) {
                    Player.PlayingTeam national = old.getTeamNumber().get(TeamType.NATIONAL_TEAM);
                    if (national != null) {
                        entity.getTeamNumber().put(TeamType.NATIONAL_TEAM, national);
                    }
                }
                entity = playerAdapter.copyEditableProperties(old, vo);
            }
        }
        entity.setAllowCountries(getValidAllowCountries(entity.getAllowCountries(), null));
        entity.setMultiLangNames(setValueOfLanguage(entity.getMultiLangNames(), entity.getName(), isCreate ? null : caller.getLanguage()));
        entity.setMultiLangAreas(setValueOfLanguage(entity.getMultiLangAreas(), entity.getArea(), isCreate ? null : caller.getLanguage()));
        entity.setMultiLangCities(setValueOfLanguage(entity.getMultiLangCities(), entity.getCity(), isCreate ? null : caller.getLanguage()));
        entity.setMultiLangAbbrs(setValueOfLanguage(entity.getMultiLangAbbrs(), entity.getAbbreviation(), isCreate ? null : caller.getLanguage()));
        // set default value
        entity.setName(getDefaultValueOfMultiLang(entity.getMultiLangNames()));
        entity.setArea(getDefaultValueOfMultiLang(entity.getMultiLangAreas()));
        entity.setCity(getDefaultValueOfMultiLang(entity.getMultiLangCities()));
        entity.setAbbreviation(getDefaultValueOfMultiLang(entity.getMultiLangAbbrs()));
        long id = SbdPlayerInternalApis.savePlayer(entity, true);
        return setEntityId(entity, id);
    }

    @Override
    public boolean save(PlayerVo entity) {
        return false;
    }

    @Override
    public Long saveWithId(PlayerVo entity) {
        return null;
    }

    @Override
    public PlayerVo findOne(Long id) {
        Player player = SbdPlayerInternalApis.getPlayerById(id);
        return new PlayerVo(player);
    }

    @Override
    public boolean delete(Long id) {
        return SbdPlayerInternalApis.deletePlayer(id);
    }

    @Override
    public long countByQuery(InternalQuery query, CallerParam caller) {
        query = addCountryCriteria(query, caller);
        return SbdPlayerInternalApis.countPlayersByQuery(query);
    }

    protected InternalQuery buildCriteria(Map<String, Object> params, QmtPageParam pageParam) {
        InternalQuery query = new InternalQuery();
        if (MapUtils.isNotEmpty(params)) {
            long id = MapUtils.getLongValue(params, "id");
            if (id > 0) {
                query.addCriteria(InternalCriteria.where("id").is(id));
            }
            String name = MapUtils.getString(params, "name");
            if (StringUtils.isNotBlank(name)) {
                Pattern pattern = getNamePattern(name);
                query.addCriteria(InternalCriteria.where("multi_lang_names.value").regex(pattern));
            }
            long gameFType = MapUtils.getLongValue(params, "gameFType");
            if (gameFType > 0) {
                query.addCriteria(InternalCriteria.where("game_f_type").is(gameFType));
            }

            long cid = MapUtils.getLongValue(params, "cid");
            if (cid > 0) {
                query.addCriteria(InternalCriteria.where("cids").is(cid));
            }

        }
        query.addCriteria(InternalCriteria.where("deleted").is(false));
        query.with(getValidPageable(pageParam));
        return query;
    }

    @Override
    public List<PlayerVo> findByQuery(InternalQuery query, CallerParam caller) {
        query = addCountryCriteria(query, caller);
        List<Player> players = SbdPlayerInternalApis.getPlayersByQuery(query);
        return Lists.transform(players, new Function<Player, PlayerVo>() {
            @Nullable
            @Override
            public PlayerVo apply(@Nullable Player input) {
                return playerVoConverter.toTVo(input, caller);
            }
        });
    }

    @Override
    public QmtPage<PlayerVo> list(Map<String, Object> params, QmtPageParam pageParam, CallerParam caller) {
        params.put("sortBy", "id");
        params.put("desc", true);
        QmtSearchData qmtSearchData = QmtSearchApis.searchData(params, Player.class);
        List<Player> players = qmtSearchData.getRows();
        List<PlayerVo> playerVos = Lists.transform(players, new Function<Player, PlayerVo>() {
            @Nullable
            @Override
            public PlayerVo apply(@Nullable Player input) {
                return playerVoConverter.toTVo(input, caller);
            }
        });
        return new QmtPage<PlayerVo>(playerVos, pageParam, qmtSearchData.getTotal());
    }

    @Override
    public boolean updateOnlineStatus(long id, boolean online, CallerParam caller) {
        Player player = SbdPlayerInternalApis.getPlayerById(id);
        if (player != null) {
            player.setOnlineLanguages(setOnlineLanguages(player.getOnlineLanguages(), online, caller.getLanguage()));
            return SbdPlayerInternalApis.savePlayer(player, true) > 0;
        }
        return false;
    }


    @Override
    protected boolean isCreateOp(PlayerVo entity) {
        if (LeNumberUtils.toLong(entity.getId()) > 0) {
            return false;
        }
        return true;
    }

    @Override
    public List<PlayerVo> getPlayersByIds(String ids, CallerParam caller) {
        List<Player> players = SbdPlayerInternalApis.getPlayersByIds(LeStringUtils.commaString2LongList(ids));
        return Lists.transform(players, new Function<Player, PlayerVo>() {
            @Nullable
            @Override
            public PlayerVo apply(@Nullable Player input) {
                return playerVoConverter.toTVo(input, caller);
            }
        });
    }
}
