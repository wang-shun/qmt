package com.lesports.qmt.sbc.converter;

import com.lesports.qmt.config.client.QmtConfigApis;
import com.lesports.qmt.sbc.api.dto.TResource;
import com.lesports.qmt.sbc.api.dto.TResourceLink;
import com.lesports.qmt.sbc.converter.support.AbstractSbcTDtoConverter;
import com.lesports.qmt.sbc.model.QmtResource;
import com.lesports.qmt.sbc.repository.ResourceRepository;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.beanutils.LeBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * User: ellios
 * Time: 16-10-27 : 下午9:58
 */
@Component("resourceConverter")
public class TResourceConverter extends AbstractSbcTDtoConverter<QmtResource, TResource> {

    @Override
    protected void fillDto(TResource dto, QmtResource model) {
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setShortName(model.getShortName());
        dto.setDesc(model.getDesc());
        dto.setGroup(LeNumberUtils.toBoolean(model.getGroup()));
        List<Long> childList = new ArrayList<>(model.getChildIds());
        dto.setChildIds(childList);
        if (model.getImageUrl() != null) {
            dto.setImageUrl(model.getImageUrl().getUrl());
        }
        dto.setLinkUrl(model.getUrl());
        dto.setType(model.getType());
        dto.setUpdateType(model.getUpdateType());
        if(CollectionUtils.isNotEmpty(model.getLinks())){
            model.getLinks().stream()
                    .map(elem -> toResourceLinkDto(elem))
                    .filter(Objects::nonNull)
                    .forEach(elem -> dto.addToLinks(elem));
        }
    }

    @Override
    protected TResource createEmptyDto() {
        return new TResource();
    }

  /*  protected TResourceItem toResourceItemDto(QmtResource.ResourceItem item){
        try {
            TResourceItem dto = new TResourceItem();
            dto.setItemId(item.getItemId());
            dto.setOrder(item.getOrder());
            dto.setImageUrls(item.getImageUrls());
            dto.setLinkUrl(item.getLinkUrl());
            dto.setName(item.getName());
            dto.setShortName(item.getShortName());
            dto.setTags(item.getTags());
            dto.setType(item.getType());
            if(item.getType() == ResourceContentType.RESOURCE){
                //如果item的类型是资源位，还要去找这个资源位下的item过来,这种情况只有两级父子关系
                QmtResource resource = resourceRepository.findOne(item.getItemId());
                if(resource != null){
                    List<QmtResource.ResourceItem> childItems = resource.getItems();
                    if(CollectionUtils.isNotEmpty(childItems)){
                        for(QmtResource.ResourceItem childItem : childItems){
                            dto.addToItems(toResourceItemDto4Child(childItem));
                        }
                    }
                }
            }
            return dto;
        } catch (Exception e) {
            LOG.error("fail to toResourceItemDto. item : {}", e);
        }
        return null;
    }

    protected TResourceItem toResourceItemDto4Child(QmtResource.ResourceItem item){
        TResourceItem dto = new TResourceItem();
        dto.setItemId(item.getItemId());
        dto.setOrder(item.getOrder());
        dto.setImageUrls(item.getImageUrls());
        dto.setLinkUrl(item.getLinkUrl());
        dto.setName(item.getName());
        dto.setShortName(item.getShortName());
        dto.setTags(item.getTags());
        dto.setType(item.getType());
        return dto;
    }*/
}
