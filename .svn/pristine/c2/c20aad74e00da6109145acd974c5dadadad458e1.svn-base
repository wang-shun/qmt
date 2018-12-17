package com.lesports.qmt.config.server.copy;

import com.lesports.api.common.CallerParam;
import com.lesports.api.common.CountryCode;
import com.lesports.api.common.LanguageCode;
import com.lesports.api.common.PubChannel;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.config.api.dto.TCaller;
import com.lesports.qmt.config.model.Caller;
import com.lesports.qmt.config.model.ClientPlatform;
import com.lesports.qmt.config.repository.CallerRepository;
import com.lesports.qmt.config.service.CallerService;
import com.lesports.qmt.config.service.ClientPlatformService;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by denghui on 2016/4/14.
 */
@Component
public class CallerCopy extends AbstractCopy<Caller> {
    @Resource
    private CallerRepository callerRepository;

    @Resource
    private CallerService callerService;

    @Resource
    private ClientPlatformService clientPlatformService;


    @Override
    MongoCrudRepository<Caller, Long> getRepository() {
        return callerRepository;
    }

    @Override
    Class<Caller> getClazz() {
        return Caller.class;
    }

    @Override
    void entityCopy(long id) {
        try {
            CallerParam callerParam = new CallerParam();
            callerParam.setCallerId(1051L);
            callerParam.setCountry(CountryCode.CN);
            callerParam.setLanguage(LanguageCode.ZH_CN);
            callerParam.setPubChannel(PubChannel.LETV);
//            TCaller caller = callerService.getTCallerById(1051);
            for (Caller caller : callerRepository.findAll()) {
                List<Long> client_ids = clientPlatformService.findIdsByQuery(InternalQuery.query(InternalCriteria.where("client_id").is(caller.getSplatId()+"")));
                if(CollectionUtils.isNotEmpty(client_ids)){
                    Long clientPlatform = client_ids.get(0);
                    caller.setSplatId(LeNumberUtils.toLong(clientPlatform));
                    callerService.save(caller);
                }
            }
            ;

//            Album item = getEntity(id);
//            if (null == item) {
//                return;
//            }
//            item.setAllowCountry(getCountryCode());
//            item.setLanguageCode(getLanguageCode());
//            saveEntity(id, item);
        } catch (Exception e) {
            LOG.error("{}", e.getMessage(), e);
        }
    }
}
