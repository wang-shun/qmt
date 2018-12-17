package com.lesports.qmt.web.api.core.util;

/**
 * Created by ruiyuansheng on 2016/12/27.
 */
public enum TvSuggestType {

    HOMEPAGE(1),
    DESKTOP(2),
    MATCHHALL(3);

    private final int value;

    private TvSuggestType(int value) {
        this.value = value;
    }

    /**
     * Get the integer value of this enum value, as defined in the Thrift IDL.
     */
    public int getValue() {
        return value;
    }

    /**
     * Find a the enum type by its integer value, as defined in the Thrift IDL.
     * @return null if the value is not found.
     */
    public static TvSuggestType findByValue(int value) {
        switch (value) {
            case 1:
                return HOMEPAGE;
            case 2:
                return DESKTOP;
            case 3:
                return MATCHHALL;
            default:
                return null;
        }
    }

}
