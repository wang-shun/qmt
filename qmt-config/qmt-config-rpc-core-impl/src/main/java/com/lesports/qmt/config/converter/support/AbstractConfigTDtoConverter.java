package com.lesports.qmt.config.converter.support;

import com.google.common.base.Function;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.qmt.converter.support.AbstractTDtoConverter;
import com.lesports.qmt.model.support.QmtModel;
import org.apache.thrift.TBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;

/**
 * User: ellios
 * Time: 16-10-28 : 下午2:56
 */
abstract public class AbstractConfigTDtoConverter<M extends QmtModel, T extends TBase> extends AbstractTDtoConverter<M, T> {

    protected static final Logger LOG = LoggerFactory.getLogger(AbstractConfigTDtoConverter.class);

    protected static final Function<ImageUrlExt, String> IMAGE_URL_FUNCTION = new Function<ImageUrlExt, String>() {
        @Nullable
        @Override
        public String apply(@Nullable ImageUrlExt input) {
            return input == null ? null : input.getUrl();
        }
    };
}
