package com.lesports.qmt.mvc;

import com.lesports.qmt.model.support.QmtModel;

/**
 * Created by denghui on 2016/10/31.
 */
public interface QmtVoConverter<E extends QmtModel, VO extends E> {

    /**
     * 将参数转换为VO对象
     * @param object
     * @return
     */
    VO toVo(Object object);

    /**
     * 复制可编辑属性
     * @param existsEntity
     * @param vo
     * @return
     */
    default E copyEditableProperties(E existsEntity, VO vo) { return existsEntity; };
}
