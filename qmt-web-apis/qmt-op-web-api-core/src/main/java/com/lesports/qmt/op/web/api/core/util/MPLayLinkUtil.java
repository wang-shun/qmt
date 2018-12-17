package com.lesports.qmt.op.web.api.core.util;

import com.lesports.api.common.CallerParam;
import com.lesports.qmt.sbc.api.common.EpisodeType;
import com.lesports.qmt.sbc.api.dto.TComboEpisode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhonglin on 2016/7/1.
 */
public class MPLayLinkUtil {
    private static final Logger LOG = LoggerFactory.getLogger(MPLayLinkUtil.class);
    private static final String M_OP_MATCH_PLAY_LINK = "http://m.sports.le.com/op/match/%s.html";
    private static final String M_OP_MATCH_CSL_PLAY_LINK = "http://m.sports.le.com/op/csl/match/%s.html";

    private static final String M_OP_PROGRAM_PLAY_LINK = "http://m.sports.le.com/op/";
    private static final String M_OP_PROGRAM_CSL_PLAY_LINK = "http://m.sports.le.com/op/";

    private static final String M_OP_VIDEO_PLAY_LINK = "http://m.sports.le.com/op/video/%s.html";
    private static final String M_OP_VIDEO_CSL_PLAY_LINK = "http://m.sports.le.com/op/csl/video/%s.html";
    private static final String PC_VIDEO_CSL_PLAY_LINK = "http://sports.le.com/video/%s.html";

    /**
     * 获取m站节目播放链接
     *
     * @param episode
     * @return
     */
    public static String getEpisodeMOpPlayLink(TComboEpisode episode,CallerParam caller) {
        String mPlayLink = "";

        if(episode == null) return  mPlayLink;
        if (episode.getType() == EpisodeType.MATCH) {
            mPlayLink = String.format(M_OP_MATCH_PLAY_LINK, episode.getMid());
        } else if (episode.getType() == EpisodeType.PROGRAM) {
            if(StringUtils.isNotBlank(episode.getPlayLink())){
                int pos = episode.getPlayLink().lastIndexOf("live");
                mPlayLink = M_OP_PROGRAM_PLAY_LINK + episode.getPlayLink().substring(pos);
            }
        }
        return  mPlayLink;
    }

    /**
     * 获取m站中超节目播放链接
     *
     * @param episode
     * @return
     */
    public static String getEpisodeMOpCslPlayLink(TComboEpisode episode,CallerParam caller) {
        String mPlayLink = "";
        if(episode == null) return  mPlayLink;
        if (episode.getType() == EpisodeType.MATCH) {
            mPlayLink = String.format(M_OP_MATCH_CSL_PLAY_LINK, episode.getMid());
        } else if (episode.getType() == EpisodeType.PROGRAM) {
            if(StringUtils.isNotBlank(episode.getPlayLink())){
                int pos = episode.getPlayLink().lastIndexOf("live");
                mPlayLink = M_OP_PROGRAM_CSL_PLAY_LINK + episode.getPlayLink().substring(pos);
            }
        }
        return  mPlayLink + "?ch=zchao"+caller.getCallerId();
    }

    /**
     * 获取对外m站视频播放链接
     *
     * @param id
     * @return
     */
    public static String getVideoMOpPlayLink(Long id,CallerParam caller) {
        String mOpPlayLink = "";
        if (id == null || id == 0)
            return mOpPlayLink;
        mOpPlayLink = String.format(M_OP_VIDEO_PLAY_LINK,id);
        return  mOpPlayLink;
    }

    /**
     * 获取对外m站中超视频播放链接
     *
     * @param id
     * @return
     */
    public static String getVideoMOpCslPlayLink(Long id,CallerParam caller) {
        String mOpPlayLink = "";
        if (id == null || id == 0)
            return mOpPlayLink;
        mOpPlayLink = String.format(M_OP_VIDEO_CSL_PLAY_LINK,id);
        return  mOpPlayLink + "?ch=zchao"+caller.getCallerId();
    }

    /**
     * 获取对外pc站中超视频播放链接
     *
     * @param id
     * @return
     */
    public static String getVideoPCOpCslPlayLink(Long id,CallerParam caller) {
        String pcOpPlayLink = "";
        if (id == null || id == 0)
            return pcOpPlayLink;
        pcOpPlayLink = String.format(PC_VIDEO_CSL_PLAY_LINK,id);
        return  pcOpPlayLink + "?ch=zchao"+caller.getCallerId();
    }
}
