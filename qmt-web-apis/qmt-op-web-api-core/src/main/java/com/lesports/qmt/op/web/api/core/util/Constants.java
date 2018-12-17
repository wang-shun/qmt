package com.lesports.qmt.op.web.api.core.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.utils.security.LeSignature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {

    //视频图片 PC用 16:9的
    public static final String VIDEO_NEWS_IMAGE_169 = "400*225";
    //视频图片 需要确认大图 16:9的
    public static final String VIDEO_NEWS_IMAGE_BIG_169 = "960*540";
    //直播图片 PC用 16:9的
    public static final String LIVE_VIDEO_IMAGE_169 = "pic3_400_225";
    //直播图片 16:9的
    public static final String LIVE_VIDEO_IMAGE_BIG_169 = "pic2_960_540";
    //视频新闻的图片4:3
    public static final String VIDEO_NEWS_IMAGE_43 = "400*300";
    //回放
    public static final Integer VIDEO_TYPE_RECORD = 0;
    //集锦
    public static final Integer VIDEO_TYPE_HIGHLIGHTS = 1;
    //奥运tagId
    public static final Long OLY_OLY_TAG_ID = 100119008L;
    //奥运中国军团tagId
    public static final Long OLY_CHINA_TAG_ID = 100316008L;
    //奥运赛事id
    public static final Long OLY_COMPETITION_ID = 100507001L;
    //中国字典id
    public static final Long OLY_CHINA_COUNTY_ID = 114588000L;

    public static final BiMap<String, Long> URL_MAP = HashBiMap.create();
    public static final BiMap<String, String> ROAD_MAP = HashBiMap.create();
    public static final HashMap<String,Long> TEXT_LIVE_ID_MAP = Maps.newHashMap();
    public static final HashMap<String,Integer> EVENT_ORDER_MAP = Maps.newHashMap();
    public static final HashMap<String,Integer> FINAL_MAP = Maps.newHashMap();
    public static final HashMap<String,Integer> SEMI_FINAL_MAP = Maps.newHashMap();

    public static Map<Long, String> SITE_MAP = Maps.newHashMap();

    public static final String BAIDU_SITE_MAP_URL = "http://op.api.sports.letv.com/sms/op/v1/competitions/baidu/";

    public static final String CMS_DATA_URL = "http://www.lesports.com/cmsdata/block/";


    public static final String VIDEO_SIZE = "200*150";

    public static List<Long> BAIDU_COMPETITION_LIST = Lists.newArrayList(20001L, 29001L, 32001L, 35001L, 192001L, 100234001L, 47001L);

    public static List<Long> BAIDU_TEAM_LIST = Lists.newArrayList(547006L, 517006L, 526006L, 541006L, 544006L, 700006L, 736006L, 658006L, 646006L,
            598006L, 607006L, 601006L, 796006L, 1032006L, 1014006L, 1002006L, 1008006L, 1041006L);


    static {
        //英超
        URL_MAP.put("yingchao", 20001L);
        //意甲
        URL_MAP.put("yijia", 29001L);
        //法甲
        URL_MAP.put("fajia", 35001L);
        //英冠
        URL_MAP.put("yinggua", 100037001L);
        //美国大联盟
        URL_MAP.put("mgdlm", 62001L);
        //女超
        URL_MAP.put("nvchao", 65001L);
        //K联赛
        URL_MAP.put("klian", 70001L);
        //J联赛
        URL_MAP.put("jlian", 67001L);
        //欧篮
        URL_MAP.put("oulian", 180001L);
        //解放者杯
        URL_MAP.put("jfzb", 63001L);
        //NFL
        URL_MAP.put("nfl", 100050001L);
        //中超
        URL_MAP.put("zhongchao", 47001L);
        //欧冠
        URL_MAP.put("ouguan", 192001L);


        //英超
        SITE_MAP.put(20001L, "http://sports.letv.com/socceri/epl/");
        //意甲
        SITE_MAP.put(29001L, "http://sports.letv.com/socceri/seriea/");
        //法甲
        SITE_MAP.put(35001L, "http://sports.letv.com/socceri/ligue1/");
        //德甲
        SITE_MAP.put(32001L, "http://sports.letv.com/socceri/bundesliga/");
        //亚冠
        SITE_MAP.put(100234001L, "http://sports.letv.com/izt/afccl2015/index.html");
        //英冠
        SITE_MAP.put(100037001L, "http://sports.letv.com/izt/15-16englandchampionships/index.html");
        //美国大联盟
        SITE_MAP.put(62001L, "http://sports.letv.com/izt/mlssoccer/index.html");
        //女超
        SITE_MAP.put(65001L, "http://sports.letv.com/izt/cwsl/index.html");
        //K联赛
        SITE_MAP.put(70001L, "http://sports.letv.com/izt/kleague/index.html");
        //J联赛
        SITE_MAP.put(67001L, "http://sports.letv.com/izt/jleague/index.html");
        //欧篮
        SITE_MAP.put(180001L, "http://sports.letv.com/basketball/euroleague/");
        //解放者杯
        SITE_MAP.put(63001L, "http://sports.letv.com/izt/2015copalibertadores/index.html");
        //NFL
        SITE_MAP.put(100050001L, "http://sports.letv.com/nfl/");
        //中超
        SITE_MAP.put(47001L, "http://sports.letv.com/soccerc/csl/");
        //欧冠
        SITE_MAP.put(192001L, "http://sports.letv.com/socceri/championsleague/");

        ROAD_MAP.put("15","13,14");
        ROAD_MAP.put("13","9,10");
        ROAD_MAP.put("14","11,12");
        ROAD_MAP.put("9","1,2");
        ROAD_MAP.put("10","3,4");
        ROAD_MAP.put("11","5,6");
        ROAD_MAP.put("12","7,8");

        TEXT_LIVE_ID_MAP.put("20160806",4004032L);
        TEXT_LIVE_ID_MAP.put("20160807",3906032L);
        TEXT_LIVE_ID_MAP.put("20160808",4007032L);
        TEXT_LIVE_ID_MAP.put("20160809",4008032L);
        TEXT_LIVE_ID_MAP.put("20160810",4106032L);
        TEXT_LIVE_ID_MAP.put("20160811",3909032L);
        TEXT_LIVE_ID_MAP.put("20160812",4107032L);
        TEXT_LIVE_ID_MAP.put("20160813",4108032L);
        TEXT_LIVE_ID_MAP.put("20160814",4109032L);
        TEXT_LIVE_ID_MAP.put("20160815",4009032L);
        TEXT_LIVE_ID_MAP.put("20160816",3910032L);
        TEXT_LIVE_ID_MAP.put("20160817",3911032L);
        TEXT_LIVE_ID_MAP.put("20160818",4010032L);
        TEXT_LIVE_ID_MAP.put("20160819",3912032L);
        TEXT_LIVE_ID_MAP.put("20160820",4011032L);
        TEXT_LIVE_ID_MAP.put("20160821",4012032L);
        TEXT_LIVE_ID_MAP.put("20160822",4013032L);


        EVENT_ORDER_MAP.put("114768000",1);
        EVENT_ORDER_MAP.put("114944000",2);
        EVENT_ORDER_MAP.put("114883000",3);
        EVENT_ORDER_MAP.put("114872000",4);
        EVENT_ORDER_MAP.put("114941000",5);
        EVENT_ORDER_MAP.put("114885000",6);
        EVENT_ORDER_MAP.put("114764000",7);
        EVENT_ORDER_MAP.put("114770000",8);
        EVENT_ORDER_MAP.put("114775000",9);
        EVENT_ORDER_MAP.put("114947000",10);
        EVENT_ORDER_MAP.put("114774000",11);
        EVENT_ORDER_MAP.put("114884000",12);
        EVENT_ORDER_MAP.put("114873000",13);
        EVENT_ORDER_MAP.put("114769000",14);
        EVENT_ORDER_MAP.put("114937000",15);
        EVENT_ORDER_MAP.put("114771000",16);
        EVENT_ORDER_MAP.put("114875000",17);
        EVENT_ORDER_MAP.put("114942000",18);
        EVENT_ORDER_MAP.put("114882000",19);
        EVENT_ORDER_MAP.put("114773000",20);
        EVENT_ORDER_MAP.put("114765000",21);
        EVENT_ORDER_MAP.put("114946000",22);
        EVENT_ORDER_MAP.put("114939000",23);
        EVENT_ORDER_MAP.put("114945000",24);
        EVENT_ORDER_MAP.put("114949000",25);
        EVENT_ORDER_MAP.put("114948000",26);
        EVENT_ORDER_MAP.put("114766000",27);
        EVENT_ORDER_MAP.put("114767000",27);
        EVENT_ORDER_MAP.put("114876000",27);
        EVENT_ORDER_MAP.put("114938000",27);
        EVENT_ORDER_MAP.put("114943000",28);
        EVENT_ORDER_MAP.put("114772000",29);
        EVENT_ORDER_MAP.put("114878000",30);



        FINAL_MAP.put("115191000 ",1);
        FINAL_MAP.put("115262000",2);
        FINAL_MAP.put("115264000",3);
        FINAL_MAP.put("115283000",4);
        FINAL_MAP.put("115337000",5);
        FINAL_MAP.put("115342000",6);
        FINAL_MAP.put("115371000",7);
        FINAL_MAP.put("115387000",8);
        FINAL_MAP.put("115391000",9);
        FINAL_MAP.put("115399000",10);
        FINAL_MAP.put("115401000",11);
        FINAL_MAP.put("115425000",12);
        FINAL_MAP.put("115466000",13);
        FINAL_MAP.put("115497000",14);
        FINAL_MAP.put("115527000",15);
        FINAL_MAP.put("115528000",16);
        FINAL_MAP.put("115535000",17);
        FINAL_MAP.put("115538000",18);
        FINAL_MAP.put("115613000",19);

        SEMI_FINAL_MAP.put("115190000 ",1);
        SEMI_FINAL_MAP.put("115291000",2);
        SEMI_FINAL_MAP.put("115303000",3);
        SEMI_FINAL_MAP.put("115307000",4);
        SEMI_FINAL_MAP.put("115322000",5);
        SEMI_FINAL_MAP.put("115329000",6);
        SEMI_FINAL_MAP.put("115330000",7);
        SEMI_FINAL_MAP.put("115389000",8);
        SEMI_FINAL_MAP.put("115407000",9);
        SEMI_FINAL_MAP.put("115410000",10);
        SEMI_FINAL_MAP.put("115427000",11);
        SEMI_FINAL_MAP.put("115429000",12);
        SEMI_FINAL_MAP.put("115436000",13);
        SEMI_FINAL_MAP.put("115442000",14);
        SEMI_FINAL_MAP.put("115449000",15);
        SEMI_FINAL_MAP.put("115451000",16);
        SEMI_FINAL_MAP.put("115457000",17);
        SEMI_FINAL_MAP.put("115498000",18);
        SEMI_FINAL_MAP.put("115503000",19);
        SEMI_FINAL_MAP.put("115510000",20);
        SEMI_FINAL_MAP.put("115514000",21);
        SEMI_FINAL_MAP.put("115545000",22);
        SEMI_FINAL_MAP.put("115556000",23);
        SEMI_FINAL_MAP.put("115602000",24);


    }

    public static void main(String[] rags){

        Map<String, String> params = Maps.newHashMap();

//        params.put("startDate","20160716");
//        params.put("span","3");
//        params.put("count","100");

//        params.put("cid","50001");
//        params.put("tag","种林");


//        params.put("startTime","20160716212721");
//        params.put("cb","getOlyBaiduSchedule");


//        params.put("startDate","20160910");
//        params.put("caller","2026");
//        params.put("span","7");
//        String sign = LeSignature.getSignature("06a5ac9504e3a3c83574cf7a6479be2d", params);

//        params.put("first","1");
//        params.put("startTime","20160720000000");
//        params.put("status","3");
//        params.put("episodeType","0");
//        params.put("liveType","0");
//        params.put("count","50");
//        params.put("page","1");





//        params.put("tag","开幕式");

        params.put("caller","2001");
        params.put("tagId","100017008");

        String sign = LeSignature.getSignature("7E49DB050A9F4042CF38294E2CEC0F14", params); //2001
//        String sign = LeSignature.getSignature("06a5ac9504e3a3c83574cf7a6479be2d", params); //2004
//        String sign = LeSignature.getSignature("9BFTBXUN5J9MR7GJNHUHCQ0CUELTCH8V", params); //2010
//        String sign = LeSignature.getSignature("LMK5KRV3SGYNEJYSRMTH68VDZIT3IAYU", params); //2011
//        String sign = LeSignature.getSignature("YTOWDK3IYXFURUQMPT7PO1M5U8AP9TQX", params); //2012
//        String sign = LeSignature.getSignature("40A29L3ITU7N1CQET6KTVCG8NHSXGAIF",params);//2013
//        String sign = LeSignature.getSignature("LMK5KRV3SGYNEJYSRMTH68VDZIT3IAYU",params);//2018
//        System.out.println("sign: " + sign);
//        String sign = LeSignature.getSignature("0N3MUR9LGW6096XV8QEPERPEIDSEDKUR",params);//2015
//        String sign = LeSignature.getSignature("KQBK7DFTMX3ZJX987SVEZWUSJ6OQORYX",params);//2021
//        String sign = LeSignature.getSignature("OF373BJSNT0X4SHBHZHJWHB63M48TZTJ",params);//2023
//        String sign = LeSignature.getSignature("XGFH242SMJ32GY5GRWJXKA5AH1LGYWBL",params);//2026

        System.out.println("sign: " + sign);

//        System.out.println(LeDateUtils.formatYMDTHMSZ(new Date()).replace("Z",""));

//        System.out.println( LeIdApis.checkIdTye(1009180005L));

//        Map<String,Object> result = MmsApis.getVideoData(25782175L);
//        System.out.println("result: " + result.get("mid").toString().replace(",","").trim());

    }
}
