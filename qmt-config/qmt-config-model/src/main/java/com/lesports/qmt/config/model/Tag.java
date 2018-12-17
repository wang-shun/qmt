package com.lesports.qmt.config.model;

import com.lesports.api.common.CountryCode;
import com.lesports.api.common.LanguageCode;
import com.lesports.qmt.model.support.QmtModel;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 标签,用户归类查找和搜索等
 * User: ellios
 * Time: 15-7-20 : 下午3:18
 */
@Document(collection = "tags")
public class Tag extends QmtModel<Long> {
    private static final long serialVersionUID = -2749726607083230167L;

    //标签名称
    private String name;
    //标签描述
    private String desc;
    //允许展示的国家
    @Field("allow_country")
    private CountryCode allowCountry;
    //所对应的语言
    @Field("language_code")
    private LanguageCode languageCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CountryCode getAllowCountry() {
        return allowCountry;
    }

    public void setAllowCountry(CountryCode allowCountry) {
        this.allowCountry = allowCountry;
    }

    public LanguageCode getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(LanguageCode languageCode) {
        this.languageCode = languageCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
