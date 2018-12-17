package com.lesports.qmt.config.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.LangString;
import com.lesports.api.common.LanguageCode;
import com.lesports.qmt.config.client.QmtConfigDictInternalApis;
import com.lesports.qmt.config.converter.DictVoConverter;
import com.lesports.qmt.config.model.DictEntry;
import com.lesports.qmt.config.service.DictWebService;
import com.lesports.qmt.config.service.impl.support.AbstractConfigWebService;
import com.lesports.qmt.config.vo.DictEntryVo;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.query.InternalQuery;
import com.lesports.utils.CallerUtils;
import com.lesports.utils.LeStringUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by denghui on 2016/10/29.
 */
@Service("dictWebService")
public class DictWebServiceImpl extends AbstractConfigWebService implements DictWebService {

    private static final Logger LOG = LoggerFactory.getLogger(DictWebServiceImpl.class);

    private static Function<DictEntry, DictEntryVo> getVoFunction(final CallerParam caller) {
        return new Function<DictEntry, DictEntryVo>() {
            @Nullable
            @Override
            public DictEntryVo apply(@Nullable DictEntry input) {
                return input == null ? null : new DictEntryVo(input, caller);
            }
        };
    }

    @Resource
    private DictVoConverter dictVoConverter;

    @Override
    public List<DictEntryVo> getChildrenDictsByParentId(long parentId, Long gameFType, CallerParam caller) {
        List<DictEntry> dictEntries = QmtConfigDictInternalApis.getDictsByParentId(parentId, gameFType);
        if (gameFType != null) {
            dictEntries = sortRound(dictEntries);
        }
        return Lists.transform(dictEntries, getVoFunction(caller));
    }

    private List<DictEntry> sortRound(List<DictEntry> dictEntries) {
        final String regex = "第(\\d{1,2})(轮|周)";
        List<DictEntry> dictEntries2 = Lists.newArrayList();
        List<DictEntry> dictEntries3 = Lists.newArrayList();
        for (DictEntry dictEntry : dictEntries) {
            Matcher matcher = Pattern.compile(regex).matcher(dictEntry.getName());
            if (matcher.find()) {
                dictEntries2.add(dictEntry);
            } else {
                dictEntries3.add(dictEntry);
            }
        }
        Collections.sort(dictEntries2, (o1, o2) -> {
            Matcher matcher1 = Pattern.compile(regex).matcher(o1.getName());
            Matcher matcher2 = Pattern.compile(regex).matcher(o2.getName());
            if (matcher1.find() && matcher2.find()) {
                int num1 = Integer.valueOf(matcher1.group(1));
                int num2 = Integer.valueOf(matcher2.group(1));
                return num1 - num2;
            }
            return 0;
        });
        dictEntries2.addAll(dictEntries3);
        return dictEntries2;
    }

    @Override
    public Map<Long, List<DictEntryVo>> getChildrenDictsByParentIds(String ids, CallerParam caller) {
        Map<Long, List<DictEntryVo>> result = Maps.newHashMap();
        for (Long id : LeStringUtils.commaString2LongList(ids)) {
            List<DictEntryVo> vos = getChildrenDictsByParentId(id, null,  caller);
            if (CollectionUtils.isNotEmpty(vos)) {
                result.put(id, vos);
            }
        }
        return result;
    }

    @Override
    public List<DictEntryVo> getDictsByIds(String ids, CallerParam caller) {
        List<DictEntry> dictEntries = QmtConfigDictInternalApis.getDictsByIds(LeStringUtils.commaString2LongList(ids));
        return Lists.transform(dictEntries, getVoFunction(caller));
    }

    @Override
    public Long saveWithId(DictEntryVo entity) {
        return saveWithId(entity, CallerUtils.getDefaultCaller());
    }

    @Override
    public Long saveWithId(DictEntryVo entity, CallerParam caller) {
        if (entity.getId() == null) {
            return doInsert(entity, caller);
        } else {
            return doUpdate(entity, caller);
        }
    }

    private Long doUpdate(DictEntryVo entity, CallerParam caller) {
        DictEntry existsDict = QmtConfigDictInternalApis.getDictById(entity.getId());
        dictVoConverter.copyEditableProperties(existsDict, entity);
        // set value into lang string list
        existsDict.setMultiLangNames(CallerUtils.setValueOfLanguage(existsDict.getMultiLangNames(),existsDict.getName(),caller.getLanguage()));
        // set default value
        existsDict.setName(getDefaultValueOfMultiLang(existsDict.getMultiLangNames()));
        existsDict.setUpdater(getOperator());
        return QmtConfigDictInternalApis.saveDict(existsDict, true);
    }

    private Long doInsert(DictEntryVo entity, CallerParam caller) {
        entity.setCreator(getOperator());
        if (LeNumberUtils.toLong(entity.getParentId()) == 0) {
            //根字典
            entity.setParentId(0l);
            entity.setTopParentId(0l);
        } else {
            DictEntry parentDict = QmtConfigDictInternalApis.getDictById(entity.getParentId());
            if (parentDict == null) {
                LOG.error("parent dict is null, id: {}", entity.getId());
                return -1l;
            }
            entity.setTopParentId(LeNumberUtils.toLong(parentDict.getTopParentId()) == 0 ? parentDict.getId() : parentDict.getTopParentId());
        }

        DictEntry old = null;
        if (entity.getId() != null) {
            old = QmtConfigDictInternalApis.getDictById(entity.getId());
        }
        if (old != null) {
            entity.setMultiLangNames(old.getMultiLangNames());
        }

        // set value into lang string list
        entity.setMultiLangNames(CallerUtils.setValueOfLanguage(entity.getMultiLangNames(),entity.getName(),caller.getLanguage()));
        // set default value
        entity.setName(getDefaultValueOfMultiLang(entity.getMultiLangNames()));
        long id = QmtConfigDictInternalApis.saveDict(entity.toDict());
        entity.setId(id);
        return id;
    }

    @Override
    public DictEntryVo findOne(Long id) {
        return findOne(id, CallerUtils.getDefaultCaller());
    }

    @Override
    public DictEntryVo findOne(Long id, CallerParam caller) {
        DictEntry dictEntry = QmtConfigDictInternalApis.getDictById(id);
        return dictEntry == null ? null : new DictEntryVo(dictEntry, caller);
    }

    @Override
    public boolean delete(Long id) {
        return QmtConfigDictInternalApis.deleteDict(id);
    }

    @Override
    public QmtPage<DictEntryVo> list(Map<String, Object> params, QmtPageParam pageParam) {
        return list(params, pageParam, CallerUtils.getDefaultCaller());
    }

    @Override
    public QmtPage<DictEntryVo> list(Map<String, Object> params, QmtPageParam pageParam, CallerParam caller) {
        InternalQuery query = new InternalQuery();
        addDictCriteriaToQuery(query, params, pageParam);

        long total = QmtConfigDictInternalApis.countDictByQuery(query);
        if (total <= 0) {
            return new QmtPage<>(Collections.EMPTY_LIST, pageParam, 0);
        }
        List<DictEntry> dictEntries = QmtConfigDictInternalApis.getDictsByQuery(query);
        return new QmtPage<>(Lists.transform(dictEntries, getVoFunction(caller)), pageParam, total);
    }

    protected String getDefaultValueOfMultiLang(List<LangString> langStrings) {
        if (CollectionUtils.isEmpty(langStrings)) {
            return StringUtils.EMPTY;
        }
        String cnValue = CallerUtils.getValueOfLanguage(langStrings, LanguageCode.ZH_CN);
        String hkValue = CallerUtils.getValueOfLanguage(langStrings, LanguageCode.ZH_HK);
        String usValue = CallerUtils.getValueOfLanguage(langStrings, LanguageCode.EN_US);
        if (StringUtils.isNotEmpty(cnValue)) {
            return cnValue;
        } else if (StringUtils.isNotEmpty(hkValue)) {
            return hkValue;
        }
        return usValue;
    }

}
