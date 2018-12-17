package com.lesports.qmt.web.api.core.util;

import com.lesports.LeConstants;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by gengchengliang on 2015/12/22.
 */
public class UrlParamUtils {

	public static String addCallerToUrl (String url, long caller) {
		if (StringUtils.isBlank(url)) {
			return LeConstants.EMPTY_STRING;
		}
		StringBuffer sb = new StringBuffer(url);
		if (url.contains(LeConstants.QUESTION_MARK)) {
			sb.append(LeConstants.AMPERSAND).append("caller=").append(caller);
		} else {
			sb.append(LeConstants.QUESTION_MARK).append("caller=").append(caller);
		}
		return sb.toString();
	}
}
