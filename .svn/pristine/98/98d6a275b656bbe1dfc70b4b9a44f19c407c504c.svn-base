package com.lesports.qmt.resource.converter.support;

import com.google.common.base.Preconditions;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.resource.converter.CvoConverter;
import com.lesports.qmt.resource.cvo.BaseCvo;
import com.lesports.qmt.sbc.api.dto.TResourceContent;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: ellios
 * Time: 16-12-28 : 下午6:01
 */
abstract public class AbstractCvoConverter<V extends BaseCvo, T> implements CvoConverter<V, T> {

    protected static final Logger LOG = LoggerFactory.getLogger(AbstractCvoConverter.class);

    @Override
    public V toCvo(TResourceContent content, T dto) {
        Preconditions.checkArgument(content != null, "content must not be null.");
        V cvo = doToCvo(content, dto);
        if(cvo != null & needFillCommonProperty()){
            fillCommonProperty(cvo, content);
        }
        return cvo;
    }

    @Override
    public V toCvo(TResourceContent content, CallerParam caller) {
        Preconditions.checkArgument(content != null, "content must not be null.");
        Preconditions.checkArgument(supportContent(content), "converter no support this content.");

        return doToCvo(content, caller);
    }

    protected abstract V doToCvo(TResourceContent content, T dto);

    protected abstract V doToCvo(TResourceContent content, CallerParam caller);

    protected abstract boolean supportContent(TResourceContent content);

    /**
     * 通用数据，就是资源位内容本身的内容
     * @param e
     * @param tResourceContent
     */
    protected void fillCommonProperty(V e, TResourceContent tResourceContent){
        if(StringUtils.isBlank(e.getName())){
            e.setName(tResourceContent.getName());
        }
        e.setDurationTime(tResourceContent.getDurationTime());
        e.setMobileImg(tResourceContent.getMobileImg());
        e.setIpadImg(tResourceContent.getIpadImg());
        e.setTvImg(tResourceContent.getTvImg());
        e.setOverdRound(tResourceContent.getOverdRound());
        e.setLivingRound(tResourceContent.getLivingRound());
        e.setToStartRound(tResourceContent.getToStartRound());
        e.setTagIds(tResourceContent.getTagIds());
        e.setOrder(tResourceContent.getOrder());
        e.setStartTime(tResourceContent.getStartTime());
        e.setEndTime(tResourceContent.getEndTime());
        e.setType(tResourceContent.getType());
        if(MapUtils.isNotEmpty(tResourceContent.getCoverImage())){
            e.setImageUrl(tResourceContent.getCoverImage());
        }
    }

    protected boolean needFillCommonProperty(){
        return true;
    }
}
