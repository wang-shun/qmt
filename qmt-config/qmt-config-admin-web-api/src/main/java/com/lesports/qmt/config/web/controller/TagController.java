package com.lesports.qmt.config.web.controller;

import com.lesports.id.api.IdType;
import com.lesports.qmt.config.service.TagWebService;
import com.lesports.qmt.config.vo.TagVo;
import com.lesports.qmt.log.WebLogAnnotation;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.mvc.support.AbstractQmtController;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by denghui on 2016/10/29.
 */
@RestController
@RequestMapping(value="/tags")
@WebLogAnnotation(ID_TYPE = IdType.TAG)
public class TagController extends AbstractQmtController<TagVo, Long> {

    private static final Logger LOG = LoggerFactory.getLogger(TagController.class);

    @Resource
    private TagWebService tagWebService;

    /**
     * 保存标签
     * @param tagVo
     * @return
     */
    @RequestMapping(method= RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasPermission('lesports.qmt.confManage.editTag', 'ADD')")
    public Long save(@ModelAttribute TagVo tagVo) {
        try {
            if (StringUtils.isBlank(tagVo.getName())) {
                throw new LeWebApplicationException( "tag name is empty", LeStatus.PARAM_INVALID);
            }
            Long result = tagWebService.saveWithId(tagVo);
            LOG.info("save tag, param:{}, result:{}", tagVo, result);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据id获取标签
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    @Override
    public TagVo getOneById(@PathVariable Long id) {
        try {
            return pretty(tagWebService.findOne(id));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 删除标签
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    @ResponseBody
    @Override
    @PreAuthorize("hasPermission('lesports.qmt.confManage.editTag', 'DELETE')")
    public boolean delete(@PathVariable Long id) {
        try {
            boolean result = tagWebService.delete(id);
            LOG.info("delete tag, id:{}, result:{}", id, result);
            return result;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 批量获取标签
     * @param ids 用逗号分隔的标签ids
     * @return
     */
    @RequestMapping(value="/ids", method= RequestMethod.GET)
    public List<TagVo> getTagsByIds(@RequestParam(value = "ids", required = true) String ids) {
        try {
            return pretty(tagWebService.getTagsByIds(ids));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 查询标签列表
     * @param params
     * @param pageParam
     * @return
     */
    @RequestMapping(method=RequestMethod.GET)
    @Override
    public QmtPage<TagVo> list(@RequestParam Map<String, Object> params, QmtPageParam pageParam) {
        try {
            LOG.info("list tag, params:{}, pageParam: {}", params, pageParam);
            return pretty(tagWebService.list(params, getValidPageParam(pageParam)));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
