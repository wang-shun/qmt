package com.lesports.qmt.sbc.vo;

import com.lesports.qmt.mvc.QmtVo;
import com.lesports.qmt.sbc.model.QmtResource;
import org.apache.commons.beanutils.LeBeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * User: ellios
 * Time: 16-10-28 : 下午8:26
 */
public class ResourceVo extends QmtResource implements QmtVo{

    public ResourceVo() {

    }
    public ResourceVo(QmtResource qmtResource) {
        try {
            LeBeanUtils.copyProperties(this, qmtResource);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
    }

    public QmtResource toModel() {
        //直接用类型转换得到的对象会报序列化错误
        QmtResource qmtResource = new QmtResource();
        try {
            LeBeanUtils.copyProperties(qmtResource, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        return qmtResource;
    }
}
