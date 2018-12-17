package com.lesports.qmt.web.api.core.common;

/**
 * Created by gengchengliang on 2015/6/16.
 */
public enum PeriodType {

	DAY("d", 1),
	WEEK("w", 7);


	private final String value;
    private final int day;

	private PeriodType(String value, int day) {
        this.day = day;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public int getDay(){
        return day;
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

}
