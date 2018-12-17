package com.lesports.qmt.converter.support;

import com.google.common.base.Preconditions;
import com.lesports.qmt.converter.TDtoConverter;
import com.lesports.qmt.model.support.QmtModel;
import org.apache.thrift.TBase;

import java.util.function.Supplier;

/**
 * User: ellios
 * Time: 16-10-27 : 下午9:47
 */
abstract public class AbstractTDtoConverter<M extends QmtModel, T extends TBase> implements TDtoConverter<M, T> {


    /**
     * 填充dto对象
     *
     * @param dto
     * @param model
     */
    protected abstract void fillDto(T dto, M model);

    @Override
    public T toDto(M model) {
        Preconditions.checkNotNull(model);
        T dto = createEmptyDto();
        fillDto(dto, model);
        return dto;
    }

    /**
     * 获取一个空的dto
     *
     * @return
     */
    abstract protected T createEmptyDto();
}
