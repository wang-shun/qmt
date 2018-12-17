package com.lesports.qmt.cms.admin.web.vo;

import com.lesports.qmt.cms.model.Widget;
import com.lesports.qmt.mvc.QmtVo;
import org.apache.commons.beanutils.LeBeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * User: ellios
 * Time: 16-12-5 : 下午6:48
 */
public class WidgetVo extends Widget implements QmtVo {

    private static final long serialVersionUID = 6073026746081319481L;

    public WidgetVo() {
    }

    public WidgetVo(Widget widget) {
        try {
            LeBeanUtils.copyProperties(this, widget);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
    }

    public Widget toModel() {
        //直接用类型转换得到的对象会报序列化错误
        Widget widget = new Widget();
        try {
            LeBeanUtils.copyProperties(widget, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        return widget;
    }

    @Override
    public WidgetVo pretty() {

        return (WidgetVo) QmtVo.super.pretty();
    }
}
