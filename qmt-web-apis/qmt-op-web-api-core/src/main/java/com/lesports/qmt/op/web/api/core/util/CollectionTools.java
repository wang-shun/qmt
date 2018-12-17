package com.lesports.qmt.op.web.api.core.util;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by gengchengliang on 2015/6/24.
 */
public class CollectionTools {

	public static String list2String (List<Long> ids) {
		if (CollectionUtils.isEmpty(ids))
			return "";
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (Long str : ids) {
			if (flag) {
				result.append(",");
			}else {
				flag = true;
			}
			result.append(str);
		}
		return result.toString();
	}

	public static List<Long> string2List (String ids) {
		if (StringUtils.isBlank(ids))
			return null;
		List<Long> idList = Lists.newArrayList();
		String[] idsArray = ids.split(",");
		for (int i=0; i<idsArray.length; i++) {
			String s = idsArray[i];
			idList.add(Long.parseLong(s));
		}
		return idList;
	}
}
