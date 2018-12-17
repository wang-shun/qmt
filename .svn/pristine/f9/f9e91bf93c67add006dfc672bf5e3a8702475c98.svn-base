package com.lesports.qmt.op.web.api.core.vo;

import com.lesports.api.common.CallerParam;
import com.lesports.utils.Utf8KeyCreater;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by zhonglin on 2016/6/17.
 */
public class GetTagCacheParam {
    private String tagName;
    private CallerParam caller;

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(tagName).append(caller).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = false;
        if (null != obj && obj instanceof GetTagCacheParam) {
            GetTagCacheParam getTagCacheParam = (GetTagCacheParam)obj;
            equals = new EqualsBuilder().append(tagName, getTagCacheParam.getTagName())
                    .append(caller.getCallerId(), getTagCacheParam.getCaller().getCallerId())
                    .append(caller.getCountry(), getTagCacheParam.getCaller().getCountry())
                    .append(caller.getLanguage(), getTagCacheParam.getCaller().getLanguage())
                    .isEquals();
        }
        return equals;
    }

    public GetTagCacheParam() {
    }

    public GetTagCacheParam(String tagName, CallerParam caller) {
        this.tagName = tagName;
        this.caller = caller;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public CallerParam getCaller() {
        return caller;
    }

    public void setCaller(CallerParam caller) {
        this.caller = caller;
    }

    public String getCacheKey(Utf8KeyCreater<String> keyCreater) {
        return keyCreater + "_" + getCaller().getCallerId()
                + "_" + getCaller().getCountry() + "_" + getCaller().getLanguage();
    }
}
