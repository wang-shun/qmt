package com.lesports.qmt.cms.admin.web.vo;

import com.lesports.qmt.cms.model.Layout;
import com.lesports.qmt.cms.model.Mapper;
import com.lesports.qmt.mvc.QmtVo;
import org.apache.commons.beanutils.LeBeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * User: ellios
 * Time: 17-1-10 : 下午5:27
 */
public class MapperVo extends Mapper implements QmtVo {

    private static final long serialVersionUID = 8556208595710501158L;

    public MapperVo() {
    }

    public MapperVo(Mapper mapper) {
        try {
            LeBeanUtils.copyProperties(this, mapper);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
    }

    public Mapper toModel() {
        //直接用类型转换得到的对象会报序列化错误
        Mapper mapper = new Mapper();
        try {
            LeBeanUtils.copyProperties(mapper, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        return mapper;
    }

    @Override
    public MapperVo pretty() {

        return (MapperVo) QmtVo.super.pretty();
    }
}
