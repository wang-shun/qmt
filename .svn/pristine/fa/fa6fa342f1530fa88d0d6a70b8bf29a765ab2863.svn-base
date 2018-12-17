package com.lesports.qmt.sbc.utils;

import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gengchengliang on 2015/8/2.
 */
public class CommentIdUtils {

	private static final Logger LOG = LoggerFactory.getLogger(CommentIdUtils.class);

	/**
	 * 创建episode的评论id
	 * @param id
	 * @return
	 */
	public static String create(Long id) {
		IdType idType = LeIdApis.checkIdTye(id);
		if (idType == null) {
			LOG.warn("illegal id : {}", id);
			return StringUtils.EMPTY;
		}
		if (idType == IdType.EPISODE) {
			if (LeIdApis.isOldId(id)) {
				return LeIdApis.getOldId(id) + "";
			} else {
				return id + "";
			}
		}
		return StringUtils.EMPTY;
	}
}
