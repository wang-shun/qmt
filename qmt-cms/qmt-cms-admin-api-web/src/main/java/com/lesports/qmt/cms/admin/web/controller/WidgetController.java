package com.lesports.qmt.cms.admin.web.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.LeConstants;
import com.lesports.qmt.cms.admin.web.service.WidgetWebService;
import com.lesports.qmt.cms.admin.web.vo.WidgetVo;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.mvc.support.AbstractQmtController;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import com.lesports.utils.LeStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * User: ellios
 * Time: 16-12-6 : 上午10:22
 */
@RestController
@RequestMapping(value = "/widgets")
public class WidgetController extends AbstractQmtController<WidgetVo, Long> {

    @Resource
    private WidgetWebService widgetWebService;

    /**
     * 根据id获取栏目页详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Override
    public WidgetVo getOneById(@PathVariable Long id) {
        try {
            return pretty(widgetWebService.findOne(id));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据id获取栏目页详情
     *
     * @param ids
     * @param names
     * @return
     */
    @RequestMapping(value = "/batch", method = RequestMethod.GET)
    public Map<String, Map<String, Object>> batchGet(@RequestParam(value = "ids", required = false) String ids,
                             @RequestParam(value = "names", required = false) String names,
                             @RequestParam(value = "paths", required = false) String paths,
                             @RequestParam(value = "type", defaultValue = "pc") String type,
                             @RequestParam("fields") String fields) {
        try {
            if(StringUtils.isEmpty(ids) && StringUtils.isEmpty(names) && StringUtils.isEmpty(paths)){
                throw new LeWebApplicationException("illegal param,  one of ids or names must not be empty", LeStatus.BAD_REQUEST);
            }
            List<String> fieldList = LeStringUtils.commaString2StringList(fields);
            if(StringUtils.isNotEmpty(ids)){
                List<Long> idList = LeStringUtils.commaString2LongList(ids);
                return widgetWebService.findByIds5Fields(idList, fieldList);
            }
            if(StringUtils.isNotEmpty(names)){
                List<String> nameList = LeStringUtils.commaString2StringList(names);
                return widgetWebService.findByNames5Fields(nameList, fieldList);
            }
            if(StringUtils.isNotEmpty(paths)){
                type = StringUtils.lowerCase(type);
                List<String> pathList = LeStringUtils.commaString2StringList(paths);
                return widgetWebService.findByPaths5Fields(pathList, type, fieldList);
            }
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
        return Maps.newHashMap();
    }

    /**
     * 删除新闻
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @Override
    public boolean delete(@PathVariable Long id) {
        try {
            boolean result = widgetWebService.delete(id);
            LOG.info("delete widgets, id:{}, result:{}", id, result);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据条件查询新闻列表
     *
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @Override
    public QmtPage<WidgetVo> list(@RequestParam Map<String, Object> params, QmtPageParam pageParam) {
        try {
            LOG.info("list widgets, params:{}, pageParam : {}", params, pageParam);
            return pretty(widgetWebService.list(params, getValidPageParam(pageParam)));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 添加栏目页
     *
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @Override
    public Long add(WidgetVo vo) {
        long result = -1;
        try {

            result = widgetWebService.saveWithId(vo);
            LOG.info("add widgets with vo : {}, result : {}", vo, result);
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 更新栏目页
     *
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    @Override
    public boolean update(WidgetVo vo) {
        boolean result = false;
        try {
            if (vo.getId() == null) {
                LOG.error("fail to update widget, for vo has no id.");
                throw new LeWebApplicationException("illegal param", LeStatus.BAD_REQUEST);
            }
            result = widgetWebService.save(vo);
            LOG.info("update widget with vo : {}, result : {}", vo, result);
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

}
