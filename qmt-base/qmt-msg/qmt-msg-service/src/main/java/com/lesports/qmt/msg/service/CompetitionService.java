package com.lesports.qmt.msg.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * User: ellios
 * Time: 15-11-10 : 下午2:55
 */
@Service
public class CompetitionService {
    private static final Logger LOG = LoggerFactory.getLogger(CompetitionService.class);

    public boolean changeSyncToCloudOfEpisodesWithCid(long cid) {
        return false;
        /*boolean result = SopsInternalApis.changeSyncToCloudOfEpisodesWithCid(cid);
        if(!result){
            LOG.info("fail to changeSyncToCloudOfEpisodesWithCid. cid : {}", cid);
        }else{
            LOG.info("success to changeSyncToCloudOfEpisodesWithCid. cid : {}, ", cid);
        }
        return result;*/
    }
}
