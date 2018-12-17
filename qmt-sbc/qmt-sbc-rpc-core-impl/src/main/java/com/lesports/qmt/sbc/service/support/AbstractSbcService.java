package com.lesports.qmt.sbc.service.support;

import com.google.common.collect.Lists;
import com.lesports.LeConstants;
import com.lesports.api.common.*;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.qmt.config.api.dto.DictEntryTopType;
import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.sbc.api.common.TvLicence;
import com.lesports.qmt.sbc.api.service.GetRelatedNewsParam;
import com.lesports.qmt.sbc.api.service.LiveShowStatusParam;
import com.lesports.qmt.sbc.api.service.LiveTypeParam;
import com.lesports.qmt.sbc.helper.CallerHelper;
import com.lesports.qmt.service.support.AbstractQmtService;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Resource;
import java.io.Serializable;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * User: ellios
 * Time: 16-3-20 : 上午11:03
 */
abstract public class AbstractSbcService<T extends QmtModel, ID extends Serializable> extends AbstractQmtService<T, ID> {

    protected static final Logger LOG = LoggerFactory.getLogger(AbstractSbcService.class);

    public static final long STAR_LEVEL_THREE = 3645701000l;
    public static final long STAR_LEVEL_TWO = 3645601000l;

    @Resource
    private CallerHelper callerHelper;

    protected Criteria getCallerPayCriteria(long callerId) {
        Criteria callerCriteria = null;
        if (callerHelper.needFilterCaller(callerId)) {
            Platform platform = callerHelper.getPlatformOfCaller(callerId);
            if (platform == null) {
                throw new IllegalArgumentException("illegal callerId : " + callerId);
            }
            callerCriteria = where("pay_platforms").is(platform);
        }
        return callerCriteria;
    }

    protected Criteria getCallerCriteria(long callerId) {
        Criteria callerCriteria = null;
        if (callerHelper.needFilterCaller(callerId)) {
            Platform platform = callerHelper.getPlatformOfCaller(callerId);
            if (platform == null) {
                throw new IllegalArgumentException("illegal callerId : " + callerId);
            }
            callerCriteria = where("play_platforms").is(platform);
        }
        return callerCriteria;
    }

    protected Criteria getCallerCriteria4News(long callerId) {
        Criteria callerCriteria = null;
        if (callerHelper.needFilterCaller(callerId)) {
            Platform platform = callerHelper.getPlatformOfCaller(callerId);
            if (platform == null) {
                throw new IllegalArgumentException("illegal callerId : " + callerId);
            }
            callerCriteria = where("platforms").is(platform);
        }
        return callerCriteria;
    }

    protected CriteriaDefinition getLiveShowStatusCriteria(LiveShowStatusParam liveShowStatusParam) {
        if (liveShowStatusParam == null) {
            return null;
        }
        switch (liveShowStatusParam) {
            case LIVE_NOT_START:
                return where("status").is(LiveShowStatus.LIVE_NOT_START);
            case LIVING:
                return where("status").is(LiveShowStatus.LIVING);
            case LIVE_END:
                return where("status").is(LiveShowStatus.LIVE_END);
            case LIVE_NOT_END:
                return where("status").in(Lists.newArrayList(LiveShowStatus.LIVING, LiveShowStatus.LIVE_NOT_START));
        }
        return null;
    }

    protected void addSupportLicenceCriteria(Query q, CallerParam caller) {
        if (caller.getCallerId() == LeConstants.LESPORTS_TV_CIBN_CALLER_CODE) {
            q.addCriteria(where("support_licences").is(TvLicence.CIBN));
        } else if (caller.getCallerId() == LeConstants.LESPORTS_TV_ICNTV_CALLER_CODE) {
            q.addCriteria(where("support_licences").is(TvLicence.ICNTV));
        } else if (caller.getCallerId() == LeConstants.LESPORTS_TV_WASU_CALLER_CODE) {
            q.addCriteria(where("support_licences").is(TvLicence.WASU));
        } else if (caller.getCallerId() == LeConstants.LESPORTS_TCL_CIBN_CALLER_CODE) {
            q.addCriteria(where("support_licences").is(TvLicence.CIBN));
        }

        if (caller.getPubChannel() == PubChannel.TCL) {//TCL过滤英超
            q.addCriteria(where("related_ids").ne(100004008L));
        }
    }

    protected void addRelatedNewsCriteriaToQuery(Query q, GetRelatedNewsParam p, CallerParam caller) {
        if (p == null) {
            return;
        }
        q.addCriteria(where("online").is(PublishStatus.ONLINE));
        if (CollectionUtils.isNotEmpty(p.getRelatedIds())) {
            q.addCriteria(where("related_ids").in(p.getRelatedIds()));
        }
        addSupportLicenceCriteria(q, caller);
        if (CollectionUtils.isNotEmpty(p.getTypes())) {
            q.addCriteria(where("type").in(p.getTypes()));
        }
        Criteria callerCriteria = getCallerCriteria4News(caller.getCallerId());
        if (callerCriteria != null) {
            q.addCriteria(callerCriteria);
        }
        //获取付费新闻
        if (p.getMember() == 1) {
            Criteria callerPayCriteria = getCallerPayCriteria(caller.getCallerId());
            if (callerPayCriteria != null) {
                q.addCriteria(callerPayCriteria);
            }
        }
        if (p.getStar() == 1) {
            q.addCriteria(where("star_level").is(STAR_LEVEL_THREE));
        } else if (p.getStar() == 2) {
            q.addCriteria(where("star_level").gte(STAR_LEVEL_TWO));
        }
    }

    protected CriteriaDefinition getLiveCriteria5CallerFilter(LiveTypeParam liveTypeParam, long callerId) {
        if (liveTypeParam == null) {
            return null;
        }
        if (!callerHelper.needFilterCaller(callerId)) {
            return getLiveCriteria(liveTypeParam);
        }
        Long splatId = callerHelper.getSplatIdOfCaller(callerId);

        if (null == splatId) {
            throw new IllegalArgumentException("illegal callerId : " + callerId);
        }
        return doGetLiveCriteria5CallerFilter(liveTypeParam, splatId);
    }

    protected CriteriaDefinition getLiveCriteria(LiveTypeParam liveTypeParam) {
        if (liveTypeParam == null) {
            return null;
        }
        switch (liveTypeParam) {
            case ONLY_LIVE:
                return where("has_live").is(true);
//            case ONLY_KEY:
//                return where("key").is(true);
            case LIVE_OR_KEY:
                return where("has_live").is(true);
            case LIVE_OR_TLIVE:
                return new Criteria().orOperator(where("has_live").is(true), where("has_text_live").is(true));
            case LIVE_TLIVE_KEY:
                return new Criteria().orOperator(where("has_live").is(true), where("has_text_live").is(true));
            case NOT_ONLY_LIVE:
                return null;
        }
        return null;
    }

    //和用户平台相关的过滤直播条件的查询
    private CriteriaDefinition doGetLiveCriteria5CallerFilter(LiveTypeParam liveTypeParam, Long splatId) {
        switch (liveTypeParam) {
            case ONLY_LIVE:
                return new Criteria().andOperator(where("has_live").is(true), where("live_streams.play_platforms").is(splatId));
            case NOT_TLIVE:
                return where("has_text_live").is(false);
//            case ONLY_APP_HOMEPAGE_RECOMMEND:
//                return new Criteria().andOperator(where("is_app_recommend").is(true), where("live_platforms").is(platform));
//            case ONLY_KEY:
//                return new Criteria().andOperator(where("key").is(true));
            case LIVE_OR_KEY:
                return new Criteria().andOperator(where("has_live").is(true), where("live_streams.play_platforms").is(splatId));
            case LIVE_OR_TLIVE:
                return new Criteria().orOperator(new Criteria().andOperator(where("has_live").is(true),
                                where("live_streams.play_platforms").is(splatId)),
                        where("has_text_live").is(true)
                );
            case LIVE_TLIVE_KEY://目前同LIVE_OR_TLIVE
                return new Criteria().orOperator(new Criteria().andOperator(where("has_live").is(true),
                                where("live_streams.play_platforms").is(splatId)),
                        where("has_text_live").is(true)
                );
            case NOT_ONLY_LIVE:
                return null;
        }
        return null;
    }

    protected boolean addGameTypeToQuery(Query q, long gameType) {
        if (gameType <= 0) {
            return false;
        }
        IdType idType = LeIdApis.checkIdTye(gameType);
        if (idType == null) {
            LOG.warn("illegal gameType : {}", gameType);
            return false;
        }
        if (idType == IdType.COMPETITION) {
            q.addCriteria(where("cid").is(gameType));
        } else if (idType == IdType.DICT_ENTRY) {
            DictEntryTopType entryType = QmtConfigApis.getDictEntryTopType(gameType);
            if (entryType == DictEntryTopType.GAME_FIRST_TYPE) {
                q.addCriteria(where("game_f_type").is(gameType));
            }
            if (entryType == DictEntryTopType.CHANNEL) {
                q.addCriteria(where("channel_id").is(gameType));
            } else {
                LOG.warn("illegal gameType : {}", gameType);
                return false;
            }
            //赛事、赛程、球队、球员、自制、话题
        } else {
            q.addCriteria(where("related_ids").is(gameType));
        }
        return true;
    }

    protected void addCountryAndLanguageCriteria(Query q, CallerParam caller) {
        CriteriaDefinition countryCriteria = getRadioCountryCriteria(caller);
        if (countryCriteria != null) {
            q.addCriteria(countryCriteria);
        }
        CriteriaDefinition languageCriteria = getRadioLanguageCriteria(caller);
        if (languageCriteria != null) {
            q.addCriteria(languageCriteria);
        }
    }

    protected CriteriaDefinition getRadioCountryCriteria(CallerParam caller) {
        if (caller.getCountry() == null) {
            return null;
        }
        return where("allow_country").is(caller.getCountry());
    }

    protected CriteriaDefinition getRadioLanguageCriteria(CallerParam caller) {
        if (caller.getLanguage() == null) {
            return null;
        }
        return where("language_code").is(caller.getLanguage());
    }
}
