package com.lesports.qmt.web.datacenter.dataprocess;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbc.api.dto.TContentItem;
import com.lesports.qmt.sbc.api.dto.TResourceContent;
import com.lesports.qmt.web.datacenter.vo.TvContentVo;

/**
 * Created by denghui on 2016/12/21.
 */
public class TvContentProcess implements DataProcess<String, TvContentVo> {

    public void constructCommonData(TvContentVo vo,TResourceContent tResourceContent){
        vo.setId(tResourceContent.getItemId());
        vo.setType(tResourceContent.getType());
        vo.setSubType(tResourceContent.getSubType());
        vo.setShowTitle(tResourceContent.isShowTitle());
        vo.setH5Url(tResourceContent.getH5Url());
        vo.setOtherContent(tResourceContent.getOtherContent());
        vo.setImage(tResourceContent.getTvImg());

        for (TContentItem tContentItem : tResourceContent.getContentItems()) {
            TvContentVo tvContentVo = new TvContentVo();
            fillWithContentItem(tvContentVo, tContentItem);
            vo.getItems().add(tvContentVo);
        }
    }

    private void fillWithContentItem(TvContentVo tvContentVo, TContentItem tContentItem) {
        tvContentVo.setName(tContentItem.getTitle());
        tvContentVo.setShowTitle(tContentItem.isShowTitle());
        tvContentVo.setType(tContentItem.getType());// url??
        tvContentVo.setId(tContentItem.getValue());
        tvContentVo.setImage(tContentItem.getImage());
    }

    @Override
    public TvContentVo getEntity() {
        return new TvContentVo();
    }

    @Override
    public void constructManualRemoteData(TvContentVo tvContentVo, String itemId, CallerParam callerParam) {

    }
}
