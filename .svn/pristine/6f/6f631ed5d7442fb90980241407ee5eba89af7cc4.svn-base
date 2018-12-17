package com.lesports.qmt.mvc;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.CountryCode;
import com.lesports.api.common.LanguageCode;
import com.lesports.utils.CallerUtils;

/**
 * Created by lufei1 on 2016/11/9.
 */
public class QmtCallerParam {
    private String country;
    private String language;
    private long caller;

    public CallerParam getCallerParam() {
        CallerParam callerParam = new CallerParam(caller);
        CountryCode countryCode = CallerUtils.getCountry(country);
        if (null != countryCode) {
            callerParam.setCountry(countryCode);
        }
        LanguageCode languageCode = CallerUtils.getLanguage(language);
        if (null != languageCode) {
            callerParam.setLanguage(languageCode);
        }
        return callerParam;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public long getCaller() {
        return caller;
    }

    public void setCaller(long caller) {
        this.caller = caller;
    }
}
