package com.lesports.qmt.config.vo;

import com.lesports.qmt.config.model.Menu;
import com.lesports.qmt.mvc.QmtVo;
import org.apache.commons.beanutils.LeBeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by denghui on 2016/11/16.
 */
public class MenuItemVo extends Menu.MenuItem implements QmtVo {

    public MenuItemVo() {
    }

    public MenuItemVo(Menu.MenuItem item) {
        try {
            LeBeanUtils.copyProperties(this, item);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
    }

}
