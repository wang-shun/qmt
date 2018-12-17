package com.lesports.qmt.sbd.web.controller;

import com.lesports.id.api.IdType;
import com.lesports.qmt.log.WebLogAnnotation;
import com.lesports.qmt.mvc.QmtCallerParam;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.sbd.adapter.TeamAdapter;
import com.lesports.qmt.sbd.param.TeamParam;
import com.lesports.qmt.sbd.service.TeamWebService;
import com.lesports.qmt.sbd.vo.TeamVo;
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
@RequestMapping(value = "/teams")
@WebLogAnnotation(ID_TYPE = IdType.TEAM)
public class TeamController {
    private static final Logger LOG = LoggerFactory.getLogger(TeamController.class);

    @Resource
    private TeamWebService teamWebService;
    @Resource
    private TeamAdapter teamAdapter;

    @RequestMapping(method = RequestMethod.GET)
    public QmtPage<TeamVo> list(@RequestParam Map<String, Object> params, QmtPageParam pageParam, QmtCallerParam callerParam) {
        try {
            return teamWebService.list(params, pageParam, callerParam.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TeamVo get(@PathVariable long id, QmtCallerParam callerParam) {
        try {
            return teamWebService.findOne(id, callerParam.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "batch", method = RequestMethod.GET)
    public List<TeamVo> getByIds(@RequestParam String ids, QmtCallerParam callerParam) {
        try {
            return teamWebService.getTeamsByIds(ids, callerParam.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasPermission('lesports.qmt.dataManage.teamList', 'ADD')")
    public boolean save(@ModelAttribute TeamParam teamParam, BindingResult bindingResult, QmtCallerParam callerParam) {
        try {
            LOG.info("save team, teamParam:{}", teamParam);
            if (bindingResult.hasErrors()) {
                LOG.error("binding error: {}", bindingResult.getAllErrors());
                throw new LeWebApplicationException("invalid params", LeStatus.PARAM_INVALID);
            }
            TeamVo team = teamAdapter.toVo(teamParam);
            return teamWebService.save(team, callerParam.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasPermission('lesports.qmt.dataManage.teamList', 'DELETE')")
    public boolean delete(@PathVariable long id) {
        try {
            return teamWebService.delete(id);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 更新上线/下线状态
     *
     * @param id
     * @param online
     * @param callerParam
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasPermission('lesports.qmt.dataManage.teamList', 'UPDATE')")
    public boolean updateOnlineStatus(@PathVariable long id, @RequestParam(value = "online", required = true) boolean online,
                                      QmtCallerParam callerParam) {
        try {
            return teamWebService.updateOnlineStatus(id, online, callerParam.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
