package com.lesports.qmt.sbc.web.controller;

import com.lesports.api.common.LiveStatus;
import com.lesports.api.common.PublishStatus;
import com.lesports.id.api.IdType;
import com.lesports.qmt.log.WebLogAnnotation;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.mvc.support.AbstractQmtController;
import com.lesports.qmt.sbc.converter.EpisodeVoConverter;
import com.lesports.qmt.sbc.param.EpisodeParam;
import com.lesports.qmt.sbc.service.EpisodeWebService;
import com.lesports.qmt.sbc.vo.EpisodeVo;
import com.lesports.qmt.tlive.TextLiveInternalApis;
import com.lesports.qmt.tlive.api.common.TextLiveType;
import com.lesports.qmt.tlive.model.TextLive;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by lufei1 on 2016/11/4.
 */
@RestController
@RequestMapping(value = "/episodes")
@WebLogAnnotation(ID_TYPE = IdType.EPISODE)
public class EpisodeController extends AbstractQmtController<EpisodeVo, Long> {

    private static final Logger LOG = LoggerFactory.getLogger(EpisodeController.class);

    @Resource
    private EpisodeWebService episodeWebService;
    @Resource
    private EpisodeVoConverter episodeVoConverter;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Override
    public EpisodeVo getOneById(@PathVariable Long id) {
        try {
            return pretty(episodeWebService.findOne(id, CallerUtils.getDefaultCaller()));
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasPermission('lesports.qmt.content.episodeList', 'ADD')")
    public Boolean save(@ModelAttribute EpisodeParam episodeParam) {
        try {
            LOG.info("save episode, episodeParam:{}", episodeParam);
            EpisodeVo episodeVo = episodeVoConverter.toVo(episodeParam);
            Long id = episodeWebService.saveWithId(episodeVo);
            return LeNumberUtils.toLong(id) > 0 ? true : false;
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Override
    public QmtPage<EpisodeVo> list(Map<String, Object> params, QmtPageParam pageParam) {
        try {
            return episodeWebService.list(params, pageParam);
        } catch (LeWebApplicationException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 上/下线节目操作
     *
     * @param id
     * @param status
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasPermission('lesports.qmt.content.episodeList', 'UPDATE')")
    public boolean updateOnlineStatus(@PathVariable long id,
                                      @RequestParam(value = "status", required = true) String status) {
        try {
            boolean result = episodeWebService.updateOnlineStatus(id, PublishStatus.valueOf(status));
            LOG.info("set episode online status, id:{}, status:{}", id, status);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 增加图文直播,增加数据直播
     *
     * @param eid
     * @param textLiveType
     * @return
     */
    @RequestMapping(value = "/addTextLive", method = RequestMethod.GET)
    @ResponseBody
    public Long addTextLive(@RequestParam(value = "eid", required = false) Long eid,
                            @RequestParam(value = "mid", required = false) Long mid,
                            @RequestParam(value = "textLiveType", required = true) int textLiveType) {
        try {
            TextLive textLive = new TextLive();
            if (textLiveType == TextLiveType.PLAY_BY_PLAY.getValue()) {
                textLive.setLatestPartnerId("-1");
            } else {
                textLive.setStatus(LiveStatus.LIVE_NOT_START);
            }
            if (LeNumberUtils.toLong(eid) > 0) {
                textLive.setEid(eid);
            }
            if (LeNumberUtils.toLong(mid) > 0) {
                textLive.setMid(mid);
            }
            textLive.setType(TextLiveType.findByValue(textLiveType));
            LOG.info("add textLive. eid :{},mid :{},textLiveType:{}", eid, mid, textLiveType);
            return TextLiveInternalApis.saveTextLive(textLive);
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 删除图文直播,数据直播
     *
     * @param textLiveId
     * @return
     */
    @RequestMapping(value = "/deleteTextLive", method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteTextLive(@RequestParam(value = "textLiveId", required = true) long textLiveId) {
        try {
            LOG.info("delete textLive.textLiveId :{} ", textLiveId);
            return TextLiveInternalApis.deleteTextLive(textLiveId);
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Long add(EpisodeVo vo) {
        return null;
    }

    @Override
    public boolean update(EpisodeVo vo) {
        return false;
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }
}
