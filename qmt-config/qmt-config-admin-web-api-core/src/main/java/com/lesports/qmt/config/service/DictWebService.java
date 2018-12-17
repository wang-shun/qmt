package com.lesports.qmt.config.service;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.config.vo.DictEntryVo;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.mvc.QmtWebService;

import java.util.List;
import java.util.Map;

/**
 * Created by denghui on 2016/10/29.
 */
public interface DictWebService extends QmtWebService<DictEntryVo, Long> {

    /**
     * 保存字典
     * @param entity
     * @param caller
     * @return
     */
    Long saveWithId(DictEntryVo entity, CallerParam caller);

    /**
     * 获取子级字典
     * @param parentId
     * @return
     */
    List<DictEntryVo> getChildrenDictsByParentId(long parentId, Long gameFType, CallerParam caller);

    /**
     * 批量获取子级字典
     * @param ids
     * @return
     */
    Map<Long, List<DictEntryVo>> getChildrenDictsByParentIds(String ids, CallerParam caller);

    /**
     * 批量获取字典
     * @param ids
     * @return
     */
    List<DictEntryVo> getDictsByIds(String ids, CallerParam caller);

    /**
     * 根据id获取字典
     * @param id
     * @param caller
     * @return
     */
    DictEntryVo findOne(Long id, CallerParam caller);

    /**
     * 根据条件查询字典
     * @param params
     * @param pageParam
     * @param caller
     * @return
     */
    QmtPage<DictEntryVo> list(Map<String, Object> params, QmtPageParam pageParam, CallerParam caller);
}
