package com.lesports.qmt.op.web.api.core.util;

import com.lesports.utils.LeDateUtils;

import java.util.Date;

/**
 * Created by gengchengliang on 2015/6/16.
 */
public class TimeUtil {

	public static Date addDay(Date _date, int _day) {
		if (_day == 0)
			return _date;
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(_date);
		c.add(java.util.Calendar.DAY_OF_YEAR, _day);
		return c.getTime();
	}

	public static String addDay(String yyyyMMdd, int _day) {
		if (yyyyMMdd == null || yyyyMMdd.length() != 8)
			return null;
		Date date = LeDateUtils.parseYYYYMMDD(yyyyMMdd);
		Date day = addDay(date, _day);
		return LeDateUtils.formatYYYYMMDD(day);
	}

}
