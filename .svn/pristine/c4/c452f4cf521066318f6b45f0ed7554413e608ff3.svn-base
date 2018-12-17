package com.lesports.qmt.config.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.lesports.qmt.config.client.QmtConfigTagInternalApis;
import com.lesports.qmt.config.model.Tag;
import com.lesports.qmt.config.service.TagWebService;
import com.lesports.qmt.config.service.impl.support.AbstractConfigWebService;
import com.lesports.qmt.config.vo.TagVo;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.query.InternalQuery;
import com.lesports.utils.LeStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by denghui on 2016/10/29.
 */
@Service("tagWebService")
public class TagWebServiceImpl extends AbstractConfigWebService implements TagWebService {

    private static final Logger LOG = LoggerFactory.getLogger(TagWebServiceImpl.class);

    private static final Function<Tag, TagVo> VO_FUNCTION = new Function<Tag, TagVo>() {
        @Nullable
        @Override
        public TagVo apply(@Nullable Tag input) {
            return input == null ? null : new TagVo(input);
        }
    };

    @Override
    public Long saveWithId(TagVo entity) {
        if (entity.getId() == null) {
            return doInsert(entity);
        } else {
            return doUpdate(entity);
        }
    }

    private Long doInsert(TagVo entity) {
        entity.setCreator(getOperator());
        long id = QmtConfigTagInternalApis.saveTag(entity.toTag());
        entity.setId(id);
        return id;
    }

    private Long doUpdate(TagVo entity) {
        Tag existsTag = QmtConfigTagInternalApis.getTagById(entity.getId());
        existsTag.setName(entity.getName());
        existsTag.setDesc(entity.getDesc());
        existsTag.setUpdater(getOperator());
        long id = QmtConfigTagInternalApis.saveTag(existsTag, true);
        entity.setId(id);
        return id;
    }

    @Override
    public TagVo findOne(Long id) {
        Tag tag = QmtConfigTagInternalApis.getTagById(id);
        return tag == null ? null : new TagVo(tag);
    }

    @Override
    public boolean delete(Long id) {
        return QmtConfigTagInternalApis.deleteTag(id);
    }

    @Override
    public QmtPage<TagVo> list(Map<String, Object> params, QmtPageParam pageParam) {
        InternalQuery query = new InternalQuery();
        addParamsCriteriaToQuery(query, params, pageParam);

        long total = QmtConfigTagInternalApis.countTagByQuery(query);
        if (total <= 0) {
            return new QmtPage<>(Collections.EMPTY_LIST, pageParam, 0);
        }
        List<Tag> tags = QmtConfigTagInternalApis.getTagsByQuery(query);
        return new QmtPage<>(Lists.transform(tags, VO_FUNCTION), pageParam, total);
    }

    @Override
    public List<TagVo> getTagsByIds(String ids) {
        List<Tag> tags = QmtConfigTagInternalApis.getTagsByIds(LeStringUtils.commaString2LongList(ids));
        return Lists.transform(tags, VO_FUNCTION);
    }
}
