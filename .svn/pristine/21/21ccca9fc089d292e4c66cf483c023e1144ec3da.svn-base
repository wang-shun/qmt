package com.lesports.qmt.sbc.converter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.qmt.sbc.api.dto.TContentItem;
import com.lesports.qmt.sbc.api.dto.TResourceContent;
import com.lesports.qmt.sbc.converter.support.AbstractSbcTDtoConverter;
import com.lesports.qmt.sbc.model.ResourceContent;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Created by denghui on 2016/12/18.
 */
@Component("resourceContentConverter")
public class TResourceContentConverter extends AbstractSbcTDtoConverter<ResourceContent, TResourceContent> {

    @Override
    protected void fillDto(TResourceContent dto, ResourceContent model) {
        dto.setId(model.getId());
        dto.setResourceId(model.getResourceId());
        dto.setItemId(model.getItemId());
        dto.setOrder(LeNumberUtils.toInt(model.getOrder()));
        dto.setName(model.getName());
        boolean setCoverImage = false;
        if(model.getCoverImage() != null){
            for(ImageUrlExt imageUrlExt : model.getCoverImage().values()){
                if(StringUtils.isNotBlank(imageUrlExt.getUrl())){
                    setCoverImage = true;
                    break;
                }
            }
        }
        if(setCoverImage){
            dto.setCoverImage(Maps.transformValues(model.getCoverImage(), IMAGE_URL_FUNCTION));
        }
        if(CollectionUtils.isNotEmpty(model.getLinks())){
            model.getLinks().stream()
                    .map(elem -> toResourceLinkDto(elem))
                    .filter(Objects::nonNull)
                    .forEach(elem -> dto.addToLinks(elem));
        }
        dto.setTagIds(model.getTagIds());
        dto.setType(model.getContentType());
        dto.setSubType(model.getSubContentType());
        dto.setDataSearch(model.getDataSearch());
        List<TContentItem> tContentItems = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(model.getContentItems())) {
            for (ResourceContent.ContentItem contentItem : model.getContentItems()) {
                TContentItem tContentItem = new TContentItem();
                fillContentItem(tContentItem, contentItem);
                tContentItems.add(tContentItem);
            }
        }
        dto.setContentItems(tContentItems);
        dto.setShowTitle(LeNumberUtils.toBoolean(model.isShowTitle()));
        dto.setH5Url(model.getH5Url());
        dto.setOtherContent(model.getOtherContent());
        dto.setTvImg(model.getTvImg());
        dto.setDurationTime(model.getDurationTime());
        dto.setMobileImg(model.getMobileImg());
        dto.setIpadImg(model.getIpadImg());
        dto.setStarLevel(model.getStarLevel());
        dto.setStartTime(model.getStartTime());
        dto.setEndTime(model.getEndTime());
    }

    private void fillContentItem(TContentItem dto, ResourceContent.ContentItem model) {
        dto.setTitle(model.getTitle());
        dto.setValue(model.getValue());
        dto.setImage(model.getImage());
        dto.setShowTitle(LeNumberUtils.toBoolean(model.isShowTitle()));
        dto.setType(model.getType());
    }

    @Override
    protected TResourceContent createEmptyDto() {
        return new TResourceContent();
    }
}
