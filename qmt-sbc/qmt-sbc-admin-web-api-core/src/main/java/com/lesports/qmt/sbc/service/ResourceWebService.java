package com.lesports.qmt.sbc.service;

import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.mvc.QmtWebService;
import com.lesports.qmt.sbc.model.QmtResourceOnline;
import com.lesports.qmt.sbc.model.ResourceContent;
import com.lesports.qmt.sbc.vo.ResourceVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: ellios
 * Time: 16-10-28 : 下午8:44
 */
public interface ResourceWebService extends QmtWebService<ResourceVo, Long> {
    Page<ResourceVo> search(Map<String, String> params, QmtPageParam pageParam);
    public boolean saveResourceContent(ResourceContent resourceVo,boolean isTop);
    public Page<ResourceContent> findResourceContentByResourceId(Long resourceId,QmtPageParam pageParam);
    public Long saveResource(ResourceVo resourceVo);
    public boolean publishResource(long id);
    public boolean addResourceRelete(Long pid,Set<Long> resourceIds,int type) throws Exception;
    public ResourceContent getResourceContentById(Long resourceId);
    public boolean deleteResourceContent(Long resourceContentId);
    public long countResourceContentByResourceId(Long resourceId);
    public boolean moveResourceContent(Long resourceContentId, Integer moveType);
    public boolean moveResource(Long gId,Long rId, Integer moveType);

    public List<QmtResourceOnline> findResourceOnlineResourceId(Long resourceId);

    List<ResourceVo> getByIds(List<Long> ids);
}
