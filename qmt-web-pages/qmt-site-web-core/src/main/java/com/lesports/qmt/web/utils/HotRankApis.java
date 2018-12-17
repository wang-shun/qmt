package com.lesports.qmt.web.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.lesports.LeConstants;
import com.lesports.qmt.web.model.HotRank;
import com.lesports.utils.LeProperties;
import com.lesports.utils.http.RestTemplateFactory;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * User: ellios
 * Time: 15-10-13 : 下午6:20
 */
public class HotRankApis {

    private final static Logger LOG = LoggerFactory.getLogger(HotRankApis.class);

    private static final RestTemplate REST_TEMPLATE = RestTemplateFactory.getLeTemplate();

    private static final String VIDEO_HOT_RANK = "V2_VIDEO_HOT_RANK";
    private static final int EXPIRE_TIME = 60 * 60;
    private static final String HOT_RANK_URL = LeProperties.getString("hot.rank.url",
            "http://top.letv.com/json/daySportPlay.jsn");

    /**
     * 获取热播榜
     *
     * @return
     */
    public static List<HotRank> getHotRankData() {
        List<HotRank> hotRanks = Lists.newArrayList();
		try {
			List<HotRank> catchHotRanks = getHotRankDataInCache();
			if (CollectionUtils.isNotEmpty(catchHotRanks)) {
				return catchHotRanks;
			}
			String jsonStr = REST_TEMPLATE.getForObject(HOT_RANK_URL, String.class);
			List<HotRank> ranks = JSON.parseArray(jsonStr, HotRank.class).subList(0, 10);
			if (CollectionUtils.isNotEmpty(ranks)) {
				for (HotRank hotRank : ranks) {
					hotRank.setUrl(String.format(LeConstants.PC_VIDEO_URL, hotRank.getId()));
					hotRanks.add(hotRank);
				}
			}
            if(CollectionUtils.isNotEmpty(ranks)){
                saveHotRankDataInCache(hotRanks);
            }
        } catch (Exception e) {
            LOG.error("fail to get getHotRankData. error : {}", e.getMessage(), e);
        }
        return hotRanks;
    }

    private static List<HotRank> getHotRankDataInCache(){
        return (List<HotRank>)KVMemcachedUtils.get(VIDEO_HOT_RANK);
    }

    private static boolean saveHotRankDataInCache(List<HotRank> hotRanks){
        return KVMemcachedUtils.set(VIDEO_HOT_RANK, hotRanks, EXPIRE_TIME);
    }
}
