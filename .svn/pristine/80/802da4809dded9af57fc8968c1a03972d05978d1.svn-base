package com.lesports.qmt.config.service;

import com.lesports.qmt.config.model.Menu;
import com.lesports.qmt.config.vo.MenuItemVo;
import com.lesports.qmt.config.vo.MenuVo;
import com.lesports.qmt.mvc.QmtWebService;

import java.util.List;

/**
 * Created by denghui on 2016/11/9.
 */
public interface MenuWebService extends QmtWebService<MenuVo, Long> {

    /**
     * 发布菜单
     * @param id
     * @return
     */
    boolean publishMenu(long id);

    /**
     * 保存某个菜单下的菜单项
     * @param id
     * @param menuItem
     * @return
     */
    Long saveMenuItem(long id, Menu.MenuItem menuItem);

    /**
     * 获取菜单下的菜单项列表
     * @param id
     * @param indexOrName
     * @return
     */
    List<MenuItemVo> getMenuItems(long id, long parentIndex, String indexOrName);

    /**
     * 获取默认/强制推荐菜单
     * @param id
     * @param force
     * @return
     */
    List<MenuItemVo> getRecommendMenuItems(long id, Boolean force);

    /**
     * 删除菜单项
     * @param id
     * @param index
     * @return
     */
    boolean deleteMenuItem(long id, long index);

    /**
     * 重置菜单项顺序
     * @param id
     * @param indexes
     * @return
     */
    boolean resetOrder(long id, List<Long> indexes);

    /**
     * 重置推荐菜单项排序
     * @param id
     * @param indexes
     * @param force
     * @return
     */
    boolean resetRecommendOrder(long id, List<Long> indexes, Boolean force);

    /**
     * 获取一个菜单下的所有二级菜单列表
     * @param id
     * @return
     */
    List<MenuItemVo> getMenuSubItems(long id);
}
