package com.lesports.qmt.op.web.api.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.CallerParam;
import com.lesports.qmt.op.web.api.core.service.PinZhuanService;
import com.lesports.qmt.op.web.api.core.util.Constants;
import com.lesports.qmt.sbd.api.dto.TCompetitor;
import com.lesports.qmt.sbd.api.dto.TMatch;
import com.lesports.qmt.sbd.client.SbdMatchApis;
import com.lesports.sms.api.common.GroundType;
import com.lesports.utils.LeDateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhonglin on 2016/7/13.
 */
@Service("pinZhuanService")
public class PinZhuanServiceImpl implements PinZhuanService {

    private static final Logger LOG = LoggerFactory.getLogger(PinZhuanServiceImpl.class);
    private static RestTemplate template =new RestTemplate();
    public  static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日 HH:mm");
    public  static SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm");

    @Override
    public Map<String,Object> getPinZhuanFocus (CallerParam caller){
        Map<String,Object> focus = Maps.newHashMap();
        String today = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());

        //焦点图信息
        JSONObject fileContent = template.getForObject(Constants.CMS_DATA_URL+"6192.json", JSONObject.class);
        List<Map<String,Object>> contents = (List<Map<String,Object>>)fileContent.get("blockContent");

        for(Map<String,Object> content:contents){
            if(content.get("content") == null)continue;
//            String title = (String)content.get("title");
            String desc = (String)content.get("shorDesc");

            String image = (String)content.get("pic1");
            String url = (String)content.get("url");
//            focus.put("title",title);
            focus.put("desc",desc);
            focus.put("image",image);
            focus.put("url",url+"?ch=pinzhuan1");
        }

        //比赛信息
        List<Map<String,String>>  matchList = Lists.newArrayList();
        fileContent = template.getForObject(Constants.CMS_DATA_URL+"6195.json", JSONObject.class);
        contents = (List<Map<String,Object>>)fileContent.get("blockContent");
        for(Map<String,Object> content:contents){
            if(content.get("subTitle") == null)continue;
            TMatch match = SbdMatchApis.getTMatchById(Long.parseLong((String) content.get("subTitle")), caller);
            if(match == null)continue;
            Map<String,String> matchMap = new HashMap<String,String>();
            matchMap.put("status",match.getStatus().getValue()+"");
            for(TCompetitor competitor:match.getCompetitors()){
                if(competitor.getGround().equals(GroundType.HOME)){
                    matchMap.put("homeTeam",competitor.getName());
                    if(StringUtils.isNotBlank(competitor.getFinalResult())){
                        matchMap.put("homeScore",competitor.getFinalResult());
                    }
                    else{
                        matchMap.put("homeScore","0");
                    }
                }

                if(competitor.getGround().equals(GroundType.AWAY)){
                    matchMap.put("awayTeam",competitor.getName());
                    if(StringUtils.isNotBlank(competitor.getFinalResult())){
                        matchMap.put("awayScore",competitor.getFinalResult());
                    }
                    else{
                        matchMap.put("awayScore","0");
                    }
                }
            }
            if(today.equals(match.getStartTime().substring(0,8))){
                matchMap.put("startTime","今天"+simpleDateFormat1.format(LeDateUtils.parseYYYYMMDDHHMMSS(match.getStartTime())));
            }
            else {
                matchMap.put("startTime",simpleDateFormat.format(LeDateUtils.parseYYYYMMDDHHMMSS(match.getStartTime())));
            }
            matchMap.put("matchUrl","http://sports.le.com/match/"+match.getId()+".html?ch=pinzhuan1");
            matchList.add(matchMap);
        }
        focus.put("matches",matchList);

        //转成
        return  focus;
    }

//    @Override
//    public Map<String,Object> getPinZhuanFocus (CallerParam caller){
//        Map<String,Object> focus = Maps.newHashMap();
//        String today = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
//
//        //焦点图信息
//        JSONObject fileContent = template.getForObject(Constants.CMS_DATA_URL+"5069.json", JSONObject.class);
//        List<Map<String,Object>> contents = (List<Map<String,Object>>)fileContent.get("blockContent");
//
//        for(Map<String,Object> content:contents){
//            if(content.get("content") == null)continue;
//            String desc = (String)content.get("shorDesc");
//
//            if(StringUtils.isBlank(desc)){
//                desc = (String)content.get("subTitle")+(String)content.get("title");
//            }
//
//            String image = (String)content.get("pic1");
//            String url = (String)content.get("url");
//            focus.put("desc",desc);
//            focus.put("image",image);
//            focus.put("url",url+"?ch=pinzhuan1");
//        }
//
//        //比赛信息
//
//        long cid = 47001L;
//        TCompetitionSeason competitionSeason = SbdsApis.getLatestTCompetitionSeasonsByCid(cid, CallerUtils.getDefaultCaller());
//        if(competitionSeason == null){
//            LOG.error("competitionSeason is  null cid:{}", cid);
//        }
//
//
//        PageParam pageParam = new PageParam();
//        pageParam.setCount(20);
//        pageParam.setPage(0);
//        Sort sort = new Sort();
//        sort.setOrders(Lists.newArrayList(new Order("start_time", Direction.ASC)));
//        pageParam.setSort(sort);
//
//        CallerParam callerParam = new CallerParam();
//        callerParam.setCallerId(LeConstants.LESPORTS_PC_CALLER_CODE);
//        callerParam.setLanguage(LanguageCode.ZH_CN);
//        callerParam.setCountry(CountryCode.CN);
//
//
//        GetEpisodesOfSeasonByMetaEntryIdParam p = new GetEpisodesOfSeasonByMetaEntryIdParam();
//        p.setCid(cid);
//        p.setCsid(competitionSeason.getId());
//        p.setEntryId(competitionSeason.getCurrentRoundId());
//        List<TComboEpisode> episodes = SopsApis.getEpisodesOfSeasonByMetaEntryId(p, pageParam, callerParam);
//
//        List<TMatch> matches = Lists.newArrayList();
//        List<TMatch> todayMatches = Lists.newArrayList();
//        List<TMatch> yesterdayMatches = Lists.newArrayList();
//        List<TMatch> tomorrowMatches = Lists.newArrayList();
//        String nowDate = LeDateUtils.formatYYYYMMDD(new Date());
//        for(TComboEpisode episode:episodes){
//            TMatch match = SbdsApis.getTMatchById(episode.getMid(), caller);
//            String matchDate = match.getStartTime().substring(0,8);
//            if(matchDate.equals(nowDate)){
//                todayMatches.add(match);
//            }
//            else if(matchDate.compareTo(nowDate) > 0){
//                tomorrowMatches.add(match);
//            }
//            else{
//                yesterdayMatches.add(match);
//            }
//        }
//
//        int size = 0;
//        if(CollectionUtils.isNotEmpty(todayMatches)){
//            if(todayMatches.size()>=2){
//                matches = getRandomNum(todayMatches,2);
//                size = 2;
//            }
//            else{
//                matches = getRandomNum(todayMatches,1);
//                size = 1;
//            }
//        }
//        if(size<2 && CollectionUtils.isNotEmpty(todayMatches)){
//            int selected = 2 - size;
//            if(todayMatches.size()>=selected){
//
//            }
//        }
//
//        for(TMatch match:matches){
//            Map<String,String> matchMap = new HashMap<String,String>();
//            matchMap.put("status",match.getStatus().getValue()+"");
//            for(TCompetitor competitor:match.getCompetitors()){
//                if(competitor.getGround().equals(GroundType.HOME)){
//                    matchMap.put("homeTeam",competitor.getName());
//                    if(StringUtils.isNotBlank(competitor.getFinalResult())){
//                        matchMap.put("homeScore",competitor.getFinalResult());
//                    }
//                    else{
//                        matchMap.put("homeScore","0");
//                    }
//                }
//
//                if(competitor.getGround().equals(GroundType.AWAY)){
//                    matchMap.put("awayTeam",competitor.getName());
//                    if(StringUtils.isNotBlank(competitor.getFinalResult())){
//                        matchMap.put("awayScore",competitor.getFinalResult());
//                    }
//                    else{
//                        matchMap.put("awayScore","0");
//                    }
//                }
//            }
//            if(today.equals(match.getStartTime().substring(0,8))){
//                matchMap.put("startTime","今天"+simpleDateFormat1.format(LeDateUtils.parseYYYYMMDDHHMMSS(match.getStartTime())));
//            }
//            else {
//                matchMap.put("startTime",simpleDateFormat.format(LeDateUtils.parseYYYYMMDDHHMMSS(match.getStartTime())));
//            }
//            matchMap.put("matchUrl","http://sports.le.com/match/"+match.getId()+".html?ch=pinzhuan1");
////            matchList.add(matchMap);
//        }
////        focus.put("matches",matchList);
//
//        //转成
//        return  focus;
//    }

    @Override
    public List<Map<String,Object>> getPinZhuanHot (CallerParam caller){

        List<Map<String,Object>> hots = Lists.newArrayList();

        //赛事集锦
        JSONObject fileContent = template.getForObject(Constants.CMS_DATA_URL+"6198.json", JSONObject.class);
        List<Map<String,Object>> contents = (List<Map<String,Object>>)fileContent.get("blockContent");
        Map<String,Object> hot = new  HashMap<String,Object>();
        hot.put("type",0);
        List<Map<String,String>> infos = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(contents)){
            int size = 0;
            for(Map<String,Object> content:contents) {
                if(size<3){
                    Map<String,String> info = new HashMap<String,String>();
                    String title = (String)content.get("title");
                    String image = (String)content.get("pic1");
                    String url = (String)content.get("url");
                    info.put("title",title);
                    info.put("image",image);
                    info.put("url",url+"?ch=pinzhuan2");
                    infos.add(info);
                }
                else{
                    String image = (String)content.get("pic1");
                    String url = (String)content.get("url");
                    hot.put("moreImage",image);
                    hot.put("moreUrl",url+"?ch=pinzhuan2");
                    break;
                }
                size++;
            }
            hot.put("infos",infos);

        }
        hots.add(hot);

        //完整回放
        fileContent = template.getForObject(Constants.CMS_DATA_URL+"6201.json", JSONObject.class);
        contents = (List<Map<String,Object>>)fileContent.get("blockContent");
        hot = new  HashMap<String,Object>();
        hot.put("type",1);
        infos = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(contents)){
            int size = 0;
            for(Map<String,Object> content:contents) {
                if(size<3){
                    Map<String,String> info = new HashMap<String,String>();
                    String title = (String)content.get("title");
                    String image = (String)content.get("pic1");
                    String url = (String)content.get("url");
                    info.put("title",title);
                    info.put("image",image);
                    info.put("url",url+"?ch=pinzhuan2");
                    infos.add(info);
                }
                else{
                    String image = (String)content.get("pic1");
                    String url = (String)content.get("url");
                    hot.put("moreImage",image);
                    hot.put("moreUrl",url+"?ch=pinzhuan2");
                    break;
                }
                size++;
            }
            hot.put("infos",infos);

        }
        hots.add(hot);

        //中超资讯
        fileContent = template.getForObject(Constants.CMS_DATA_URL+"6204.json", JSONObject.class);
        contents = (List<Map<String,Object>>)fileContent.get("blockContent");
        hot = new  HashMap<String,Object>();
        hot.put("type",2);
        infos = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(contents)){
            int size = 0;
            for(Map<String,Object> content:contents) {
                if(size<3){
                    Map<String,String> info = new HashMap<String,String>();
                    String title = (String)content.get("title");
                    String image = (String)content.get("pic1");
                    String url = (String)content.get("url");
                    info.put("title",title);
                    info.put("image",image);
                    info.put("url",url+"?ch=pinzhuan2");
                    infos.add(info);
                }
                else{
                    String image = (String)content.get("pic1");
                    String url = (String)content.get("url");
                    hot.put("moreImage",image);
                    hot.put("moreUrl",url+"?ch=pinzhuan2");
                    break;
                }
                size++;
            }
            hot.put("infos",infos);

        }
        hots.add(hot);

        //中超周边
        fileContent = template.getForObject(Constants.CMS_DATA_URL+"6207.json", JSONObject.class);
        contents = (List<Map<String,Object>>)fileContent.get("blockContent");
        hot = new  HashMap<String,Object>();
        hot.put("type",3);
        infos = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(contents)){
            int size = 0;
            for(Map<String,Object> content:contents) {
                if(size<3){
                    Map<String,String> info = new HashMap<String,String>();
                    String title = (String)content.get("title");
                    String image = (String)content.get("pic1");
                    String url = (String)content.get("url");
                    info.put("title",title);
                    info.put("image",image);
                    info.put("url",url+"?ch=pinzhuan2");
                    infos.add(info);
                }
                else{
                    String image = (String)content.get("pic1");
                    String url = (String)content.get("url");
                    hot.put("moreImage",image);
                    hot.put("moreUrl",url+"?ch=pinzhuan2");
                    break;
                }
                size++;
            }
            hot.put("infos",infos);
        }
        hots.add(hot);

        //转成
        return  hots;
    }


    @Override
    public Map<String,Object> getPinZhuanProgram (CallerParam caller){

        Map<String,Object> program = Maps.newHashMap();

        //logo和简介
        JSONObject fileContent = template.getForObject(Constants.CMS_DATA_URL+"6210.json", JSONObject.class);
        List<Map<String,Object>> contents = (List<Map<String,Object>>)fileContent.get("blockContent");

        //第一张图和简介
        if(CollectionUtils.isNotEmpty(contents)){
            Map<String,Object> content = contents.get(0);
            String desc = (String)content.get("shorDesc");
            String image = (String)content.get("pic1");
            String url = (String)content.get("url");
//            focus.put("title",title);
            program.put("desc",desc);
            program.put("image",image);
            program.put("url",url+"?ch=pinzhuan3");
            String bigImage = (String)content.get("pic2");
            program.put("bigImage",bigImage);
        }


        //节目信息
        fileContent = template.getForObject(Constants.CMS_DATA_URL+"6213.json", JSONObject.class);
        contents = (List<Map<String,Object>>)fileContent.get("blockContent");
        List<Map<String,String>> infos = Lists.newArrayList();
        for(Map<String,Object> content:contents) {
            Map<String,String> info = new HashMap<String,String>();
            String title = (String)content.get("title");
            String url = (String)content.get("url");
            info.put("title",title);
            info.put("url",url+"?ch=pinzhuan3");
            infos.add(info);
        }
        program.put("infos",infos);

        //转成
        return  program;
    }

    /**
     * 返回随机数
     * @param list 备选号码
     * @param selected 备选数量
     * @return
     */
    public static List<TMatch> getRandomNum(List<TMatch> list, int selected) {
        List<TMatch> reList = Lists.newArrayList();

        Random random = new Random();
        // 先抽取，备选数量的个数
        if (list.size() >= selected) {
            for (int i = 0; i < selected; i++) {
                // 随机数的范围为0-list.size()-1;
                int target = random.nextInt(list.size());
                reList.add(list.get(target));
                list.remove(target);
            }
        } else {
            selected = list.size();
            for (int i = 0; i < selected; i++) {
                int target = random.nextInt(list.size());
                reList.add(list.get(target));
                list.remove(target);
            }
        }

        return reList;
    }
}
