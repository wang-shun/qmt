package com.lesports.qmt.op.web.api.core.common;

/**
 * Created by gengchengliang on 2015/6/16.
 */
public enum PeriodType {

	DAY("d"),
	WEEK("w");


	private final String value;

	private PeriodType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static PeriodType findByValue(String value) {
		switch (value) {
			case "d":
				return DAY;
			case "w":
				return WEEK;
			default:
				return DAY;
		}
	}

	public static int findUnitByValue(PeriodType value) {
		switch (value) {
			case DAY:
				return 1;
			case WEEK:
				return 7;
			default:
				return 1;
		}
	}

}
