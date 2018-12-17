package com.lesports.qmt.resource.converter;

import com.lesports.api.common.CallerParam;
import com.lesports.id.client.LeIdApis;
import com.lesports.qmt.resource.converter.support.AbstractCvoConverter;
import com.lesports.qmt.resource.cvo.TvContentCvo;
import com.lesports.qmt.sbc.api.dto.ResourceItemType;
import com.lesports.qmt.sbc.api.dto.TContentItem;
import com.lesports.qmt.sbc.api.dto.TResourceContent;
import com.lesports.qmt.sbc.api.dto.TVideo;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.utils.math.LeNumberUtils;

/**
 * User: ellios
 * Time: 16-12-28 : 下午10:12
 */
public class TvContentCvoConverter extends AbstractCvoConverter<TvContentCvo, String> {
    @Override
    public TvContentCvo doToCvo(TResourceContent content, String dto) {
        TvContentCvo cvo = new TvContentCvo();
        if (content.getSubType() == ResourceItemType.VIDEO) {
            try {
                cvo.setId(String.valueOf(LeIdApis.convertLeSportsVideoIdToMmsVideoId(LeNumberUtils.toLong(content.getItemId()))));
            } catch (Exception e) {
                cvo.setId(content.getItemId());
            }
        } else {
            cvo.setId(content.getItemId());
        }
        cvo.setType(content.getType());
        cvo.setSubType(content.getSubType());
        cvo.setShowTitle(content.isShowTitle());
        cvo.setH5Url(content.getH5Url());
        cvo.setOtherContent(content.getOtherContent());
        cvo.setImage(content.getTvImg());
        cvo.setName(content.getName());

        for (TContentItem tContentItem : content.getContentItems()) {
            TvContentCvo subCvo = new TvContentCvo();
            fillWithContentItem(subCvo, tContentItem);
            cvo.getItems().add(subCvo);
        }

        return cvo;
    }

    private void fillWithContentItem(TvContentCvo cvo, TContentItem tContentItem) {
        cvo.setName(tContentItem.getTitle());
        cvo.setShowTitle(tContentItem.isShowTitle());
        cvo.setType(tContentItem.getType());// url??
        cvo.setId(tContentItem.getValue());
        cvo.setImage(tContentItem.getImage());
    }

    @Override
    public TvContentCvo doToCvo(TResourceContent content, CallerParam caller) {
        String dto = "";
        return toCvo(content, dto);
    }

    @Override
    protected boolean supportContent(TResourceContent content) {
        return true;
    }

    protected boolean needFillCommonProperty(){
        return false;
    }
}
