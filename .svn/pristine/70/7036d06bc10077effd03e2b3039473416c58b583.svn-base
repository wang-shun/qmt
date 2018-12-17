package com.lesports.qmt.sbc.converter.support;

import com.google.common.base.Function;
import com.lesports.api.common.Channel;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.qmt.config.api.dto.TDictEntry;
import com.lesports.qmt.converter.support.AbstractTDtoConverter;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.sbc.api.dto.TResourceLink;
import com.lesports.qmt.sbc.model.QmtResource;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;

/**
 * User: ellios
 * Time: 16-10-28 : 下午2:56
 */
abstract public class AbstractSbcTDtoConverter<M extends QmtModel, T extends TBase> extends AbstractTDtoConverter<M, T> {

    protected static final Logger LOG = LoggerFactory.getLogger(AbstractSbcTDtoConverter.class);

    public static final Function<ImageUrlExt, String> IMAGE_URL_FUNCTION = new Function<ImageUrlExt, String>() {

        @Nullable
        @Override
        public String apply(@Nullable ImageUrlExt input) {
            return input != null ? input.getUrl() : null;
        }
    };

    /**
     * 把字典转化为频道
     *
     * @param entry
     * @return
     */
    protected Channel convertDictToChannel(TDictEntry entry) {
        Channel channel = new Channel();

        if (entry == null) {
            channel.setId(-1);
            channel.setName(StringUtils.EMPTY);
        } else {
            channel.setId(entry.getId());
            channel.setName(entry.getName());
        }
        return channel;
    }

    protected TResourceLink toResourceLinkDto(QmtResource.ResourceLink link){
        TResourceLink dto = new TResourceLink();
        dto.setName(link.getName());
        dto.setUrl(link.getUrl());
        dto.setOrder(LeNumberUtils.toInt(link.getOrder(),0));
        return dto;
    }
}
