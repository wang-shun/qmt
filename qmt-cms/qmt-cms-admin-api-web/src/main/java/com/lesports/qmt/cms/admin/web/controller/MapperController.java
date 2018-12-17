package com.lesports.qmt.cms.admin.web.controller;

import com.lesports.qmt.cms.admin.web.service.MapperWebService;
import com.lesports.qmt.cms.admin.web.vo.ColumnVo;
import com.lesports.qmt.cms.admin.web.vo.MapperVo;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.mvc.support.AbstractQmtController;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * User: ellios
 * Time: 17-1-10 : 下午5:27
 */
@RestController
@RequestMapping(value = "/mappers")
public class MapperController extends AbstractQmtController<MapperVo, Long> {

    @Resource
    private MapperWebService mapperWebService;

    /**
     * 根据id获取栏目页详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Override
    public MapperVo getOneById(@PathVariable Long id) {
        try {
            return pretty(mapperWebService.findOne(id));
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
     * @param type
     * @param type
     * @return
     */
    @RequestMapping(value = "/map.json", method = RequestMethod.GET)
    public MapperVo getOneByType(@RequestParam(value = "type", defaultValue = "pc") String type,
                                 @RequestParam(value = "version", required = false) String version) {
        try {
            return pretty(mapperWebService.findByTypeAndVersion(type, version));
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
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
            boolean result = mapperWebService.delete(id);
            LOG.info("delete columns, id:{}, result:{}", id, result);
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
    public QmtPage<MapperVo> list(@RequestParam Map<String, Object> params, QmtPageParam pageParam) {
        try {
            LOG.info("list mappers, params:{} pageParam : {}", params, pageParam);
            return pretty(mapperWebService.list(params, getValidPageParam(pageParam)));
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
     * @return
     */
    @RequestMapping(value = "/map.json", method = RequestMethod.POST)
    @ResponseBody
    public Long addMapjson(@RequestParam Map<String, Object> res, @RequestParam String type, @RequestParam String version) {
        long result = -1;
        try {
            res.remove("type");
            res.remove("version");
            result = mapperWebService.saveWithId(res, type, version);
            LOG.info("add column with res : {}, type : {}, version : {}, result : {}", res, type, version, result);
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
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
    public Long add(MapperVo vo) {
        long result = -1;
        try {

            result = mapperWebService.saveWithId(vo);
            LOG.info("add column with vo : {}, result : {}", vo, result);
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
    public boolean update(MapperVo vo) {
        return false;
    }
}
