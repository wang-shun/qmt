package com.lesports.qmt.sbd.helper;

import com.lesports.qmt.sbd.model.Competition;
import com.lesports.qmt.sbd.model.CompetitionSeason;
import com.lesports.qmt.sbd.repository.CompetitionRepository;
import com.lesports.qmt.sbd.repository.CompetitionSeasonRepository;
import com.lesports.qmt.sbd.utils.KVCacheUtils;
import com.lesports.utils.Utf8KeyCreater;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * User: ellios
 * Time: 15-6-9 : 下午6:12
 */
@Component("competitionSeasonHelper")
public class CompetitionSeasonHelper {

    //赛事下面最新的赛季
    protected static final Utf8KeyCreater<String> LATEST_COMPETITION_SEASON_KEY_CREATER = new Utf8KeyCreater<>("V2_SMS_LATEST_COMPETITION_SEASON_TF_");

    //缓存一小时
    protected static final int LATEST_COMPETITION_SEASON_CACHE_EXPIRE_TIME = 60 * 60;

    @Resource
    private CompetitionSeasonRepository competitionSeasonRepository;

    @Resource
    private CompetitionRepository competitionRepository;

    public CompetitionSeason getLatestSeasonByCid(long cid) {
        if (cid <= 0) {
            return null;
        }
        CompetitionSeason competitionSeason = new CompetitionSeason();
        String key = LATEST_COMPETITION_SEASON_KEY_CREATER.textKey(cid + "");
        Object obj = KVCacheUtils.get(key);
        if (obj != null) {
            competitionSeason = (CompetitionSeason) obj;
        } else {
            Competition one = competitionRepository.findOne(cid);
            if (one == null) {
                return null;
            }
            String currentSeason = one.getCurrentSeason();
            if (StringUtils.isNotBlank(currentSeason)) {
                List<CompetitionSeason> seasons = competitionSeasonRepository.findByCid(cid, currentSeason);
                if (CollectionUtils.isEmpty(seasons)) {
                    return null;
                }
                competitionSeason = seasons.get(0);
            } else {
                competitionSeason = competitionSeasonRepository.findLatestByCid(cid);
            }
            KVCacheUtils.set(key, competitionSeason, LATEST_COMPETITION_SEASON_CACHE_EXPIRE_TIME);
        }
        return competitionSeason;
    }
}
