package com.lesports.qmt.sbc.model;

import com.lesports.api.common.CountryCode;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.api.common.LanguageCode;
import com.lesports.qmt.model.support.QmtModel;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 新闻图片
 * Created by denghui on 2016/10/13.
 */
@Document(collection = "news_images")
public class NewsImage extends QmtModel<Long> {
    private static final long serialVersionUID = 6882012686990826153L;

    //关联的新闻ID
    @Field("news_id")
    private Long newsId;
    //图片标题
    private String name;
    //图片描述
    private String desc;
    //图片Url信息
    private ImageUrlExt image;
    //图集：是否列表封面图
    @Field("is_cover")
    private Boolean isCover;
    //图集：是否PC聚合页封面图
    @Field("is_agg_cover")
    private Boolean isAggCover;
    //展示顺序
    @Field("show_order")
    private Integer showOrder;
    //是否被删除
    private Boolean deleted;
    //发布国家
    @Field("allow_country")
    private CountryCode allowCountry;
    //发布语言
    @Field("language_code")
    private LanguageCode languageCode;

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ImageUrlExt getImage() {
        return image;
    }

    public void setImage(ImageUrlExt image) {
        this.image = image;
    }

    public Boolean getCover() {
        return isCover;
    }

    public void setCover(Boolean cover) {
        isCover = cover;
    }

    public Boolean getAggCover() {
        return isAggCover;
    }

    public void setAggCover(Boolean aggCover) {
        isAggCover = aggCover;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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

    //can not delete
    //fix error when deserialize id using fastjson because of the method in BaseModel
    public void setHelperId(Long helperId) {
        setId(helperId);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }
}
