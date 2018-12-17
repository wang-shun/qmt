package com.lesports.qmt.sbd.converter;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.LeConstants;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.CountryCode;
import com.lesports.api.common.LanguageCode;
import com.lesports.qmt.config.client.QmtConfigDictInternalApis;
import com.lesports.qmt.config.model.DictEntry;
import com.lesports.qmt.converter.TDtoConverter;
import com.lesports.qmt.sbd.api.common.CompetitorType;
import com.lesports.qmt.sbd.api.common.ScopeType;
import com.lesports.qmt.sbd.api.dto.TTeamPlayer;
import com.lesports.qmt.sbd.api.dto.TTeamSeason;
import com.lesports.qmt.sbd.api.dto.TTopList;
import com.lesports.qmt.sbd.api.dto.TTopListItem;
import com.lesports.qmt.sbd.api.service.GetTeamSeasonsParam;
import com.lesports.qmt.sbd.model.*;
import com.lesports.qmt.sbd.repository.CompetitionRepository;
import com.lesports.qmt.sbd.repository.CompetitionSeasonRepository;
import com.lesports.qmt.sbd.repository.PlayerRepository;
import com.lesports.qmt.sbd.repository.TeamRepository;
import com.lesports.qmt.sbd.service.TeamSeasonService;
import com.lesports.qmt.sbd.utils.ImageUtil;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.LeProperties;
import com.lesports.utils.PageUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by denghui on 2016/3/24.
 */
@Component("tTopListVoConverter")
public class TTopListVoConverter implements TDtoConverter<TopList, TTopList> {

	protected static final String CUSTOM_TOPLIST_DOMAIN = LeProperties.getString("custom.toplist.domain", "");

    @Resource
    private TeamRepository teamRepository;
    @Resource
    private PlayerRepository playerRepository;
    @Resource
    private CompetitionRepository competitionRepository;
    @Resource
    private CompetitionSeasonRepository competitionSeasonRepository;
	@Resource
    private TeamSeasonService teamSeasonService;

    @Override
    public TTopList toDto(TopList topList) {
        Preconditions.checkNotNull(topList);
        TTopList tTopList = new TTopList();
        fillTTopList(tTopList, topList);
        return tTopList;
    }

    @Override
    public TTopList adaptDto(TTopList vo, CallerParam caller) {
        Preconditions.checkNotNull(vo);
        Preconditions.checkNotNull(caller);
		//如果自定义榜单的url不为空,则去拼接对应的域名和参数
		if (StringUtils.isNotBlank(vo.getUrl())) {
			adapterDomainNameAndParam(vo.getUrl(), caller, vo);
		}
        String name = CallerUtils.getValueOfLanguage(vo.getMultiLangNames(), caller.getLanguage());
        if (StringUtils.isNotEmpty(name)) {
            vo.setName(name);
        }

        String cname = CallerUtils.getValueOfLanguage(vo.getMultiLangCNames(), caller.getLanguage());
        if (StringUtils.isNotEmpty(cname)) {
            vo.setCname(cname);
        }

        String desc = CallerUtils.getValueOfLanguage(vo.getMultiLangDesc(), caller.getLanguage());
        if (StringUtils.isNotEmpty(desc)) {
            vo.setDesc(desc);
        }
        String typeName = CallerUtils.getValueOfLanguage(vo.getMultiLangTypeNames(), caller.getLanguage());
        if (StringUtils.isNotEmpty(typeName)) {
            vo.setTypeName(typeName);
        }
        vo.setMultiLangNamesIsSet(false);
        vo.setMultiLangDescIsSet(false);
        vo.setMultiLangCNamesIsSet(false);
        vo.setMultiLangTypeNamesIsSet(false);
        for (TTopListItem item : vo.getItems()) {
            String competitorName = CallerUtils.getValueOfLanguage(item.getMultiLangCompetitorNames(), caller.getLanguage());
            if (StringUtils.isNotEmpty(competitorName)) {
                item.setCompetitorName(competitorName);
            }
            String teamName = CallerUtils.getValueOfLanguage(item.getMultiLangTeamNames(), caller.getLanguage());
            if (StringUtils.isNotEmpty(teamName)) {
                item.setTeamName(teamName);
            }
            if(item.getCompetitorType() == CompetitorType.TEAM){
                String teamLogo = CallerUtils.getValueOfCountry(item.getMultiCounLogos(), caller.getCountry());
                if (StringUtils.isNotEmpty(teamLogo)) {
                    item.setTeamLogo(teamLogo);
                    item.setLogoUrl(teamLogo);
                }
            }

			String posName = CallerUtils.getValueOfLanguage(item.getMultiLangPositionNames(), caller.getLanguage());
			if (StringUtils.isNotEmpty(posName)) {
				item.setPosition(posName);
			}
			String teamOfficialName = CallerUtils.getValueOfLanguage(item.getMultiLangTeamOfficialNames(), caller.getLanguage());
			if (StringUtils.isNotEmpty(teamOfficialName)) {
				item.setTeamOfficialName(teamOfficialName);
			}
			item.setMultiLangPositionNamesIsSet(false);
            item.setMultiLangCompetitorNamesIsSet(false);
            item.setMultiLangTeamNamesIsSet(false);
            item.setMultiCounLogosIsSet(false);
			item.setMultiLangTeamOfficialNamesIsSet(false);
        }
        return vo;
    }

	private void adapterDomainNameAndParam(String url, CallerParam caller, TTopList topList) {
		//拼接对应的域名
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isNotBlank(CUSTOM_TOPLIST_DOMAIN) && caller.getCountry() != CountryCode.CN) {
			sb.append(CUSTOM_TOPLIST_DOMAIN).
					append(url.substring(url.substring(7).indexOf('/') + 7));
		} else {
			sb.append(url);
		}
		//添加语言和国家的参数
		String hasParamUrl = addCountryAndLanguageToUrl(sb, caller);
		topList.setUrl(hasParamUrl);
	}

	public static String addCountryAndLanguageToUrl (StringBuffer sb, CallerParam caller) {
		if (sb.toString().contains(LeConstants.QUESTION_MARK)) {
			sb.append(LeConstants.AMPERSAND).append("country=").append(caller.getCountry()).
					append(LeConstants.AMPERSAND).append("language=").append(caller.getLanguage());
		} else {
			sb.append(LeConstants.QUESTION_MARK).append("country=").append(caller.getCountry()).
					append(LeConstants.AMPERSAND).append("language=").append(caller.getLanguage());
		}
		return sb.toString();
	}

    private void fillTTopList(final TTopList tTopList, TopList topList) {
		final Long csid = topList.getCsid();
		tTopList.setId(topList.getId());
        tTopList.setCid(LeNumberUtils.toLong(topList.getCid()));
        tTopList.setCsid(LeNumberUtils.toLong(csid));
        Competition competition = competitionRepository.findOne(topList.getCid());
        if (competition != null) {
            tTopList.setCname(competition.getName());
            tTopList.setMultiLangCNames(competition.getMultiLangNames());
        }
        if (topList.getCsid() != null) {
            CompetitionSeason season = competitionSeasonRepository.findOne(topList.getCsid());
            if (season != null) {
                tTopList.setSeason(season.getSeason());
            }
        }

//        DictEntry entry = QmtConfigDictInternalApis.getDictById(topList.getScopeId());
//        topList.getScopeType().equals(ScopeType.)
//        if (topList.getGroup() != null) {
//            DictEntry entry = dictRepository.findOne(topList.getGroup());
//            if (entry != null) {
//                tTopList.setGroup(entry.getReadableName());
//            }
//        }
//        if (topList.getRound() != null) {
//            DictEntry entry = dictRepository.findOne(topList.getRound());
//            if (entry != null) {
//                tTopList.setRound(entry.getReadableName());
//            }
//        }
//        if (topList.getStage() != null) {
//            DictEntry entry = dictRepository.findOne(topList.getStage());
//            if (entry != null) {
//                tTopList.setStage(entry.getReadableName());
//            }
//        }
//        if (topList.getSubstation() != null) {
//            DictEntry entry = dictRepository.findOne(topList.getStage());
//            if (entry != null) {
//                tTopList.setSubstation(entry.getReadableName());
//            }
//        }

        tTopList.setType(topList.getType());
        tTopList.setUrl(topList.getUrl());
        tTopList.setDesc(topList.getDescription());
        tTopList.setMultiLangDesc(topList.getMultiLangDesc());
        //自定义榜单的名称
        tTopList.setName(topList.getName());
        tTopList.setMultiLangNames(topList.getMultiLangNames());
        //榜单排序
        tTopList.setOrder(LeNumberUtils.toInt(topList.getOrder()));
        DictEntry typeDictEntry = QmtConfigDictInternalApis.getDictById(topList.getType());
        if (typeDictEntry != null) {
            tTopList.setTypeName(typeDictEntry.getReadableName());
            tTopList.setMultiLangTypeNames(typeDictEntry.getMultiLangReadableNames());
        }
        List<TopList.TopListItem> topListItems = topList.getItems();
        List<TTopListItem> tTopListItems = Lists.transform(topListItems, new Function<TopList.TopListItem, TTopListItem>() {
            @Nullable
            @Override
            public TTopListItem apply(@Nullable TopList.TopListItem item) {
                TTopListItem tTopListItem = new TTopListItem();
                tTopListItem.setCompetitorId(item.getCompetitorId());
                tTopListItem.setCompetitorType(item.getCompetitorType());
                tTopListItem.setRank(LeNumberUtils.toInt(item.getRank()));
				Team team = null;
				if (item.getTeamId() != null) {
                    tTopListItem.setTeamId(item.getTeamId());
                    team = teamRepository.findOne(item.getTeamId());
                    if (team != null) {
                        tTopListItem.setTeamName(team.getAbbreviation());
                        tTopListItem.setMultiLangTeamNames(team.getMultiLangAbbrs());
                        if(team.getLogo() != null){
                            tTopListItem.setTeamLogo(ImageUtil.addLogoSuffix(team.getLogo().getUrl(), team.getLogo().getPath()));
                        }
                        if(CollectionUtils.isNotEmpty(team.getMultiCounLogos())) {
                            tTopListItem.setMultiCounLogos(team.getMultiCounLogos());
                        }
						tTopListItem.setTeamOfficialName(team.getName());
						if(CollectionUtils.isNotEmpty(team.getMultiLangNames())) {
							tTopListItem.setMultiLangTeamOfficialNames(team.getMultiLangNames());
						}
                    }
                }
                switch (item.getCompetitorType()) {
                    case PLAYER:
                        Player player = playerRepository.findOne(item.getCompetitorId());
                        if (player != null) {
                            if(player.getImage() != null){
                                tTopListItem.setLogoUrl(ImageUtil.addLogoSuffix(player.getImage().getUrl(), player.getImage().getPath()));
                            }
                            tTopListItem.setCompetitorName(player.getName());
                            tTopListItem.setMultiLangCompetitorNames(player.getMultiLangNames());
							if (LeNumberUtils.toInt(player.getPosition()) != 0) {
								tTopListItem.setPositionId(player.getPosition());
								DictEntry entry = QmtConfigDictInternalApis.getDictById(player.getPosition());
								if (entry != null) {
									tTopListItem.setPosition(entry.getReadableName());
									if (CollectionUtils.isNotEmpty(entry.getMultiLangNames())) {
										tTopListItem.setMultiLangPositionNames(entry.getMultiLangReadableNames());
									}
								}
							}
							//NBA的球员榜单需要球员号码
							if (team != null && LeNumberUtils.toInt(team.getCurrentCid()) == 44001) {
								CallerParam callerParam = new CallerParam();
								callerParam.setCallerId(1003);
								callerParam.setCountry(CountryCode.CN);
								callerParam.setLanguage(LanguageCode.ZH_CN);
								GetTeamSeasonsParam p = new GetTeamSeasonsParam();
								p.setCsid(csid);
								p.setTid(team.getId());
								List<Long> teamSeasonIds = teamSeasonService.getTeamSeasonIds(p, PageUtils.convertToPageable(PageUtils.createPageParam(0)), callerParam);
								if (CollectionUtils.isNotEmpty(teamSeasonIds)) {
									TTeamSeason tTeamSeason = teamSeasonService.getTTeamSeasonById(teamSeasonIds.get(0), callerParam);
									List<TTeamPlayer> players = tTeamSeason.getPlayers();
									if (CollectionUtils.isNotEmpty(players)) {
										for (TTeamPlayer tTeamPlayer : players) {
											if (tTeamPlayer.getPid() == player.getId()) {
												tTopListItem.setNumber(LeNumberUtils.toInt(tTeamPlayer.getNumber()));
												break;
											}
										}
									}
								}
							}
						}
                        break;
                    case TEAM:
						if (team == null) {
							team = teamRepository.findOne(item.getCompetitorId());
						}
						if (team != null) {
                            if(team.getLogo() != null){
                                tTopListItem.setLogoUrl(ImageUtil.addLogoSuffix(team.getLogo().getUrl(), team.getLogo().getPath()));
                            }
							tTopListItem.setCompetitorName(team.getAbbreviation());
							tTopListItem.setMultiLangCompetitorNames(team.getMultiLangNames());
                            if(CollectionUtils.isNotEmpty(team.getMultiCounLogos())) {
                                tTopListItem.setMultiCounLogos(team.getMultiCounLogos());
                            }
							tTopListItem.setTeamOfficialName(team.getName());
							if(CollectionUtils.isNotEmpty(team.getMultiLangNames())) {
								tTopListItem.setMultiLangTeamOfficialNames(team.getMultiLangNames());
							}
						}
                }
                if (item.getShowOrder() != null) {
                    tTopListItem.setShowOrder(item.getShowOrder());
                }
                Map<String, String> tStatsMap = Maps.newHashMap();
                if (MapUtils.isNotEmpty(item.getStats())) {
                    for (Map.Entry<String, Object> entry : item.getStats().entrySet()) {
                        tStatsMap.put(entry.getKey(), String.valueOf(entry.getValue()));
                    }
                }
                tTopListItem.setStats(tStatsMap);
                return tTopListItem;
            }
        });
        tTopList.setItems(tTopListItems);
    }

}
