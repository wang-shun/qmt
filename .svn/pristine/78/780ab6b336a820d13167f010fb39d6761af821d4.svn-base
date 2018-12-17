import com.google.common.collect.Lists;
import com.lesports.api.common.CallerParam;
import com.lesports.api.common.PageParam;
import com.lesports.qmt.sbc.api.common.NewsType;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import com.lesports.qmt.sbc.api.dto.TNews;
import com.lesports.qmt.sbc.api.service.GetRelatedNewsParam;
import com.lesports.qmt.sbc.client.QmtSbcApis;
import com.lesports.qmt.sbc.client.QmtSbcEpisodeApis;
import com.lesports.utils.CallerUtils;
import org.apache.thrift.TException;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by denghui on 2016/12/15.
 */
public class QmtSbcApisTest {

    @Test
    public void  byId() {
        TNews tNews = QmtSbcApis.getTNewsById(706501019l, CallerUtils.getDefaultCaller());
        System.out.println(tNews);
    }

    @Test
    public void  xxx() {
        long[] ids = {706601019, 700501019, 694401019, 689801019, 687201019, 625101019};
        for (long id : ids) {
            TNews tNews = QmtSbcApis.getTNewsById(id, CallerUtils.getDefaultCaller());
            System.out.println(tNews);
        }
    }

    @Test
    public void  testRelatedIds() {
        List<Long> relatedIds = Lists.newArrayList(3642701000l);
        List<NewsType> newsTypes = Lists.newArrayList(NewsType.IMAGE_ALBUM,NewsType.RICHTEXT,NewsType.VIDEO);
        GetRelatedNewsParam param = new GetRelatedNewsParam();
        param.setRelatedIds(relatedIds);
        param.setTypes(newsTypes);
        param.setStar(1);
        PageParam page = new PageParam();
        page.setPage(1);
        page.setCount(10);
        CallerParam caller = CallerUtils.getDefaultCaller();
        caller.setCallerId(1001);
        List<Long> ids = QmtSbcApis.getNewsIdsByRelatedId(param, page, caller);
        System.out.println(ids);
    }

    @Test
    public void  testRelatedNews() throws TException {
        List<NewsType> newsTypes = Lists.newArrayList(NewsType.IMAGE_ALBUM,NewsType.RICHTEXT,NewsType.VIDEO);
        CallerParam caller = CallerUtils.getDefaultCaller();
        caller.setCallerId(1003);
        PageParam page = new PageParam();
        page.setPage(1);
        page.setCount(3);
        List<TNews> ids = QmtSbcApis.getNewsRelatedWithSomeNews(706601019l,newsTypes, page,caller);
        System.out.println(ids);
    }
}
