package com.lesports.qmt.config.vo;

import com.lesports.qmt.config.model.Menu;
import com.lesports.qmt.mvc.QmtVo;
import org.apache.commons.beanutils.LeBeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by denghui on 2016/11/9.
 */
public class MenuVo extends Menu implements QmtVo {


    public MenuVo() {
    }

    public MenuVo(Menu menu) {
        this.setId(menu.getId());
        this.setName(menu.getName());
        this.setPlatforms(menu.getPlatforms());
        this.setCreateAt(menu.getCreateAt());
        this.setUpdateAt(menu.getUpdateAt());
    }

    public Menu toMenu() {
        //直接用类型转换得到的对象会报序列化错误
        Menu menu = new Menu();
        try {
            LeBeanUtils.copyProperties(menu, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
        return menu;
    }

}
