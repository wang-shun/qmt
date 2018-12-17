package com.lesports.qmt.sbd;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import com.lesports.qmt.sbd.model.Player;
import com.lesports.qmt.sbd.utils.QmtSearchApis;
import com.lesports.qmt.sbd.utils.QmtSearchData;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by lufei1 on 2016/12/4.
 */
public class QmtSearchApisTest {

    @Test
    public void testGetMatch() {
        Map map = Maps.newHashMap();
//        map.put("name", "库蒂巴耶夫");
        map.put("caller","1001");
        QmtSearchData qmtSearchData = QmtSearchApis.searchData(map, Player.class);
        List<Player> players = JSON.parseObject(JSON.toJSONString(qmtSearchData.getRows()), new TypeReference<List<Player>>() {
        });

        Assert.assertNotNull(players);
    }
}
