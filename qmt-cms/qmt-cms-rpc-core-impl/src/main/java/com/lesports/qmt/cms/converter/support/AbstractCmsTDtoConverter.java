package com.lesports.qmt.cms.converter.support;

import com.lesports.api.common.Channel;
import com.lesports.qmt.config.api.dto.TDictEntry;
import com.lesports.qmt.converter.support.AbstractTDtoConverter;
import com.lesports.qmt.model.support.QmtModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: ellios
 * Time: 16-10-28 : 下午2:56
 */
abstract public class AbstractCmsTDtoConverter<M extends QmtModel, T extends TBase> extends AbstractTDtoConverter<M, T> {

    protected static final Logger LOG = LoggerFactory.getLogger(AbstractCmsTDtoConverter.class);

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
}
