package com.lesports.qmt.cms.admin.web.vo;

import com.lesports.qmt.cms.model.ColumnPage;
import com.lesports.qmt.mvc.QmtVo;
import com.lesports.utils.LeStringUtils;
import org.apache.commons.beanutils.LeBeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * User: ellios
 * Time: 16-12-1 : 下午7:12
 */
public class ColumnPageVo extends ColumnPage implements QmtVo {

    private static final long serialVersionUID = 8934060215687118667L;

    private String wPaths;

    public String getwPaths() {
        return wPaths;
    }

    public void setwPaths(String wPaths) {
        this.wPaths = wPaths;
    }

    public ColumnPageVo() {
    }

    public ColumnPageVo(ColumnPage page) {
        try {
            LeBeanUtils.copyProperties(this, page);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        this.wPaths = LeStringUtils.toCommaString(page.getWidgetPaths());

    }

    public ColumnPage toModel() {
        //直接用类型转换得到的对象会报序列化错误
        ColumnPage page = new ColumnPage();
        try {
            LeBeanUtils.copyProperties(page, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        page.setWidgetPaths(LeStringUtils.commaString2StringList(this.getwPaths()));
        return page;
    }

    @Override
    public ColumnPageVo pretty() {
        return (ColumnPageVo) QmtVo.super.pretty();
    }


}
