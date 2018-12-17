package com.lesports.qmt.sbc;

import com.lesports.qmt.sbc.client.QmtSbcNewsInternalApis;
import com.lesports.qmt.sbc.model.News;
import org.junit.Test;

/**
 * Created by denghui on 2016/11/18.
 */
public class NewsInternalApisTest {

    @Test
    public void getById() {
        News news = QmtSbcNewsInternalApis.getNewsById(100026019l);
        QmtSbcNewsInternalApis.saveNews(news);
        System.out.println(news);
    }

}
