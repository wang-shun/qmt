package com.lesports.qmt.cms.admin.web.vo;

import com.lesports.qmt.cms.api.common.PageType;
import com.lesports.qmt.cms.model.Column;
import com.lesports.qmt.mvc.QmtVo;
import org.apache.commons.beanutils.LeBeanUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

/**
 * User: ellios
 * Time: 16-12-5 : 下午6:46
 */
public class ColumnVo extends Column implements QmtVo {

    private static final long serialVersionUID = 8556208595710501158L;

    public ColumnVo() {
    }

    public ColumnVo(Column column) {
        try {
            LeBeanUtils.copyProperties(this, column);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
    }

    private SimpleColumnPage mPage;
    private SimpleColumnPage pcPage;
    private SimpleColumnPage dummyPage;

    public SimpleColumnPage getMPage() {
        return mPage;
    }

    public void setMPage(SimpleColumnPage mPage) {
        this.mPage = mPage;
    }

    public SimpleColumnPage getPcPage() {
        return pcPage;
    }

    public void setPcPage(SimpleColumnPage pcPage) {
        this.pcPage = pcPage;
    }

    public SimpleColumnPage getDummyPage() {
        return dummyPage;
    }

    public void setDummyPage(SimpleColumnPage dummyPage) {
        this.dummyPage = dummyPage;
    }

    public Column toModel() {
        //直接用类型转换得到的对象会报序列化错误
        Column column = new Column();
        try {
            LeBeanUtils.copyProperties(column, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        return column;
    }

    @Override
    public ColumnVo pretty() {

        return (ColumnVo) QmtVo.super.pretty();
    }
}
