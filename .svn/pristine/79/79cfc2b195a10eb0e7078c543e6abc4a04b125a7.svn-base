package com.lesports.qmt.config.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.lesports.api.common.Platform;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.qmt.config.client.QmtConfigMenuInternalApis;
import com.lesports.qmt.config.converter.MenuVoConverter;
import com.lesports.qmt.config.model.Menu;
import com.lesports.qmt.config.service.MenuWebService;
import com.lesports.qmt.config.service.impl.support.AbstractConfigWebService;
import com.lesports.qmt.config.vo.MenuItemVo;
import com.lesports.qmt.config.vo.MenuVo;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import com.lesports.utils.LeStringUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by denghui on 2016/11/9.
 */
@Service("menuWebService")
public class MenuWebServiceImpl extends AbstractConfigWebService implements MenuWebService {

    public static final Function<Menu, MenuVo> VO_FUNCTION = new Function<Menu, MenuVo>() {
        @Nullable
        @Override
        public MenuVo apply(@Nullable Menu input) {
            return input == null ? null : new MenuVo(input);
        }
    };

    public static final Function<Menu.MenuItem, MenuItemVo> ITEM_VO_FUNCTION = new Function<Menu.MenuItem, MenuItemVo>() {
        @Nullable
        @Override
        public MenuItemVo apply(@Nullable Menu.MenuItem input) {
            return input == null ? null : new MenuItemVo(input);
        }
    };

    private static final Logger LOG = LoggerFactory.getLogger(MenuWebServiceImpl.class);

    @Resource
    private MenuVoConverter menuVoConverter;

    @Override
    public boolean publishMenu(long id) {
        return QmtConfigMenuInternalApis.publishMenu(id);
    }

    @Override
    public Long saveMenuItem(long id, Menu.MenuItem menuItem) {
        Menu menu = QmtConfigMenuInternalApis.getMenuById(id);
        if (menu == null) {
            LOG.warn("menu not exists: {}", id);
            return -1l;
        }

        List<Menu.MenuItem> items = menu.getItems();
        if (menuItem.getIndex() == null) {
            menuItem.setIndex(LeIdApis.nextId(IdType.MENU_ITEM));
            menuItem.setOrder(findMaxOrder(items,menuItem.getParentIndex()) + 1);
            items.add(menuItem);
        } else {
            for (Menu.MenuItem item : items){
                if (menuItem.getIndex().equals(item.getIndex())){
                    //allow empty
                    copyEditableProperties(menuItem, item);
                }
            }
        }
        return QmtConfigMenuInternalApis.saveMenu(menu);
    }

    private void copyEditableProperties(Menu.MenuItem menuItem, Menu.MenuItem existsMenuItem) {
        existsMenuItem.setName(menuItem.getName());
        existsMenuItem.setResourceType(menuItem.getResourceType());
        existsMenuItem.setPubChannels(menuItem.getPubChannels());
        existsMenuItem.setImages(menuItem.getImages());
        existsMenuItem.setResourceId(menuItem.getResourceId());
        existsMenuItem.setNewsResourceId(menuItem.getNewsResourceId());
        existsMenuItem.setNewsAutoResourceId(menuItem.getNewsAutoResourceId());
        existsMenuItem.setMatchResourceId(menuItem.getMatchResourceId());
        existsMenuItem.setCarouselResourceId(menuItem.getCarouselResourceId());
        existsMenuItem.setMatchCid(menuItem.getMatchCid());
        existsMenuItem.setToplistCid(menuItem.getToplistCid());
        existsMenuItem.setTeamId(menuItem.getTeamId());
        existsMenuItem.setCampId(menuItem.getCampId());
        existsMenuItem.setCampCategoryId(menuItem.getCampCategoryId());
        existsMenuItem.setUrl(menuItem.getUrl());
        existsMenuItem.setmUrl(menuItem.getmUrl());
        existsMenuItem.setNew(menuItem.getNew());
        existsMenuItem.setHot(menuItem.getHot());
        existsMenuItem.setTeamData(menuItem.getTeamData());
        existsMenuItem.setPlayerData(menuItem.getPlayerData());
        existsMenuItem.setScheduleType(menuItem.getScheduleType());
        existsMenuItem.setRecommend(menuItem.getRecommend());
        existsMenuItem.setRecommendOrder(menuItem.getRecommendOrder());
        existsMenuItem.setForceRecommend(menuItem.getForceRecommend());
        existsMenuItem.setForceRecommendOrder(menuItem.getForceRecommendOrder());
    }

    @Override
    public List<MenuItemVo> getMenuItems(long id, long parentIndex, String indexOrName) {
        Menu menu = QmtConfigMenuInternalApis.getMenuById(id);
        if (menu == null) {
            LOG.warn("menu not exists: {}", id);
            return Collections.EMPTY_LIST;
        }

        List<Menu.MenuItem> items = Lists.newArrayList(Collections2.filter(menu.getItems(), input -> {
            if (parentIndex >= 0 && !input.getParentIndex().equals(parentIndex)) {
                return false;
            }
            if (StringUtils.isNotEmpty(indexOrName) && !input.getName().contains(indexOrName)
                    && !input.getIndex().toString().equals(indexOrName)) {
                return false;
            }
            return true;
        }));
        Collections.sort(items, ((o1, o2) -> o1.getOrder() - o2.getOrder()));
        return Lists.transform(items, ITEM_VO_FUNCTION);
    }

    @Override
    public List<MenuItemVo> getRecommendMenuItems(long id, Boolean force) {
        Menu menu = QmtConfigMenuInternalApis.getMenuById(id);
        if (menu == null) {
            LOG.warn("menu not exists: {}", id);
            return Collections.EMPTY_LIST;
        }

        boolean tv = menu.getPlatforms().contains(Platform.TV);
        // tv的推荐取1级菜单 app的推荐取2级菜单
        List<Menu.MenuItem> result = Lists.newArrayList();
        if (LeNumberUtils.toBoolean(force)) {
            for (Menu.MenuItem menuItem : menu.getItems()) {
                if (LeNumberUtils.toBoolean(menuItem.getForceRecommend()) && ((tv && LeNumberUtils.toInt(menuItem.getLevel()) == 1)
                        || !tv && LeNumberUtils.toInt(menuItem.getLevel()) == 2)) {
                    result.add(menuItem);
                }
            }
        } else {
            for (Menu.MenuItem menuItem : menu.getItems()) {
                if (LeNumberUtils.toBoolean(menuItem.getRecommend()) && ((tv && LeNumberUtils.toInt(menuItem.getLevel()) == 1)
                        || !tv && LeNumberUtils.toInt(menuItem.getLevel()) == 2)) {
                    result.add(menuItem);
                }
            }
        }

        if (LeNumberUtils.toBoolean(force)) {
            Collections.sort(result, (o1, o2) -> {
                if (o1.getForceRecommendOrder() == null && o2.getForceRecommendOrder() != null) {
                    return 1;
                } else if (o1.getForceRecommendOrder() != null && o2.getForceRecommendOrder() == null) {
                    return -1;
                } else if (o1.getForceRecommendOrder() == null && o2.getForceRecommendOrder() == null) {
                    return 0;
                } else {
                    return o1.getForceRecommendOrder() - o2.getForceRecommendOrder();
                }
            });
        } else {
            Collections.sort(result, (o1, o2) -> {
                if (o1.getRecommendOrder() == null && o2.getRecommendOrder() != null) {
                    return 1;
                } else if (o1.getRecommendOrder() != null && o2.getRecommendOrder() == null) {
                    return -1;
                } else if (o1.getRecommendOrder() == null && o2.getRecommendOrder() == null) {
                    return 0;
                } else {
                    return o1.getRecommendOrder() - o2.getRecommendOrder();
                }
            });
        }
        return Lists.transform(result, ITEM_VO_FUNCTION);
    }


    @Override
    public boolean deleteMenuItem(long id, long index) {
        Menu menu = QmtConfigMenuInternalApis.getMenuById(id);
        if (menu == null) {
            LOG.warn("menu not exists: {}", id);
            return false;
        }

        Menu.MenuItem deletedItem = null;
        Iterator<Menu.MenuItem> iterator = menu.getItems().iterator();
        while (iterator.hasNext()) {
            Menu.MenuItem item = iterator.next();
            if (LeNumberUtils.toLong(item.getIndex()) == index) {
                iterator.remove();
                deletedItem = item;
            }
        }
        if (deletedItem != null) {
            //重新计算Order
            for (Menu.MenuItem item : menu.getItems()) {
                if (item.getLevel().equals(deletedItem.getLevel()) && item.getOrder() > deletedItem.getOrder()) {
                    item.setOrder(item.getOrder() - 1);
                }
            }
            //删除一级菜单项级联删除下面的二级菜单项
            if (deletedItem.getLevel().equals(1)) {
                iterator = menu.getItems().iterator();
                while (iterator.hasNext()) {
                    Menu.MenuItem item = iterator.next();
                    if (item.getLevel().equals(2) && item.getParentIndex().equals(deletedItem.getIndex())) {
                        iterator.remove();
                    }
                }
            }
            return QmtConfigMenuInternalApis.saveMenu(menu, true) > 0;
        }
        LOG.warn("menu index not exists: {}", index);
        return false;
    }

    @Override
    public boolean resetOrder(long id, List<Long> indexes) {
        Menu menu = QmtConfigMenuInternalApis.getMenuById(id);
        if (menu == null) {
            LOG.warn("menu not exists: {}", id);
            return false;
        }

        // check indexes
        int level = 0, order = 1;
        for (Long index : indexes) {
            Menu.MenuItem item = new Menu.MenuItem();
            item.setIndex(index);
            int i = menu.getItems().indexOf(item);
            if (i < 0) {
                LOG.warn("menu index not exists: {}", index);
                return false;
            }
            item = menu.getItems().get(i);
            if (level == 0) {
                level = item.getLevel();
            } else if (level != item.getLevel()) {
                LOG.warn("menu index level not equal: {}", indexes);
                return false;
            }
            item.setOrder(order++);
        }
        Collections.sort(menu.getItems(), ((o1, o2) -> o1.getOrder() - o2.getOrder()));
        return QmtConfigMenuInternalApis.saveMenu(menu) > 0;
    }

    @Override
    public boolean resetRecommendOrder(long id, List<Long> indexes, Boolean force) {
        Menu menu = QmtConfigMenuInternalApis.getMenuById(id);
        if (menu == null) {
            LOG.warn("menu not exists: {}", id);
            return false;
        }

        if (LeNumberUtils.toBoolean(force)) {
            for (Menu.MenuItem menuItem : menu.getItems()) {
                if (LeNumberUtils.toBoolean(menuItem.getForceRecommend())) {
                    for (int i = 0; i < indexes.size(); i++) {
                        if (menuItem.getIndex().equals(indexes.get(i))) {
                            menuItem.setForceRecommendOrder(i + 1);
                        }
                    }
                }
            }
        } else {
            for (Menu.MenuItem menuItem : menu.getItems()) {
                if (LeNumberUtils.toBoolean(menuItem.getRecommend())) {
                    for (int i = 0; i < indexes.size(); i++) {
                        if (menuItem.getIndex().equals(indexes.get(i))) {
                            menuItem.setRecommendOrder(i + 1);
                        }
                    }
                }
            }
        }
        Collections.sort(menu.getItems(), ((o1, o2) -> o1.getOrder() - o2.getOrder()));
        return QmtConfigMenuInternalApis.saveMenu(menu) > 0;
    }

    @Override
    public List<MenuItemVo> getMenuSubItems(long id) {
        Menu menu = QmtConfigMenuInternalApis.getMenuById(id);
        if (menu == null) {
            LOG.warn("menu not exists: {}", id);
            return Collections.EMPTY_LIST;
        }

        List<Menu.MenuItem> menuItems = Lists.newArrayList();
        for (Menu.MenuItem item : menu.getItems()) {
            if (LeNumberUtils.toInt(item.getLevel()) == 2) {
                menuItems.add(item);
            }
        }
        return Lists.transform(menuItems, ITEM_VO_FUNCTION);
    }

    @Override
    public Long saveWithId(MenuVo entity) {
        if (entity.getId() == null) {
            return doInsert(entity);
        } else {
            return doUpdate(entity);
        }
    }

    private Long doUpdate(MenuVo entity) {
        Menu existsMenu = QmtConfigMenuInternalApis.getMenuById(entity.getId());
        menuVoConverter.copyEditableProperties(existsMenu, entity);
        existsMenu.setUpdater(getOperator());
        return QmtConfigMenuInternalApis.saveMenu(existsMenu, true);
    }

    private Long doInsert(MenuVo entity) {
        entity.setCreator(getOperator());
        long id = QmtConfigMenuInternalApis.saveMenu(entity.toMenu());
        entity.setId(id);
        return id;
    }

    @Override
    public MenuVo findOne(Long id) {
        Menu menu = QmtConfigMenuInternalApis.getMenuById(id);
        return menu == null ? null : new MenuVo(menu);
    }

    @Override
    public boolean delete(Long id) {
        return QmtConfigMenuInternalApis.deleteMenu(id);
    }

    @Override
    public QmtPage<MenuVo> list(Map<String, Object> params, QmtPageParam pageParam) {
        InternalQuery query = new InternalQuery();
        query.addCriteria(InternalCriteria.where("deleted").is(false));
        String platforms = MapUtils.getString(params, "platforms", null);
        if (StringUtils.isNotBlank(platforms)) {
            List<String> platformList = LeStringUtils.commaString2StringList(platforms);
            query.addCriteria(InternalCriteria.where("platforms").in(platformList));
        }
        addParamsCriteriaToQuery(query, params, pageParam);

        long total = QmtConfigMenuInternalApis.countMenuByQuery(query);
        if (total <= 0) {
            return new QmtPage<>(Collections.EMPTY_LIST, pageParam, 0);
        }
        List<Menu> menus = QmtConfigMenuInternalApis.getMenusByQuery(query);
        return new QmtPage<>(Lists.transform(menus, VO_FUNCTION), pageParam, total);
    }

    private int findMaxOrder(List<Menu.MenuItem> items, long parentIndex){
        int maxOrder = 0;
        if (CollectionUtils.isNotEmpty(items)) {
            List<Menu.MenuItem> filteredItems = Lists.newArrayList(Collections2.filter(items,
                    input -> LeNumberUtils.toLong(input.getParentIndex()) == parentIndex));
            Collections.sort(filteredItems, ((o1, o2) -> o2.getOrder() - o1.getOrder()));
            if (CollectionUtils.isNotEmpty(filteredItems)) {
                maxOrder = filteredItems.get(0).getOrder();
            }
        }
        return maxOrder;
    }
}
