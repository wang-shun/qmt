package com.lesports.qmt.config.web.controller.menu;

import com.lesports.id.api.IdType;
import com.lesports.qmt.config.client.QmtConfigMenuInternalApis;
import com.lesports.qmt.config.converter.MenuVoConverter;
import com.lesports.qmt.config.model.Menu;
import com.lesports.qmt.config.param.SaveMenuItemParam;
import com.lesports.qmt.config.param.SaveMenuParam;
import com.lesports.qmt.config.service.MenuWebService;
import com.lesports.qmt.config.vo.MenuItemVo;
import com.lesports.qmt.config.vo.MenuVo;
import com.lesports.qmt.log.WebLogAnnotation;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.mvc.support.AbstractQmtController;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import com.lesports.utils.LeStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by denghui on 2016/11/10.
 */
@RestController
@RequestMapping(value="/pcMMenus")
@WebLogAnnotation(ID_TYPE = IdType.MENU)
public class PCMMenuController extends AbstractQmtController<MenuVo, Long> {

    private static final Logger LOG = LoggerFactory.getLogger(PCMMenuController.class);

    @Resource
    private MenuWebService menuWebService;
    @Resource
    private MenuVoConverter menuVoConverter;

    /**
     * 保存菜单
     * @param param
     * @return
     */
    @RequestMapping(method= RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasPermission('lesports.qmt.pcmConf.PCmenu', 'ADD')")
    public Long save(@Validated @ModelAttribute SaveMenuParam param, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                LOG.error("binding error: {}", bindingResult.getAllErrors());
                throw new LeWebApplicationException( "invalid params", LeStatus.PARAM_INVALID);
            }

            MenuVo menuVo = menuVoConverter.toVo(param);
            Long result = menuWebService.saveWithId(menuVo);
            LOG.info("save menu, param:{}, result:{}", menuVo, result);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据id获取菜单
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    @Override
    public MenuVo getOneById(@PathVariable Long id) {
        try {
            return pretty(menuWebService.findOne(id));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    @ResponseBody
    @Override
    @PreAuthorize("hasPermission('lesports.qmt.pcmConf.PCmenu', 'DELETE')")
    public boolean delete(@PathVariable Long id) {
        try {
            boolean result = menuWebService.delete(id);
            LOG.info("delete menu, id:{}, result:{}", id, result);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 发布菜单
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasPermission('lesports.qmt.pcmConf.PCmenu', 'UPDATE')")
    public boolean publishMenu(@PathVariable Long id) {
        try {
            boolean result = menuWebService.publishMenu(id);
            LOG.info("publish menu item, id:{}, result:{}", id, result);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 查询菜单列表
     * @param params
     * @param pageParam
     * @return
     */
    @RequestMapping(method=RequestMethod.GET)
    @Override
    public QmtPage<MenuVo> list(@RequestParam Map<String, Object> params, QmtPageParam pageParam) {
        try {
            LOG.info("list menu, params:{}, pageParam: {}", params, pageParam);
            return pretty(menuWebService.list(params, getValidPageParam(pageParam)));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取菜单下的一级/二级菜单项列表
     * @param id
     * @param parentIndex
     * @param indexOrName
     * @param pageParam
     * @return
     */
    @RequestMapping(value = "/{id}/items", method=RequestMethod.GET)
    public QmtPage<MenuItemVo> getMenuItems(@PathVariable long id,
                                            @RequestParam(required = false, defaultValue = "0") long parentIndex,
                                            @RequestParam(required = false) String indexOrName,
                                            QmtPageParam pageParam) {
        try {
            List<MenuItemVo> full = menuWebService.getMenuItems(id, parentIndex, indexOrName);
            return createPage(getValidPageParam(pageParam), full);
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取一个菜单下的所有二级菜单列表
     * @param id
     * @param pageParam
     * @return
     */
    @RequestMapping(value = "/{id}/subItems", method=RequestMethod.GET)
    public QmtPage<MenuItemVo> getMenuItems(@PathVariable long id,
                                            QmtPageParam pageParam) {
        try {
            List<MenuItemVo> full = menuWebService.getMenuSubItems(id);
            return createPage(getValidPageParam(pageParam), full);
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private QmtPage<MenuItemVo> createPage(QmtPageParam pageParam, List<MenuItemVo> full) {
        List<MenuItemVo> result = null;
        int fromIndex = (pageParam.getPage() - 1) * pageParam.getCount();
        if (fromIndex >= full.size()) {
            result = Collections.EMPTY_LIST;
        }
        int toIndex = pageParam.getPage() * pageParam.getCount();
        if (toIndex >= full.size()) {
            toIndex = full.size();
        }
        if (result == null) {
            result = full.subList(fromIndex, toIndex);
        }
        return new QmtPage<>(result, pageParam, full.size());
    }

    /**
     * 获取默认/强制推荐菜单
     * @param id
     * @param force
     * @return
     */
    @RequestMapping(value = "/{id}/recommendItems", method=RequestMethod.GET)
    public List<MenuItemVo> getRecommendMenuItems(@PathVariable long id,
                                                     @RequestParam(defaultValue = "false", required = false) Boolean force) {
        try {
            return menuWebService.getRecommendMenuItems(id, force);
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 保存菜单项
     * @param id
     * @param param
     * @return
     */
    @RequestMapping(value = "/{id}/items", method=RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasPermission('lesports.qmt.pcmConf.PCmenu', 'ADD')")
    public Long saveMenuItem(@PathVariable long id, @ModelAttribute SaveMenuItemParam param, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                LOG.error("binding error: {}", bindingResult.getAllErrors());
                throw new LeWebApplicationException( "invalid params", LeStatus.PARAM_INVALID);
            }

            Menu.MenuItem item = param.toMenuItem();
            Long result = menuWebService.saveMenuItem(id, item);
            LOG.info("save menu item, id:{}, index:{}, result:{}", id, result);
            return item.getIndex();
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取某菜单项
     * @param id
     * @param index
     * @return
     */
    @RequestMapping(value = "/{id}/items/{index}", method=RequestMethod.GET)
    public MenuItemVo getMenuItem(@PathVariable long id, @PathVariable long index) {
        try {
            Menu menu = QmtConfigMenuInternalApis.getMenuById(id);
            if (menu != null) {
                for (Menu.MenuItem item : menu.getItems()) {
                    if (item.getIndex().equals(index)) {
                        return new MenuItemVo(item);
                    }
                }
            }
            return null;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 删除菜单项
     * @param id
     * @param index
     * @return
     */
    @RequestMapping(value = "/{id}/items/{index}", method=RequestMethod.DELETE)
    @ResponseBody
    @PreAuthorize("hasPermission('lesports.qmt.pcmConf.PCmenu', 'DELETE')")
    public boolean deleteMenuItem(@PathVariable long id, @PathVariable long index) {
        try {
            boolean result = menuWebService.deleteMenuItem(id, index);
            LOG.info("delete menu item, id:{}, index:{}, result:{}", id, result);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 重置菜单项顺序
     * @param id
     * @param indexes
     * @return
     */
    @RequestMapping(value = "/{id}/items/order", method=RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasPermission('lesports.qmt.pcmConf.PCmenu', 'UPDATE')")
    public boolean resetOrder(@PathVariable long id, @RequestParam(required = true) String indexes) {
        try {
            boolean result = menuWebService.resetOrder(id, LeStringUtils.commaString2LongList(indexes));
            LOG.info("resetOrder, id:{}, indexes:{}, result:{}", id, indexes, result);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 重置推荐菜单项顺序
     * @param id
     * @param indexes
     * @return
     */
    @RequestMapping(value = "/{id}/recommendItems/order", method=RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasPermission('lesports.qmt.pcmConf.PCmenu', 'UPDATE')")
    public boolean resetRecommendOrder(@PathVariable long id,
                              @RequestParam(defaultValue = "false", required = false) Boolean force,
                              @RequestParam(required = true) String indexes) {
        try {
            boolean result = menuWebService.resetRecommendOrder(id, LeStringUtils.commaString2LongList(indexes), force);
            LOG.info("resetRecommendOrder, id:{}, indexes:{}, force:{}, result:{}", id, indexes, force, result);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
