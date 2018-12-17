package com.lesports.qmt.sbc.vo;

import com.lesports.qmt.mvc.QmtVo;
import com.lesports.qmt.sbc.model.News;
import com.lesports.qmt.sbc.model.NewsImage;
import com.lesports.utils.LeDateUtils;
import org.apache.commons.beanutils.LeBeanUtils;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by denghui on 2016/10/31.
 */
public class NewsVo extends News implements QmtVo {

    private static final long serialVersionUID = 2570124937429257253L;

    private List<RelatedTagVo> relatedTags;
    private List<NewsImage> images;

    public NewsVo() {
    }

    public NewsVo(News news) {
        try {
            LeBeanUtils.copyProperties(this, news);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
    }

    public List<RelatedTagVo> getRelatedTags() {
        return relatedTags;
    }

    public void setRelatedTags(List<RelatedTagVo> relatedTags) {
        this.relatedTags = relatedTags;
    }

    public List<NewsImage> getImages() {
        return images;
    }

    public void setImages(List<NewsImage> images) {
        this.images = images;
    }

    public News toNews() {
        //直接用类型转换得到的对象会报序列化错误
        News news = new News();
        try {
            LeBeanUtils.copyProperties(news, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        return news;
    }

    @Override
    public NewsVo pretty() {
        this.setPublishAt(LeDateUtils.tansYYYYMMDDHHMMSSPretty(this.getPublishAt()));
        if (CollectionUtils.isNotEmpty(this.images)) {
            for (NewsImage image : this.images) {
                image.setCreateAt(LeDateUtils.tansYYYYMMDDHHMMSSPretty(image.getCreateAt()));
                image.setUpdateAt(LeDateUtils.tansYYYYMMDDHHMMSSPretty(image.getUpdateAt()));
            }
        }
        return (NewsVo) QmtVo.super.pretty();
    }
}
