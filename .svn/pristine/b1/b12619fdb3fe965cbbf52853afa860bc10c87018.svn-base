package com.lesports.qmt.sbc.vo;

import com.alibaba.fastjson.JSON;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.qmt.mvc.QmtVo;
import com.lesports.qmt.sbc.api.dto.ResourceItemType;
import com.lesports.qmt.sbc.model.ResourceContent;
import org.apache.commons.beanutils.LeBeanUtils;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: wangjichuan
 * Time: 16-10-28 : 下午8:26
 */
public class ResourceContentVo extends ResourceContent implements QmtVo{
    private String coverImgParam;
    private String contentItemsParam;
    private String dataSearchParam;

    public ResourceContentVo() {

    }
    public ResourceContentVo(ResourceContent resourceContent) {
        try {
            LeBeanUtils.copyProperties(this, resourceContent);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
    }

    public ResourceContent toModel() {
        //直接用类型转换得到的对象会报序列化错误
        ResourceContent resourceContent = new ResourceContent();
        Map<String, ImageUrlExt> coverImage = JSON.parseObject(getCoverImgParam(), Map.class);
        List<ContentItem> contentItems = JSON.parseObject(getContentItemsParam(),List.class);
        List<ResourceItemType> dataSearch = JSON.parseObject(getDataSearchParam(),List.class);
        if(CollectionUtils.isNotEmpty(getTagIds()) && getTagIds().get(0) == null){
            setTagIds(new ArrayList<Long>());
        }
        try {
            LeBeanUtils.copyProperties(resourceContent, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        resourceContent.setCoverImage(coverImage);
        resourceContent.setContentItems(contentItems);
        resourceContent.setDataSearch(dataSearch);
        return resourceContent;
    }

    public String getCoverImgParam() {
        return coverImgParam;
    }

    public void setCoverImgParam(String coverImgParam) {
        this.coverImgParam = coverImgParam;
    }

    public String getContentItemsParam() {
        return contentItemsParam;
    }

    public void setContentItemsParam(String contentItemsParam) {
        this.contentItemsParam = contentItemsParam;
    }

    public String getDataSearchParam() {
        return dataSearchParam;
    }

    public void setDataSearchParam(String dataSearchParam) {
        this.dataSearchParam = dataSearchParam;
    }
}
