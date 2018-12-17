package com.lesports.qmt.userinfo.web.controller;

import com.lesports.qmt.QmtConstants;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.userinfo.service.WorkbenchService;
import com.lesports.qmt.userinfo.web.vo.SubscriptionVo;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import com.lesports.utils.LeStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by denghui on 2017/2/15.
 */
@RestController
@RequestMapping(value="/workbench")
public class SubscriptionController {
    protected static final Logger LOG = LoggerFactory.getLogger(SubscriptionController.class);

    @Resource
    private WorkbenchService workbenchService;

    @PreAuthorize("hasPermission('lesports.qmt.home', 'READ')")
    @RequestMapping(value="/subscription", method= RequestMethod.GET)
    public QmtPage<SubscriptionVo> list(QmtPageParam pageParam) {
        try {
            return workbenchService.listSubscription(getValidPageParam(pageParam));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 订阅某个实体
     * @param ids
     * @return
     */
    @RequestMapping(value="/subscribe", method= RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasPermission('lesports.qmt.home', 'READ')")
    public boolean subscribe(@RequestParam(required = true) String ids) {
        try {
            return workbenchService.subscribe(LeStringUtils.commaString2LongList(ids));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 取消订阅
     * @param ids
     * @return
     */
    @RequestMapping(value="/unsubscribe", method= RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasPermission('lesports.qmt.home', 'READ')")
    public boolean unsubscribe(@RequestParam(required = true) String ids) {
        try {
            return workbenchService.unsubscribe(LeStringUtils.commaString2LongList(ids));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    protected QmtPageParam getValidPageParam(QmtPageParam page) {
        page.setPage(getValidPage(page.getPage()));
        page.setCount(getValidCount(page.getCount()));
        return page;
    }

    protected int getValidPage(int page) {
        return page > 0 ? page : 1;
    }

    protected int getValidCount(int count) {
        return count > 0 ? (count < QmtConstants.MAX_PAGE_SIZE ? count : QmtConstants.MAX_PAGE_SIZE)
                : QmtConstants.DEFAULT_WEB_PAGE_SIZE;
    }
}
