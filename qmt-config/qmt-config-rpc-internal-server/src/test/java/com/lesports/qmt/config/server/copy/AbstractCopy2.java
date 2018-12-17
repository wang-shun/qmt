package com.lesports.qmt.config.server.copy;

import com.google.common.collect.Lists;
import com.lesports.api.common.CountryCode;
import com.lesports.api.common.LangString;
import com.lesports.api.common.LanguageCode;
import com.lesports.mongo.repository.MongoCrudRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by denghui on 2016/5/17.
 */
public abstract class AbstractCopy2<T> {

    protected final static Logger LOG = LoggerFactory.getLogger(AbstractCopy2.class);

    public void entitiesCopy() throws TException, InterruptedException {
        List<String> ids = getIds();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (String id : ids) {
            final String idFinal = id;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    entityCopy(idFinal);
                }
            });
        }
    }

    protected T getEntity(String id) throws TException {
        Query query = new Query(Criteria.where("_id").is(id));
        return getRepository().findOneByQuery(query);

    }

    protected void saveEntity(String id, T entity) throws TException {
        boolean result = getRepository().save(entity);
        LOG.info("save {} result {}", id, result);
    }

    abstract MongoCrudRepository<T, String> getRepository();

    abstract Class<T> getClazz();

    List<String> getIds(){
        List<String> ids = new ArrayList<>();
        List<String> partial;
        int page = 0, count = 1000;
        do {
            Query query = new Query();
            query.skip(page * count);
            query.limit(count);
            page++;
            query.with(new Sort(Sort.Direction.DESC, "_id"));
            partial = getRepository().findIdByQuery(query);
            ids.addAll(partial);
        } while (CollectionUtils.isNotEmpty(partial));

        return ids;
    }

    abstract void entityCopy(String id);

    protected LanguageCode getLanguageCode() {
        return LanguageCode.ZH_CN;
    }

    protected CountryCode getCountryCode() {
        return CountryCode.CN;
    }


    protected List<LangString> getMultiLang(String value) {
        List<LangString> langStrings = Lists.newArrayList();
        if (null != value) {
            LangString cn = new LangString();
            cn.setLanguage(LanguageCode.ZH_CN);
            cn.setValue(value);
            langStrings.add(cn);
            LangString hk = new LangString();
            hk.setLanguage(LanguageCode.ZH_HK);
//            hk.setValue(HanLP.convertToTraditionalChinese(value));
            langStrings.add(hk);
        }
        return langStrings;
    }

    protected List<CountryCode> getCountryCodes() {
        return Arrays.asList(CountryCode.CN);
    }
}
