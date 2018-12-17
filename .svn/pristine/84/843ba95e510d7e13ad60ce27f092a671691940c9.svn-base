package com.lesports.qmt.sbc.web.controller;

import com.lesports.id.api.IdType;
import com.lesports.qmt.log.WebLogAnnotation;
import com.lesports.qmt.sbc.model.Album;
import com.lesports.qmt.sbc.param.AlbumParam;
import com.lesports.qmt.sbc.service.AlbumService;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 专辑相关功能控制器
 * Created by zhangxudong@le.com on 2016/10/25.
 */
@RestController
@RequestMapping(value = "/albums")
@WebLogAnnotation(ID_TYPE = IdType.ALBUM)
public class AlbumController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlbumController.class);

    @Resource
    private AlbumService albumService;

    // 获取专辑列表
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<Album> queryAlbumPage(
            @RequestParam(value = "gameFType", required = false, defaultValue = "0") long gameFType,
            @RequestParam(value = "cid", required = false, defaultValue = "0") long cid,
            @RequestParam(value = "id", required = false, defaultValue = "0") long id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "20") int pageSize) {
        Page<Album> result = albumService.getAlbumPage(pageNumber, pageSize, id, name, gameFType, cid);
        return result;
    }

    // 根据ID获取专辑详情
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Album queryAlbumDetailById(@PathVariable("id") long id) {
        try {
            return albumService.getAlbumById(id);
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 批量获取专辑列表
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Album> queryAlbumListByIds(@RequestParam(value = "ids", required = false, defaultValue = "") String ids) {
        try {
            return albumService.getAlbumByIds(ids);
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 新增/编辑专辑
    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasPermission('lesports.qmt.video.albumList', 'ADD')")
    public long modifyAlbum(@Validated AlbumParam albumParam, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors())
                throw new LeWebApplicationException("invalid params", LeStatus.PARAM_INVALID);

            long result = albumService.save(albumParam);
            if (0 == result)
                throw new LeWebApplicationException("save album failed", LeStatus.SERVER_EXCEPTION);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
