package com.lesports.qmt.converter;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.model.support.QmtModel;
import org.apache.thrift.TBase;

/**
 * User: ellios
 * Time: 16-3-21 : 下午2:07
 */
public interface TDtoConverter<M extends QmtModel, T extends TBase> {

    /**
     * 将model转化为thrift的dto对象
     *
     * @param m
     * @return
     */
    T toDto(M m);

    /**
     * 根据caller的信息适配thrift的dto对象
     *
     * @param dto
     * @param caller
     */
    default T adaptDto(T dto, CallerParam caller){
        throw new UnsupportedOperationException();
    }
}
