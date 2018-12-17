package com.lesports.qmt.sbd.service.support;

import com.google.common.collect.Lists;
import com.lesports.api.common.*;
import com.lesports.qmt.model.support.QmtModel;
import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.mvc.QmtVo;
import com.lesports.qmt.sbd.SbdCompetitionInternalApis;
import com.lesports.qmt.sbd.model.Competition;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import com.lesports.utils.CallerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by lufei on 2016/10/26.
 */
public abstract class AbstractSbdWebService<T extends QmtVo, ID extends Serializable> implements SbdWebService<T, ID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSbdWebService.class);
    private static final List<CountryCode> DEFAULT_ALLOW_COUNTRIES = Arrays.asList(CallerUtils.getDefaultCountry());


    protected Pageable check(Pageable pageable) {
        return pageable == null ? new PageRequest(0, Integer.MAX_VALUE) : pageable;
    }

    /**
     * 是否创建操作
     *
     * @param entity
     * @return
     */
    protected abstract boolean isCreateOp(T entity);


    public List<T> findByParams(Map<String, Object> params, CallerParam caller) {
        return null;
    }

    protected Pattern getNamePattern(String name) {
        Pattern pattern = null;
        try {
            pattern = Pattern.compile("^.*" + name.trim() + ".*$", Pattern.CASE_INSENSITIVE);
        } catch (Exception e) {
            throw new IllegalArgumentException("can not compile pattern from name");
        }
        return pattern;
    }

    @Override
    public QmtPage<T> list(InternalQuery query, QmtPageParam pageParam, CallerParam caller) {
        long total = countByQuery(query, caller);
        List<T> entities;
        if (total <= 0) {
            //count为0,就别继续查了
            entities = Collections.emptyList();
            return new QmtPage<>(entities, pageParam, 0);
        }

        entities = findByQuery(query, caller);
        return new QmtPage<>(entities, pageParam, total);
    }

    @Override
    public T findOneByQuery(InternalQuery query, CallerParam caller) {
        List<T> list = findByQuery(query, caller);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    protected List<LangString> setValueOfLanguage(List<LangString> langStrings, String value, LanguageCode lang) {
        //若LanguageCode为空设置为default
        if (null == lang) {
            LanguageCode[] languageCodes = {LanguageCode.ZH_CN, LanguageCode.ZH_HK, LanguageCode.EN_US, LanguageCode.ZH_TW};
            for (LanguageCode languageCode : languageCodes) {
                langStrings = setValueOfLanguage(langStrings, value, languageCode);
            }
            return langStrings;
        }
        return CallerUtils.setValueOfLanguage(langStrings, value, lang);
    }

    protected List<LangString> setValueOfLanguage(List<LangString> langStrings, String value, LanguageCode lang,
                                                  List<CountryCode> newCountryCodes, List<CountryCode> oldCountryCodes) {
        langStrings = CallerUtils.setValueOfLanguage(langStrings, value, lang);
        if (countriesChanged(newCountryCodes, oldCountryCodes)) {
            // 手动勾选国家后检查地区的语言是否有值
            for (CountryCode countryCode : newCountryCodes) {
                List<LanguageCode> langsOfCountry = CallerUtils.getLanguagesOfCountry(countryCode);
                for (LanguageCode langOfCountry : langsOfCountry) {
                    String valueOfLang = CallerUtils.getValueOfLanguage(langStrings, langOfCountry);
                    if (StringUtils.isEmpty(valueOfLang)) {
                        langStrings = setValueOfLanguage(langStrings, value, langOfCountry);
                    }
                }
            }
        }
        return langStrings;
    }

    protected boolean countriesChanged(List<CountryCode> newCountryCodes, List<CountryCode> oldCountryCodes) {
        if (CollectionUtils.isEmpty(oldCountryCodes)) {
            return true;
        }
        if (newCountryCodes.size() != oldCountryCodes.size()) {
            return true;
        }
        for (CountryCode newCountryCode : newCountryCodes) {
            if (!oldCountryCodes.contains(newCountryCode)) {
                return true;
            }
        }
        return false;
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

    protected InternalQuery addCountryCriteria(InternalQuery query, CallerParam caller) {
        if (query == null || caller == null || caller.getCountry() == null || caller.getCountry() == CountryCode.ALL) {
            return query;
        }
        if (CollectionUtils.isNotEmpty(query.getCriterias())) {
            // check if added
            for (InternalCriteria criteria : query.getCriterias()) {
                if ("allow_countries".equals(criteria.getKey())) {
                    return query;
                }
            }
        }
        return query.addCriteria(InternalCriteria.where("allow_countries").in(Arrays.asList(caller.getCountry())));
    }

    protected InternalQuery addRadioCountryCriteria(InternalQuery query, CallerParam caller) {
        if (query == null || caller == null || caller.getCountry() == null) {
            return query;
        }
        if (CollectionUtils.isNotEmpty(query.getCriterias())) {
            // check if added
            for (InternalCriteria criteria : query.getCriterias()) {
                if ("allow_country".equals(criteria.getKey())) {
                    return query;
                }
            }
        }
        return query.addCriteria(InternalCriteria.where("allow_country").is(caller.getCountry()));
    }

    protected List<CountryCode> getAllowCountriesOfCompetition(Long cid) {
        Competition competition = SbdCompetitionInternalApis.getCompetitionById(cid);
        if (competition == null) {
            throw new IllegalArgumentException("competition not exists, id:" + cid);
        }
        return competition.getAllowCountries();
    }

    protected InternalQuery addRadioCountryAndRadioLangCriteria(InternalQuery query, CallerParam caller) {
        if (caller != null) {
            if (caller.getCountry() != null) {
                if (!checkPropertyhasAdd(query, caller, "allow_country")) {
                    query.addCriteria(InternalCriteria.where("allow_country").is(caller.getCountry()));
                }
            }
            if (caller.getLanguage() != null) {
                if (!checkPropertyhasAdd(query, caller, "language_code")) {
                    query.addCriteria(InternalCriteria.where("language_code").is(caller.getLanguage()));
                }
            }
        }
        return query;
    }

    protected Pageable getValidPageable(QmtPageParam pageParam) {
        PageRequest pageRequest = new PageRequest(pageParam.getPage() - 1,
                pageParam.getCount(), new Sort(Sort.Direction.DESC, "_id"));
        return pageRequest;
    }

    protected void addRadioCountryAndRadioLangProperty(T bean, CallerParam caller) {
        try {
            if (caller.getCountry() != null) {
                if (isValidProperty(bean, "allowCountry")) {
                    setProperty(bean, caller.getCountry(), "setAllowCountry");
                }
            }
            if (caller.getLanguage() != null) {
                if (isValidProperty(bean, "languageCode")) {
                    setProperty(bean, caller.getLanguage(), "setLanguageCode");
                }
            }
        } catch (Exception e) {
            return;
        }
    }

    /**
     * 通过反射判断实体是否存在该属性
     *
     * @param bean
     * @param property
     * @return
     */
    private Boolean isValidProperty(T bean, String property) {
        Boolean flag = false;
        try {
            Object instance = bean.getClass().newInstance();
            Field field = instance.getClass().getDeclaredField(property);
            if (field != null) {
                flag = true;
            }
        } catch (Exception e) {
        }
        return flag;
    }

    /**
     * 通过反射设置属性值
     *
     * @param bean
     * @param value
     * @param methedName
     */
    private void setProperty(T bean, Object value, String methedName) {
        try {
            Method m1 = bean.getClass().getDeclaredMethod(methedName, value.getClass());
            m1.invoke(bean, value);
        } catch (Exception e) {

        }
    }

    private Boolean checkPropertyhasAdd(InternalQuery query, CallerParam caller, String property) {
        if (CollectionUtils.isNotEmpty(query.getCriterias())) {
            for (InternalCriteria criteria : query.getCriterias()) {
                if (property.equals(criteria.getKey())) {
                    return true;
                }
            }
        }
        return false;
    }

    protected List<CountryCode> getValidAllowCountries(List<CountryCode> ofEntity, Long cid) {
        if (CollectionUtils.isEmpty(ofEntity)) {
            if (cid == null) {
                ofEntity = DEFAULT_ALLOW_COUNTRIES;
            } else {
                // 继承
                ofEntity = getAllowCountriesOfCompetition(cid);
            }
            return ofEntity;
        }
        return ofEntity;
    }

    protected <E extends QmtModel<Long>> boolean setEntityId(E entity, long id) {
        if (id <= 0) {
            return false;
        }
        if (entity.getId() == null) {
            entity.setId(id);
        }
        return true;
    }

    protected boolean allowCountriesChanged(List<CountryCode> old, List<CountryCode> cur) {
        if (old.size() != cur.size()) {
            return true;
        }
        for (CountryCode countryCode : old) {
            if (!cur.contains(countryCode)) {
                return true;
            }
        }
        return false;
    }

    protected List<LanguageCode> setOnlineLanguages(List<LanguageCode> onlineLanguages, boolean online, LanguageCode languageCode) {
        if (onlineLanguages == null) {
            onlineLanguages = Lists.newArrayList();
        }
        if (online && !onlineLanguages.contains(languageCode)) {
            onlineLanguages.add(languageCode);
        } else if (!online && onlineLanguages.contains(languageCode)) {
            onlineLanguages.remove(languageCode);
        }
        return onlineLanguages;
    }
}
