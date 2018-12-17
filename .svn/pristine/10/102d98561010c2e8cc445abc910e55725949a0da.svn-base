package com.lesports.qmt.sbd.web.controller;

import com.lesports.id.api.IdType;
import com.lesports.qmt.log.WebLogAnnotation;
import com.lesports.qmt.mvc.QmtCallerParam;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.sbd.adapter.PlayerAdapter;
import com.lesports.qmt.sbd.param.PlayerParam;
import com.lesports.qmt.sbd.service.PlayerWebService;
import com.lesports.qmt.sbd.vo.PlayerVo;
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
@RequestMapping(value = "/players")
@WebLogAnnotation(ID_TYPE = IdType.PLAYER)
public class PlayerController {
    private static final Logger LOG = LoggerFactory.getLogger(PlayerController.class);

    @Resource
    private PlayerWebService playerWebService;
    @Resource
    private PlayerAdapter playerAdapter;


    @RequestMapping(method = RequestMethod.GET)
    public QmtPage<PlayerVo> list(@RequestParam Map<String, Object> params, QmtPageParam pageParam, QmtCallerParam callerParam) {
        try {
            return playerWebService.list(params, pageParam, callerParam.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PlayerVo get(@PathVariable long id, QmtCallerParam callerParam) {
        try {
            return playerWebService.findOne(id, callerParam.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/batch", method = RequestMethod.GET)
    public List<PlayerVo> get(@RequestParam String ids, QmtCallerParam callerParam) {
        try {
            return playerWebService.getPlayersByIds(ids, callerParam.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasPermission('lesports.qmt.dataManage.playerList', 'ADD')")
    public boolean save(@ModelAttribute PlayerParam playerParam, BindingResult bindingResult, QmtCallerParam callerParam) {
        try {
            LOG.info("save player, playerParam:{}", playerParam);
            if (bindingResult.hasErrors()) {
                LOG.error("binding error: {}", bindingResult.getAllErrors());
                throw new LeWebApplicationException("invalid params", LeStatus.PARAM_INVALID);
            }
            PlayerVo playerVo = playerAdapter.toVo(playerParam);
            return playerWebService.save(playerVo, callerParam.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasPermission('lesports.qmt.dataManage.playerList', 'DELETE')")
    public boolean delete(@PathVariable long id) {
        try {
            return playerWebService.delete(id);
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
    @PreAuthorize("hasPermission('lesports.qmt.dataManage.playerList', 'UPDATE')")
    public boolean updateOnlineStatus(@PathVariable long id, @RequestParam(value = "online", required = true) boolean online,
                                      QmtCallerParam callerParam) {
        try {
            return playerWebService.updateOnlineStatus(id, online, callerParam.getCallerParam());
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
