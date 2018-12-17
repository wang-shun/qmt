package com.lesports.qmt.web.api.core.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ruiyuansheng on 2016/12/5.
 */
public class TvThemeVo implements Serializable {

    private TvSuggestVo theme;

    private List<TvSuggestVo> subSuggests;

    public TvSuggestVo getTheme() {
        return theme;
    }

    public void setTheme(TvSuggestVo theme) {
        this.theme = theme;
    }

    public List<TvSuggestVo> getSubSuggests() {
        return subSuggests;
    }

    public void setSubSuggests(List<TvSuggestVo> subSuggests) {
        this.subSuggests = subSuggests;
    }

    @Override
    public String toString() {
        return "TvThemeVo{" +
                "theme=" + theme +
                ", subSuggests=" + subSuggests +
                '}';
    }
}
