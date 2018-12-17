package com.lesports.qmt.sbc;

import com.lesports.qmt.sbc.client.QmtSbcEpisodeInternalApis;
import com.lesports.qmt.sbc.model.Episode;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by lufei1 on 2016/12/2.
 */
public class EpisodeInternalApiTest {

    @Test
    public void testGetEpisodeById() throws Exception {
        Episode episode = QmtSbcEpisodeInternalApis.getEpisodeById(4601005L);
        Assert.assertNotNull(episode);
    }

    @Test
    public void testGetCompetitionById() throws Exception {
        Episode episode = new Episode();
        episode.setName("节目测试1");
        episode.setStartTime("20160902213000");
        episode.setCid(56001L);
        episode.setCsid(1022310003L);
        long id = QmtSbcEpisodeInternalApis.saveEpisode(episode);
        System.out.println("id: " + id);
    }

    @Test
    public void testCreateEpisodes() throws Exception {
        Episode episode = QmtSbcEpisodeInternalApis.getEpisodeById(8901005L);
        Episode.ChatRoom chatRoom = new Episode.ChatRoom();
        chatRoom.setStartTime("20170102214000");
        chatRoom.setEndTime("20170102223000");
        episode.setChatRoom(chatRoom);
        QmtSbcEpisodeInternalApis.saveEpisode(episode);
    }
}
