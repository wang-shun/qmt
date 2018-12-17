package com.lesports.qmt.sbc.server;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lesports.api.common.CountryCode;
import com.lesports.api.common.LiveStatus;
import com.lesports.qmt.sbc.api.common.ContentRating;
import com.lesports.qmt.sbc.api.common.ShieldType;
import com.lesports.qmt.sbc.api.dto.StreamSourceType;
import com.lesports.qmt.sbc.api.service.TSbcInternalService;
import com.lesports.qmt.sbc.model.*;
import com.lesports.qmt.sbc.repository.VideoRepository;
import com.lesports.qmt.sbc.server.support.AbstractIntegrationTest;
import com.lesports.qmt.sbc.service.LiveService;
import com.lesports.qmt.sbc.service.VideoService;
import com.lesports.query.InternalQuery;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.http.RestTemplateFactory;
import com.lesports.utils.math.LeNumberUtils;
import com.lesports.utils.transcoders.CachedData;
import com.lesports.utils.transcoders.SerializingTranscoder;
import com.lesports.utils.transcoders.Transcoder;
import com.letv.urus.liveroom.api.dto.LiveMainDTO;
import com.letv.urus.liveroom.api.dto.LiveWrapperDTO;
import com.letv.urus.liveroom.api.dto.sports.ChannelIdNameEnameDTO;
import com.letv.urus.liveroom.api.sports.LiveRoomSportsQueryAPI;
import com.letv.urus.liveroom.api.sports.LiveRoomSportsWriterAPI;
import com.letv.urus.liveroom.base.UrusAuth;
import org.apache.commons.io.FileUtils;
import org.apache.thrift.TException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

public class TSbcInternalServiceAdapterTest extends AbstractIntegrationTest {

    @Resource
    private TSbcInternalService.Iface sbcInternalService;
    @Resource
    private LiveRoomSportsQueryAPI liveRoomSportsQueryAPI;
    @Resource
    private LiveRoomSportsWriterAPI liveRoomSportsWriterAPI;
    @Resource
    private VideoRepository videoRepository;
    @Resource
    private VideoService videoService;
    @Resource
    private LiveService liveService;

    private Transcoder transcoder = new SerializingTranscoder();

    private static <T> ByteBuffer serialize(T entity) {
        return ByteBuffer.wrap(TRANSCODER.encode(entity).getFullData());
    }

    private static <T> T unserialize(ByteBuffer bf) {
        if (bf == null) {
            return null;
        }
        return TRANSCODER.decode(new CachedData(bf.array()));
    }

    private static final Transcoder TRANSCODER = new SerializingTranscoder();

    @Test
    public void testGetById() throws Exception {
        ByteBuffer bf = sbcInternalService.getEntityBytesById(1L, serialize(News.class));
        News news = unserialize(bf);
        Assert.assertNull(news);
    }

    @Test
    public void testGetAlbumById() throws Exception {
        ByteBuffer bf = sbcInternalService.getEntityBytesById(4801004L, serialize(Album.class));
        Album album = unserialize(bf);
        Assert.assertNotNull(album);
    }

    @Test
    public void testSaveAlbum2() throws Exception {
        ByteBuffer bf = sbcInternalService.getEntityBytesById(4201004L, serialize(Album.class));
        Album album = unserialize(bf);
//        album.setId(null);
        album.setLeecoAid(10031116L);
        album.setDesc("this is a new description from tony...hahahahah");
        long id = sbcInternalService.saveEntity(serialize(album), serialize(Album.class), false);
        Assert.assertNotEquals(0, id);
    }

    @Test
    public void testSaveVideo2() throws Exception {
        ByteBuffer bf = sbcInternalService.getEntityBytesById(95001053L, serialize(Video.class));
        Video video = unserialize(bf);
        video.setDesc("this is a new description from tony...hahahahah");
        long id = sbcInternalService.saveEntity(serialize(video), serialize(Video.class), false);
        Assert.assertNotEquals(0, id);
    }

    @Test
    public void testUpdateVideo() throws Exception {
//        List<Long> ids = videoRepository.findIdByQuery(new Query());
//        for (long index : ids) {
//            Video video = null;
//            try {
//                video = videoRepository.findOne(index);
//            } catch (Exception e) {
//
//            }
//            if (null == video) {
//                continue;
//            }
//            video.setId(LeIdApis.convertMmsVideoIdToLeSportsVideoId(video.getLeecoVid()));
//            try {
//                videoService.save(video, true);
//                videoService.delete(index);
//
//            } catch (Exception e) {
//                System.out.print("");
//            }
//        }
        Video video= videoRepository.findOne(27371064053L);
//        video.setId(null);
        videoService.save(video);
    }

//    @Test
//    public void testUpdateVideo() throws Exception {
//        boolean tf = sbcInternalService.schedulerUpdateLiveStatus();
//        Assert.assertNotEquals(0, tf);
//    }

    @Test
    public void testSaveAlbum() throws Exception {
        Album album = new Album();
//        album.setLeecoVid(1L);
        album.setLeecoAid(1L);
//        album.setVmid(1L);
        album.setTitle("first test album title");
//        album.setShortTitle("first test album short title");
//        album.setTags("first test album tags");
        album.setDesc("first test album desc");
//        album.setGameFType(1L);
        album.setCid(1L);
        album.setBaiduCollected(true);
        album.setContentRating(ContentRating.ALL);
//        album.setManualSet(true);
        //album.setPay(true);
        album.setPayPlatforms(Sets.newHashSet());
        //album.setPublishTime("20160808080800");
        //album.setMarkCountry(Sets.newHashSet());
        album.setReasonOfExReview("there is nothing to feed");

        long id = sbcInternalService.saveEntity(serialize(album), serialize(Album.class), true);
        Assert.assertNotEquals(0, id);
    }

    @Test
    public void testGetVideoById() throws Exception {
        ByteBuffer bf = sbcInternalService.getEntityBytesById(4001053L, serialize(Video.class));
        Video video = unserialize(bf);
        Assert.assertNull(video);
    }

    @Test
    public void testGetVideoByQuery() throws Exception {
        Pageable pageable = new PageRequest(0, 10, new Sort(new Sort.Order(Sort.Direction.DESC, "create_at")));
        InternalQuery query = new InternalQuery().with(pageable);
        ByteBuffer bf = sbcInternalService.getEntitiesBytesByQuery(serialize(query), serialize(Video.class));
        List<Video> videos = unserialize(bf);
        Assert.assertNull(videos);
    }

    @Test
    public void testSaveVideo() throws Exception {
        Video video = new Video();
//        video.setId(12701053L);
//        video.setAid();
        video.setTitle("this is a test title");
//        video.setShortTitle("this is a test short title");
//        video.setType(VideoContentType.OTHER);
//        Map<String, ImageUrlExt> images = new HashMap<>();
//        images.put("16:9", new ImageUrlExt());
//        video.setImages(images);
//        video.setLeecoVid(1L);
//        video.setLeecoAid(1L);
//        video.setVmid(1L);
//        video.setName("first test video name");
//        video.setShortName("first test video short name");
//        video.setRelatedIds(Sets.newHashSet());
//        video.setDesc("first test video desc");
//        video.setGameFType(1L);
//        video.setCid(1L);
//        video.setContentRating(ContentRating.ALL);
//        video.setManualSet(true);
//        video.setIsPay(true);
//        video.setPayPlatforms(Sets.newHashSet());
//        video.setPublishTime("20160808080800");
//        video.setMarkCountry(Sets.newHashSet());
//        video.setReasonOfExReview("there is nothing to feed too");

        long id = sbcInternalService.saveEntity(serialize(video), serialize(Video.class), true);
        Assert.assertNotEquals(0, id);
    }

    @Test
    public void testVideoJson() throws Exception {
        String videoJson = "{\"channel\":3642601000,\"cid\":65001,\"cloneAids\":[],\"commentFlag\":true,\"createAt\":\"20170209110918\",\"deleted\":false,\"desc\":\"dd\",\"downloadPlatforms\":[\"MOBILE\",\"PAD\",\"TV\",\"MSITE\",\"PC\"],\"drmFlag\":0,\"duration\":0,\"highlightId\":-1,\"id\":27371209053,\"images\":{},\"isClone\":false,\"isOverView\":false,\"isPay\":false,\"languageCode\":\"ZH_CN\",\"leecoMid\":61641663,\"leecoVid\":27371209,\"mainId\":-1,\"matchIds\":[],\"onlineStatus\":\"OFFLINE\",\"payPlatforms\":[],\"playPlatforms\":[\"MOBILE\",\"TV\",\"MSITE\",\"PC\",\"PAD\"],\"publishSetting\":0,\"recommend2Homepage\":false,\"recordId\":-1,\"relatedIds\":[3642601000,65001],\"selfProducedProgramId\":-1,\"shareDesc\":\"dd\",\"shieldAreaType\":0,\"shieldCountries\":[],\"shortTitle\":\"打点\",\"source\":\"dd\",\"starLevel\":3645501000,\"statements\":[\"text1\",\"text2\"],\"supportLicences\":[\"CIBN\"],\"title\":\"英超第5轮 曼城4-1曼联.flv\",\"updateAt\":\"20170209110918\",\"videoType\":\"FEATURE\",\"vrsPlayPlatforms\":[null]}";
        sbcInternalService.saveEntity(serialize(JSONObject.parseObject(videoJson, Video.class)), serialize(Video.class), true);
    }

    @Test
    public void testCloneVideo() throws Exception {
        sbcInternalService.cloneVideoToAlbum(95001053L, Arrays.asList(2101053L));
    }

    @Test
    public void testGetAlbumByQuery() throws Exception {
        Pageable pageable = new PageRequest(0, 50, new Sort(new Sort.Order(Sort.Direction.DESC, "create_at")));
        InternalQuery query = new InternalQuery().with(pageable);
        ByteBuffer bf = sbcInternalService.getEntitiesBytesByQuery(serialize(query), serialize(Album.class));
        List<Album> albumList = unserialize(bf);
        Assert.assertNull(albumList);
    }

    @Test
    public void testHessian() throws Exception {
        UrusAuth auth = new UrusAuth();
        auth.setUrusAppId("id");
        auth.setUrusSecret("secret");
        auth.setRequestId("123");
        auth.setUrusUserId("dabingge");
        List<ChannelIdNameEnameDTO> channelIdNameEnameDTOs = liveRoomSportsQueryAPI.getIdNameEnameOfCibnChannel(auth);
        System.out.println(channelIdNameEnameDTOs);
    }

    @Test
    public void testHessianSave() throws Exception {
        UrusAuth auth = new UrusAuth();
        auth.setUrusAppId("id");
        auth.setUrusSecret("secret");
        auth.setRequestId("123");
        auth.setUrusUserId("dabingge");
        LiveWrapperDTO liveWrapperDTO = new LiveWrapperDTO();
        liveWrapperDTO.setLiveBranchDTOs(null);
        liveWrapperDTO.setLiveMainDTO(null);
        liveWrapperDTO.setLiveStatusDTO(null);
        long liveId = 0;
        try {
            liveId = liveRoomSportsWriterAPI.save(auth, liveWrapperDTO, "222", "127.0.0.1");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println(liveId);
    }

    @Test
    public void testHessianGet() throws Exception {
        UrusAuth auth = new UrusAuth();
        auth.setUrusAppId("id");
        auth.setUrusSecret("secret");
        auth.setRequestId("123");
        auth.setUrusUserId("dabingge");
        long liveId = 0;
        LiveWrapperDTO liveWrapperDTO = null;
        try {
            liveWrapperDTO = liveRoomSportsQueryAPI.getLiveRoomWithBranchByLiveId(auth, 4578625674708029089L);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println(liveWrapperDTO);
    }

    @Test
    public void testGetChannels() {
        UrusAuth auth = new UrusAuth();
        auth.setUrusAppId("id");
        auth.setUrusSecret("secret");
        auth.setRequestId("123");
        auth.setUrusUserId("dabingge");

        try {
            List<ChannelIdNameEnameDTO> channels = liveRoomSportsQueryAPI.getIdNameEnameOfNormalChannel(auth);
            channels.removeIf(channelIdNameEnameDTO -> channelIdNameEnameDTO.getChannelId() != 215);
            System.out.println(channels);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    @Test
    public void testHessianDelete() throws Exception {
        UrusAuth auth = new UrusAuth();
        auth.setUrusAppId("id");
        auth.setUrusSecret("secret");
        auth.setRequestId("123");
        auth.setUrusUserId("dabingge");
        LiveWrapperDTO liveWrapperDTO = new LiveWrapperDTO();
        LiveMainDTO liveMainDTO = new LiveMainDTO();
        liveMainDTO.setLiveid(4578625674708029089L);
        liveWrapperDTO.setLiveMainDTO(liveMainDTO);
        try {
//            liveRoomSportsWriterAPI.deleteLive(auth,liveWrapperDTO,"232323","10.154.157.33");
            List<Long> liveIds = new ArrayList<Long>();
            liveIds.add(4578625674708029089L);
            liveRoomSportsWriterAPI.deleteLives(auth, liveIds, "232323", "10.154.157.33");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println(liveWrapperDTO);
    }

    @Test
    public void testDoCreate() throws Exception {
        Live live = new Live();
        live.setEid(1L);
        live.setStartTime(LeDateUtils.formatYYYYMMDDHHMMSS(new Date()));
        live.setEndTime(LeDateUtils.formatYYYYMMDDHHMMSS(new Date()));
        live.setStreamSourceType(StreamSourceType.LETV);
        live.setThirdLivePageUrl("http://douniwan.org");
        live.setChannelId(89L);
//        live.setChannelId(1L);
        live.setCibnChannelId(1L);
        live.setSpecialLive(1);
        live.setIsDrm(true);
        live.setCommentaryLanguage("詹俊解说/粤语解说");
        live.setWeight(1);
//        live.setCharge(0);
//        live.setScreeningId("1");
        //live.setPayBeginTime(LeDateUtils.formatYYYYMMDDHHMMSS(new Date()));
        live.setPayPlatforms(null);
        live.setIsManual(1);
        live.setCopyrightId(1L);
        live.setPlayPlatforms(null);
        live.setShieldType(ShieldType.ALLOW);
        live.setShieldRegion(Lists.newArrayList());
        live.setBelongArea(CountryCode.ALL);
        live.setStatus(LiveStatus.LIVE_END);
//        live.setCoverImage(Maps.newHashMap());
        live.setRelatedIds(Lists.newArrayList());
        live.setDeleted(true);
        live.setShieldRowId(1L);
        long id = sbcInternalService.saveEntity(serialize(live), serialize(Live.class), true);
        return;
    }

    @Test
    public void testSaveLive() throws TException {

//        String liveString = "{\"eid\":8101005, \"channelId\":361,\"startTime\":\"20170827014518\",\"endTime\":\"20170827014521\",\"commentaryLanguage\":\"哈哈dddsssa哈\",\"copyrightId\":1501059,\"coverImage\":{},\"playPlatforms\":[7601060, 7401060, 201060],\"payPlatforms\":[201060],\"relatedIds\":[],\"shieldRegion\":[],\"streamSourceType\":\"LETV\"}";
//        Live live = JSONObject.parseObject(liveString, Live.class);
        Live live = liveService.findOne(101020170313103349L);
//        live.setChannelId(215L);
//        live.setStartTime("20190101000000");
//        live.setEndTime("20190101000010");
//        live.setOpStatus(LiveStatus.LIVE_NOT_START);
        long id = sbcInternalService.saveEntity(serialize(live), serialize(Live.class), false);
        System.out.print(id);
    }

    @Test
    public void testSavePayLive() throws TException {
        String liveString = "{\"channelId\":96,\"commentaryLanguage\":\"阿萨德\",\"copyrightId\":201059,\"coverImage\":{\"index\":0,\"path\":\"/image2/20170215/vkmfjmxod/0b71a381-0d96-4c10-8425-545e3ef555ba.jpg\",\"setIndex\":true,\"setPath\":true,\"setUrl\":true,\"url\":\"http://i1.letvimg.com/lc04_qmt/201702/15/18/34/vkmfjmxod.jpg\"},\"createAt\":\"20170215183440\",\"deleted\":false,\"eid\":1192701005,\"endTime\":\"20170209183323\",\"id\":101020170215183440,\"isPay\":true,\"payBeginTime\":\"\",\"payPlatforms\":[301060,2401060,4501060,701060,2801060,4901060,3601060,5701060,3201060,1101060,5301060,4001060,401060,4601060,4201060,2301060,801060,5001060,3101060,2701060,3501060,1201060,5401060,5801060,3901060,501060,2601060,4701060,101060,2201060,4301060,3001060,901060,5101060,3401060,5501060,3801060,2501060,201060,4401060,2901060,1001060,5201060,601060,4801060,5601060,3301060,4101060,3701060],\"playPlatforms\":[301060,2401060,4501060,6601060,701060,2801060,7001060,3601060,1501060,3201060,1101060,4001060,6101060,1901060,401060,4601060,4201060,2301060,6501060,801060,2701060,6901060,3501060,1201060,6201060,2001060,1601060,501060,2601060,6801060,101060,2201060,4301060,6401060,3001060,7201060,1301060,7601060,1701060,5901060,6701060,6301060,201060,4401060,2901060,7101060,1001060,601060,1401060,3301060,7501060,4101060,6001060,1801060],\"relatedIds\":[],\"shieldRegion\":[],\"shieldRowId\":526,\"shieldType\":\"ALLOW_ALL\",\"startTime\":\"20170208183323\",\"status\":\"LIVE_END\",\"streamSourceType\":\"LETV\",\"thirdLivePageUrl\":\"\",\"updateAt\":\"20170215230855\"}";
        Live live = JSONObject.parseObject(liveString, Live.class);
//        Live live = liveService.findOne(101020170214170422L);
//        live.setStartTime("20170201000000");
//        live.setEndTime("20170202000000");
//        live.setChannelId(96L);
//        live.setPayPlatforms(Sets.newHashSet(101060L,2201060L,2301060L,201060L,301060L,2401060L,2501060L,401060L,501060L,601060L,701060L,801060L,2601060L,2701060L,2801060L,2901060L,3001060L,3101060L,3201060L,3301060L,3401060L,3501060L,3601060L,3701060L,3801060L,3901060L,4001060L,4101060L,4201060L,4301060L,901060L,4401060L,4501060L,4601060L,1001060L,1101060L,1201060L,4701060L,4801060L,4901060L,5001060L,5101060L,5201060L,5301060L,5401060L,5501060L,5601060L,5701060L,5801060L));
        long id = sbcInternalService.saveEntity(serialize(live), serialize(Live.class), false);
        System.out.print(id);
    }

    @Test
    public void testSaveVideoMedium() throws TException {
        String videoMediumString = "{\"createAt\":\"20161226154228\",\"formats\":[{\"codeRate\":58,\"status\":\"DISPATCH_SUCCESS\"},{\"codeRate\":21,\"status\":\"TRANSCODING\"},{\"codeRate\":13,\"status\":\"TRANSCODING\"},{\"codeRate\":22,\"status\":\"TRANSCODING\"},{\"codeRate\":51,\"status\":\"TRANSCODING\"}],\"id\":20401063,\"updateAt\":\"20161226154228\",\"videoId\":24201053}";
        VideoMedium videoMedium = JSONObject.parseObject(videoMediumString, VideoMedium.class);
        long id = sbcInternalService.saveEntity(serialize(videoMedium), serialize(VideoMedium.class), false);
        System.out.print(id);
    }

    @Test
    public void testAlbumString() throws TException {
        String albumString = "{\"baiduCollected\":false,\"channel\":3642701000,\"cid\":65001,\"contentRating\":\"ALL\",\"deleted\":false,\"desc\":\"的发生\",\"id\":1601064,\"images\":{},\"isPay\":false,\"matchIds\":[],\"pay\":false,\"payPlatforms\":[],\"relatedIds\":[65001,3642701000],\"selfProducedProgram\":false,\"title\":\"123测试34341kkkkkktujk\"}";
        Album album = JSONObject.parseObject(albumString, Album.class);
        sbcInternalService.saveEntity(serialize(album), serialize(Album.class), false);
    }

    @Test
    public void testGetMMSDictId() throws IOException {
        Map<String, Long> nameMapping = new HashMap<>();
        fillMap(getDictList(3), nameMapping);
        fillMap(getDictList(18), nameMapping);
        fillMap(getDictList(59), nameMapping);
        fillMap(getDictList(85), nameMapping);
        fillMap(getDictList(83), nameMapping);
        fillMap(getDictList(5), nameMapping);
        List<String> lines = FileUtils.readLines(new File("e:/dict_name.txt"));
        for (String line : lines) {
            Long id = nameMapping.get(line);
            if (null != id) {
                FileUtils.write(new File("e:/dict_id.txt"), id + "\r\n", true);
            } else {
                FileUtils.write(new File("e:/dict_id.txt"), "  \r\n", true);
            }
        }
    }

    private void fillMap(List<Map> res, Map<String, Long> nameMapping) {
        for (Map ll : res) {
            nameMapping.put((String) ll.get("value"), LeNumberUtils.toLong((Integer) ll.get("id")));
        }
    }

    private List<Map> getDictList(int id) {
        RestTemplate restTemplate = RestTemplateFactory.getTemplate();
        Map result = restTemplate.getForObject("http://i.api.le.com/mms/inner/dicts/" + id, Map.class);
        return (List) result.get("data");
    }
}