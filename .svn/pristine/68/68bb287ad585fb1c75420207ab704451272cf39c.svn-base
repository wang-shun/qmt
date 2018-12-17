package service.impl;

import com.google.common.collect.Sets;
import com.lesports.qmt.sbc.api.common.ContentRating;
import com.lesports.qmt.sbc.client.QmtSbcAlbumInternalApis;
import com.lesports.qmt.sbc.client.QmtSbcVideoInternalApis;
import com.lesports.qmt.sbc.converter.AlbumVoConverter;
import com.lesports.qmt.sbc.model.Album;
import com.lesports.qmt.sbc.model.Video;
import com.lesports.qmt.sbc.param.AlbumParam;
import com.lesports.query.InternalCriteria;
import com.lesports.query.InternalQuery;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created by zhangxudong@le.com on 11/8/16.
 */
public class VideoServiceImplTest {

@Test
    public void testSaveVideo() throws Exception {
        Video video = new Video();
//        video.setId(4401053L);
        video.setLeecoVid(1L);
        video.setVmid(1L);
//        video.setName("first test video name");
//        video.setShortName("first test video short name");
        video.setRelatedIds(Sets.newHashSet());
        video.setDesc("first test video desc");
//        video.setGameFType(1L);
        video.setCid(1L);
        video.setContentRating(ContentRating.ALL);
//        video.setManualSet(true);
        video.setIsPay(true);
        video.setPayPlatforms(Sets.newHashSet());
        video.setPublishTime("20160808080800");
        video.setReasonOfExReview("there is nothing to feed too");

        long videoId = QmtSbcVideoInternalApis.saveVideo(video);
        Assert.assertNotEquals(0,videoId);
    }

    @Test
    public void testGetVideo() throws Exception {
        Pageable pageable = new PageRequest(0, 20, new Sort(new Sort.Order(Sort.Direction.DESC, "create_at")));
        InternalQuery query = new InternalQuery().with(pageable);
        query.addCriteria(new InternalCriteria("_id").is(93401053));
        List<Video> result = QmtSbcVideoInternalApis.getVideoByQuery(query);
        Assert.assertNotEquals(null,result);
    }

    @Test
    public void testGetVideo2() throws Exception {
        Video result = QmtSbcVideoInternalApis.getVideoById(93401053L);
        Assert.assertNotEquals(null,result);
    }

    @Test
    public void testGetAlbum() throws Exception {
        Pageable pageable = new PageRequest(0, 20, new Sort(new Sort.Order(Sort.Direction.DESC, "_id")));
        InternalQuery query = new InternalQuery().with(pageable);
//        query.addCriteria(new InternalCriteria("_id").is(57501053));
        List<Album> result = QmtSbcAlbumInternalApis.getAlbumByQuery(query);
        Assert.assertNotEquals(null,result);
    }

    @Test
    public void testSaveAlbum() throws Exception {
        AlbumParam albumParam = new AlbumParam();
        albumParam.setTitle("short title 1");
//        albumParam.setTagIds("1,2,3");
        albumParam.setDesc("this is a desc");
//        albumParam.setGameFType(1L);
        albumParam.setCid(1L);
//        albumParam.setIsBaiduCollected(false);
//        albumParam.setIsSelfProducedProgram(true);
        albumParam.setContentRating(1);
        albumParam.setIsPay(true);
        albumParam.setPayPlatforms("456001");
        albumParam.setReasonOfExReview("know how");
        albumParam.setImages("hahahaha");
        AlbumVoConverter albumVoConverter = new AlbumVoConverter();
        Album album = albumVoConverter.toAlbum(albumParam);
        long result = QmtSbcAlbumInternalApis.saveAlbum(album);
        Assert.assertNotEquals(null,result);
    }
}
