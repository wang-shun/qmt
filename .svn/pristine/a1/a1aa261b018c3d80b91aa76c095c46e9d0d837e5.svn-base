package com.lesports.qmt.config.service.impl;

import com.google.common.collect.Lists;
import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.config.api.dto.TTag;
import com.lesports.qmt.config.converter.TTagConverter;
import com.lesports.qmt.config.model.Tag;
import com.lesports.qmt.config.repository.TagRepository;
import com.lesports.qmt.config.service.TagService;
import com.lesports.qmt.msg.core.ActionType;
import com.lesports.qmt.msg.core.BusinessType;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.core.LeMessageBuilder;
import com.lesports.qmt.msg.producer.SwiftMessageApis;
import com.lesports.qmt.service.support.AbstractQmtService;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * User: ellios
 * Time: 15-7-22 : 上午11:06
 */
@Service("tagService")
public class TagServiceImpl extends AbstractQmtService<Tag,Long> implements TagService {

	@Resource
	private TagRepository tagRepository;
	@Resource
	private TTagConverter tagConverter;

	@Override
	protected QmtOperationType getOpreationType(Tag entity) {
		if (LeNumberUtils.toLong(entity.getId()) <= 0) {
			return QmtOperationType.CREATE;
		}
		return QmtOperationType.UPDATE;
	}

	@Override
	protected boolean doCreate(Tag entity) {
		entity.setId(LeIdApis.nextId(IdType.TAG));
		return tagRepository.save(entity);
	}

	@Override
	protected boolean doAfterCreate(Tag entity) {
		List<BusinessType> businessTypes = Lists.newArrayList();
		if (!indexEntity(entity.getId(), IdType.TAG.toString())) {
			businessTypes.add(BusinessType.SEARCH_INDEX);
		}
		LeMessage message = LeMessageBuilder.create()
				.setEntityId(entity.getId())
				.setIdType(IdType.TAG)
				.setBusinessTypes(ActionType.ADD, businessTypes)
				.build();
		SwiftMessageApis.sendMsgAsync(message);
		return true;
	}

	@Override
	protected boolean doUpdate(Tag entity) {
		return tagRepository.save(entity);
	}

	@Override
	protected boolean doAfterUpdate(Tag entity) {
		List<BusinessType> businessTypes = Lists.newArrayList();
		if (!indexEntity(entity.getId(), IdType.TAG.toString())) {
			businessTypes.add(BusinessType.SEARCH_INDEX);
		}
		LeMessage message = LeMessageBuilder.create()
				.setEntityId(entity.getId())
				.setIdType(IdType.TAG)
				.setBusinessTypes(ActionType.UPDATE, businessTypes)
				.build();
		SwiftMessageApis.sendMsgAsync(message);
		return true;
	}

	@Override
	protected boolean doDelete(Long id) {
		return tagRepository.delete(id);
	}

	@Override
	protected boolean doAfterDelete(Tag entity) {
		List<BusinessType> businessTypes = Lists.newArrayList();
		if (!indexEntity(entity.getId(), IdType.TAG.toString())) {
			businessTypes.add(BusinessType.SEARCH_INDEX);
		}
		LeMessage message = LeMessageBuilder.create()
				.setEntityId(entity.getId())
				.setIdType(IdType.TAG)
				.setBusinessTypes(ActionType.DELETE, businessTypes)
				.build();
		SwiftMessageApis.sendMsgAsync(message);
		return true;
	}

	@Override
	protected Tag doFindExistEntity(Tag entity) {
		return findOne(entity.getId());
	}

	@Override
	protected MongoCrudRepository<Tag, Long> getInnerRepository() {
		return tagRepository;
	}

	@Override
	public TTag getTTagById(long id) {
		Tag tag = findOne(id);
		if (tag == null) {
			LOG.warn("fail to getTTagById, tag no exists. id : {}", id);
			return null;
		}
		TTag dto = tagConverter.toDto(tag);
		if (null == dto) {
			LOG.warn("fail to getTTagById, toTVo fail. id : {}", id);
			return null;
		}
		return dto;
	}

	@Override
	public List<TTag> getTTagsByIds(List<Long> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.EMPTY_LIST;
		}
		List<TTag> returnList = Lists.newArrayListWithExpectedSize(ids.size());
		for(Long id : ids){
			TTag tCaller = getTTagById(id);
			if(tCaller != null){
				returnList.add(tCaller);
			}
		}
		return returnList;
	}

	@Override
	public TTag getTTagByName(String name) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		Tag tag = tagRepository.findOneByQuery(query);
		if (tag == null) {
			LOG.warn("fail to getTTagByName, tag no exists. name : {}, caller : {}", name);
			return null;
		}
		TTag dto = tagConverter.toDto(tag);
		if (null == dto) {
			LOG.warn("fail to getTTagByName, toTVo fail. name : {}, caller : {}", name);
			return null;
		}
		return dto;
	}
}