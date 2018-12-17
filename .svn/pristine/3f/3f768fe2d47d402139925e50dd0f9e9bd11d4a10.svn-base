package com.lesports.qmt.web.utils;

import com.google.common.base.Preconditions;
import com.lesports.LeConstants;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;

/**
 * 生成新英合作的相关内容的工具
 * User: ellios
 * Time: 16-3-28 : 下午7:44
 */
public class XinYingOpUtils {

    /**
     * 新英付费播放url
     *
     * @param comboEpisode
     */
    public static String buildXinyingPlayUrl(TComboEpisode comboEpisode, CallerParam caller) {
		Preconditions.checkNotNull(comboEpisode);
		StringBuffer sb = new StringBuffer();
		if (caller.getCallerId() == LeConstants.LESPORTS_PC_CALLER_CODE) {//pc的新英付费地址
			sb.append("http://ssports.smgbb.cn/live/");
		} else {//m站的新英付费地址
			sb.append("http://m.ssports.smgbb.cn/live/");
		}
		if (comboEpisode.getXinyingMatchId() != 0) {
			sb.append(comboEpisode.getXinyingMatchId()).append(".html?source=lesports");
			if (caller.getCallerId() == LeConstants.LESPORTS_PC_CALLER_CODE) {//pc的新英付费地址
				sb.append("&subsource=lesportsPc");
			} else {//m站的新英付费地址
				sb.append("&subsource=lesportsApp");
			}
		}
		return sb.toString();
    }
}
