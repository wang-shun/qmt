package com.lesports.qmt.sbc.web.controller;

import com.lesports.id.api.IdType;
import com.lesports.qmt.config.model.Tag;
import com.lesports.qmt.log.WebLogAnnotation;
import com.lesports.qmt.sbc.model.Video;
import com.lesports.qmt.sbc.param.VideoListParam;
import com.lesports.qmt.sbc.param.VideoParam;
import com.lesports.qmt.sbc.service.VideoService;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangxudong@le.com on 2016/10/25.
 */
@RestController
@RequestMapping(value = "/videos")
@WebLogAnnotation(ID_TYPE = IdType.VIDEO)
public class    VideoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoController.class);

    @Resource
    private VideoService videoService;

    // 获取视频列表
    @RequestMapping(value = "", method = RequestMethod.GET)
    //todo 增加视频查询条件，参数见新版prd
    public Page<VideoListParam> queryVideoPage(
            @RequestParam(value = "type", required = false, defaultValue = "-1") int type,
            @RequestParam(value = "channel", required = false, defaultValue = "0") long channel,
            @RequestParam(value = "cid", required = false, defaultValue = "0") long cid,
            @RequestParam(value = "id", required = false, defaultValue = "0") long id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "20") int pageSize) {
        Page<VideoListParam> result = videoService.getVideoPage(pageNumber, pageSize, id, name, type, channel, cid);
        return result;
    }

    // 获取视频列表
    @RequestMapping(value = "/album", method = RequestMethod.GET)
    public Page<VideoListParam> queryVideoPage(
            @RequestParam(value = "aid", required = false, defaultValue = "0") long aid,
            @RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "20") int pageSize) {
        Page<VideoListParam> result = videoService.getVideoPage(pageNumber, pageSize, aid);
        return result;
    }

    // 视频移出专辑
    @RequestMapping(value = "/offAlbum", method = RequestMethod.PUT)
    @PreAuthorize("hasPermission('lesports.qmt.video.videoList', 'ADD')")
    public boolean modifyVideo(@RequestParam(value = "id", required = false, defaultValue = "0") long id) {
        return videoService.moveVideoFromAlbum(id);
    }

    // 添加视频到专辑
    @RequestMapping(value = "/toAlbum", method = RequestMethod.PUT)
    @PreAuthorize("hasPermission('lesports.qmt.video.videoList', 'ADD')")
    public boolean addVideoToAlbum(@RequestParam(value = "aid", required = false, defaultValue = "0") long aid,
                                   @RequestParam(value = "ids", required = false, defaultValue = "") String ids) {
        return videoService.addVideoToAlbum(aid, ids);
    }

    // 根据ID获取视频详情
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Video queryVideoDetailById(@PathVariable("id") long id) {
        try {
            return videoService.getVideo(id);
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //克隆视频到专辑
    @RequestMapping(value = "/{id}/clone", method = RequestMethod.POST)
    @PreAuthorize("hasPermission('lesports.qmt.video.videoList', 'ADD')")
    public boolean cloneVideoToAlbum(@PathVariable("id") long id, @RequestParam("albumIds") String albumIds) {
        try {
            return videoService.cloneVideoToAlbum(id, albumIds);
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //克隆视频到专辑的专辑名称
    @RequestMapping(value = "/{id}/cloneAlbumNames", method = RequestMethod.GET)
    public String getCloneVideoAlbumNames(@PathVariable("id") long id) {
        try {
            return videoService.getCloneVideoAlbumNames(id);
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //获取视频的标签信息
    @RequestMapping(value = "/{id}/tags", method = RequestMethod.GET)
    public List<Tag> getVideoTagsByVideoId(@PathVariable("id") long id) {
        try {
            return videoService.getTagsByVideoId(id);
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 新增/编辑视频
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @PreAuthorize("hasPermission('lesports.qmt.video.videoList', 'ADD')")
    public long modifyVideo(@ModelAttribute VideoParam videoParam, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors())
                throw new LeWebApplicationException("invalid params", LeStatus.PARAM_INVALID);

            long result = videoService.save(videoParam);
            if (0 == result)
                throw new LeWebApplicationException("save news failed", LeStatus.SERVER_EXCEPTION);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    // 视频追加码流
//    @RequestMapping(value = "/{id}/addRates", method = RequestMethod.POST)
//    public String addVideoStream(@PathVariable("id") long id,
//                                 @RequestParam("rate") String rate) {
//        return null;
//    }

    // 删除视频
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @PreAuthorize("hasPermission('lesports.qmt.video.videoList', 'DELETE')")
    public void deleteVideo(@RequestParam("ids") String ids) {
        boolean result = videoService.deleteBatch(ids);
        if (false == result)
            throw new LeWebApplicationException("delete videos failed", LeStatus.SERVER_EXCEPTION);
    }

    // 视频批量重转
//    @RequestMapping(value = "/retranscoding", method = RequestMethod.POST)
//    public void reuploadVideoBatch(@Validated ReuploadVideoParam videoParam) {
//        boolean result = videoService.retranscodingBatch(videoParam);
//        if (false == result)
//            throw new LeWebApplicationException("retranscoding videos failed", LeStatus.SERVER_EXCEPTION);
//    }

    // 视频批量修改
    @RequestMapping(value = "/repair", method = RequestMethod.POST)
    @PreAuthorize("hasPermission('lesports.qmt.video.videoList', 'UPDATE')")
    public String modifyVideoBatch(@RequestParam("ids") String ids,
                                   @RequestParam("content_rate") Integer contentRate,
                                   @RequestParam("disable_type") Long disableType,
                                   @RequestParam("disable_area") Long disableArea,
                                   @RequestParam("comment_flag") Boolean commentFlag,
                                   @RequestParam("barrage_flag") Boolean barrageFlag,
                                   @RequestParam("search_flag") Boolean searchFlag) {

        return null;
    }
}
