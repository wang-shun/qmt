package com.lesports.qmt.config.server.copy;

import com.google.common.collect.Lists;
import com.lesports.api.common.CountryCode;
import com.lesports.api.common.LangString;
import com.lesports.api.common.LanguageCode;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.utils.http.RestTemplateFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * trunk.
 *
 * @author pangchuanxiao
 * @since 2016/4/13
 */
public abstract class AbstractCopy<T> {

    protected final static Logger LOG = LoggerFactory.getLogger(AbstractCopy.class);

    public void entitiesIndex() {
        List<Long> ids = getIds();
        final RestTemplate restTemplate = RestTemplateFactory.getTemplate();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (long id : ids) {
            final long idFinal = id;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    MultiValueMap<String, String> param = new LinkedMultiValueMap<>();

                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add("content-type", "application/x-www-form-urlencoded");
                    HttpEntity httpEntity = new HttpEntity(param, httpHeaders);

                    restTemplate.exchange(getIndexTemplate(), HttpMethod.PUT, httpEntity, Object.class, idFinal);
                }
            });
        }
    }

    public void entitiesCopy() throws TException, InterruptedException {
        List<Long> ids = getIds();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (long id : ids) {
            final long idFinal = id;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    entityCopy(idFinal);
                }
            });
        }
    }

    protected T getEntity(long id) throws TException {
        Query query = new Query(Criteria.where("_id").is(id));
        return getRepository().findOneByQuery(query);

    }

    protected void saveEntity(long id, T entity) throws TException {
        boolean result = getRepository().save(entity);
        LOG.info("save {} result {}", id, result);
    }

    protected String getIndexTemplate() {
        return "";
    }

    abstract MongoCrudRepository<T, Long> getRepository();

    abstract Class<T> getClazz();

    List<Long> getIds(){
        List<Long> ids = new ArrayList<>();
        List<Long> partial;
        int page = 0, count = 1000;
        do {
//            Query query = new Query(Criteria.where("_id").gt(730800019));
//            query.skip(page * count);
//            query.limit(count);
//            page++;
//            query.with(new Sort(Sort.Direction.DESC, "_id"));
            Query q = query(where("deleted").is(false));
            q.addCriteria(where("pids").is(106768007));
//            q.addCriteria(where("aid").gt(0));
            q.skip(page * count);
            q.limit(count);
            page++;
            partial = getRepository().findIdByQuery(q);
            ids.addAll(partial);
        } while (CollectionUtils.isNotEmpty(partial));

        return ids;
    }

    abstract void entityCopy(long id);

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
