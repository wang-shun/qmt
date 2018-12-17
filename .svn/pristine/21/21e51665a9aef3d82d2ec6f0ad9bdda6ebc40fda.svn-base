package com.lesports.qmt.sbd.web.controller;

import com.lesports.id.api.IdType;
import com.lesports.qmt.log.WebLogAnnotation;
import com.lesports.qmt.mvc.QmtCallerParam;
import com.lesports.qmt.sbd.adapter.TopListItemAdapter;
import com.lesports.qmt.sbd.param.TopListItemParam;
import com.lesports.qmt.sbd.service.TopListWebService;
import com.lesports.qmt.sbd.vo.TopListVo;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by lufei1 on 2016/10/25.
 */
@RestController
@RequestMapping(value = "/topLists")
@WebLogAnnotation(ID_TYPE = IdType.TOP_LIST)
public class TopListController {
    private static final Logger LOG = LoggerFactory.getLogger(TopListController.class);

    @Resource
    private TopListWebService topListWebService;
    @Resource
    private TopListItemAdapter topListItemAdapter;

    /**
     * 根据条件查询榜单列表
     * 赛季id：csid
     *
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<TopListVo> list(@RequestParam Map<String, Object> params, QmtCallerParam callerParam) {
        try {
            return topListWebService.findByParams(params, callerParam.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TopListVo get(@PathVariable long id, QmtCallerParam callerParam) {
        try {
            return topListWebService.findOne(id, callerParam.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 保存/更新 榜单数据
     *
     * @param topListVo
     */
    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasPermission('lesports.qmt.dataManage.competitionList', 'ADD')")
    public boolean save(@ModelAttribute TopListVo topListVo, QmtCallerParam callerParam) {
        try {
            return topListWebService.save(topListVo, callerParam.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/publish/{id}", method = RequestMethod.GET)
    public boolean publish(@PathVariable long id, QmtCallerParam callerParam) {
        try {
            return topListWebService.publish(id, callerParam.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据榜单Id获取榜单item列表
     *
     * @param topListId
     */
    @RequestMapping(value = "/{topListId}/items", method = RequestMethod.GET)
    public List<Map<String, Object>> listTopListItem(@PathVariable long topListId, QmtCallerParam callerParam) {
        try {
            return topListWebService.listTopListItems(topListId, callerParam.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 保存/更新 榜单item数据
     *
     * @param topListId
     * @param param
     */
    @RequestMapping(value = "/{topListId}/items", method = RequestMethod.POST)
    @PreAuthorize("hasPermission('lesports.qmt.dataManage.competitionList', 'ADD')")
    public boolean saveTopListItem(@PathVariable long topListId, @ModelAttribute TopListItemParam param,
                                   BindingResult bindingResult, QmtCallerParam callerParam) {
        try {
            LOG.info("save topList: {} , topListItemParam :{}", topListId, param);
            if (bindingResult.hasErrors()) {
                LOG.error("binding error: {}", bindingResult.getAllErrors());
                throw new LeWebApplicationException("invalid params", LeStatus.PARAM_INVALID);
            }
            TopListVo.TopListItem topListItem = topListItemAdapter.toVo(param);
            return topListWebService.saveTopListItem(topListId, topListItem);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 删除榜单item数据
     *
     * @param topListId
     * @param topListItem
     */
    @RequestMapping(value = "/{topListId}/items", method = RequestMethod.DELETE)
    @PreAuthorize("hasPermission('lesports.qmt.dataManage.competitionList', 'DELETE')")
    public boolean deleteTopListItem(@PathVariable long topListId, @ModelAttribute TopListVo.TopListItem topListItem) {
        try {
            return topListWebService.deleteTopListItem(topListId, topListItem);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
