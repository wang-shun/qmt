package com.lesports.qmt.cms.admin.web.vo;

import com.lesports.qmt.cms.model.Layout;
import com.lesports.qmt.mvc.QmtVo;
import org.apache.commons.beanutils.LeBeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * User: ellios
 * Time: 16-12-5 : 下午6:49
 */
public class LayoutVo extends Layout implements QmtVo {

    private static final long serialVersionUID = 8556208595710501158L;

    public LayoutVo() {
    }

    public LayoutVo(Layout layout) {
        try {
            LeBeanUtils.copyProperties(this, layout);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
    }

    public Layout toModel() {
        //直接用类型转换得到的对象会报序列化错误
        Layout layout = new Layout();
        try {
            LeBeanUtils.copyProperties(layout, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        return layout;
    }

    @Override
    public LayoutVo pretty() {

        return (LayoutVo) QmtVo.super.pretty();
    }
}
