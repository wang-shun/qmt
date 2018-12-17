package com.lesports.qmt.sbd.web.controller;

import com.lesports.id.api.IdType;
import com.lesports.qmt.log.WebLogAnnotation;
import com.lesports.qmt.mvc.QmtCallerParam;
import com.lesports.qmt.sbd.adapter.TeamSeasonAdapter;
import com.lesports.qmt.sbd.param.TeamSeasonParam;
import com.lesports.qmt.sbd.service.TeamSeasonWebService;
import com.lesports.qmt.sbd.vo.TeamSeasonVo;
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
@RequestMapping(value = "/teamSeasons")
@WebLogAnnotation(ID_TYPE = IdType.TEAM_SEASON)
public class TeamSeasonController {
    private static final Logger LOG = LoggerFactory.getLogger(TeamSeasonController.class);

    @Resource
    private TeamSeasonWebService teamSeasonWebService;
    @Resource
    private TeamSeasonAdapter teamSeasonAdapter;

    /**
     * 根据条件查询teamseason
     * tid 球队id
     * csid 赛季id
     *
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<TeamSeasonVo> list(@RequestParam Map<String, Object> params, QmtCallerParam callerParam) {
        try {
            return teamSeasonWebService.findByParams(params, callerParam.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TeamSeasonVo get(@PathVariable long id, QmtCallerParam callerParam) {
        try {
            return teamSeasonWebService.findOne(id, callerParam.getCallerParam());
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
    public boolean save(@ModelAttribute TeamSeasonParam param, BindingResult bindingResult, QmtCallerParam callerParam) {
        try {
            LOG.info("save teamSeason, teamSeasonParam:{}", param);
            if (bindingResult.hasErrors()) {
                LOG.error("binding error: {}", bindingResult.getAllErrors());
                throw new LeWebApplicationException("invalid params", LeStatus.PARAM_INVALID);
            }
            TeamSeasonVo teamSeasonVo = teamSeasonAdapter.toVo(param);
            return teamSeasonWebService.save(teamSeasonVo, callerParam.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
