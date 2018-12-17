package com.lesports.qmt.sbc.thrift;

import com.google.common.collect.Lists;
import com.lesports.AbstractIntegrationTest;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.service.GetRelatedNewsParam;
import com.lesports.qmt.sbc.api.service.TSbcEpisodeService;
import com.lesports.qmt.sbc.api.service.TSbcService;
import com.lesports.utils.CallerUtils;
import org.apache.thrift.TException;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by denghui on 2016/12/21.
 */
public class TSbcServiceAdapterTest extends AbstractIntegrationTest {

    @Resource
    private TSbcService.Iface sbcService;

	@Resource
	private TSbcEpisodeService.Iface sbcEpisodeService;

    @Test
    public void  testRelatedIds() throws TException {
        List<Long> relatedIds = Lists.newArrayList(3642701000l);
        List<NewsType> newsTypes = Lists.newArrayList(NewsType.IMAGE_ALBUM,NewsType.RICHTEXT,NewsType.VIDEO);
        GetRelatedNewsParam param = new GetRelatedNewsParam();
        param.setRelatedIds(relatedIds);
        param.setTypes(newsTypes);
        PageParam page = new PageParam();
        page.setPage(0);
        page.setCount(10);
        CallerParam caller = CallerUtils.getDefaultCaller();
        caller.setCallerId(1001);
        List<Long> ids = sbcService.getNewsIdsByRelatedId(param, page, caller);
        System.out.println(ids);
    }

    @Test
    public void  testRelatedNews() throws TException {
        List<NewsType> newsTypes = Lists.newArrayList(NewsType.IMAGE_ALBUM,NewsType.RICHTEXT,NewsType.VIDEO);
        CallerParam caller = CallerUtils.getDefaultCaller();
        caller.setCallerId(1003);
        List<Long> ids = sbcService.getNewsIdsRelatedWithSomeNews(1006782019,newsTypes, null,caller);
        System.out.println(ids);
    }

    @Test
    public void  testGetTComboEpisodeById() throws TException {
        CallerParam caller = CallerUtils.getDefaultCaller();
        caller.setCallerId(1003);
		TComboEpisode comboEpisode = sbcEpisodeService.getTComboEpisodeById(1029035005l, caller);
		System.out.println(comboEpisode.getGameFType());
    }
}
