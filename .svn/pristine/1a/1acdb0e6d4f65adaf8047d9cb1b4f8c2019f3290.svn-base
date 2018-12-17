package com.lesports.qmt.sbc.service.impl;

import com.lesports.AbstractIntegrationTest;
import com.lesports.qmt.sbc.model.Episode;
import com.lesports.qmt.sbc.service.EpisodeService;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by lufei1 on 2016/11/30.
 */
public class EpisodeServiceImplTest extends AbstractIntegrationTest {

    @Resource
    private EpisodeService episodeService;

    @Test
    public void testDoCreate() {
        Episode episode = episodeService.findOne(8701005L);
        episodeService.save(episode);
    }


}
