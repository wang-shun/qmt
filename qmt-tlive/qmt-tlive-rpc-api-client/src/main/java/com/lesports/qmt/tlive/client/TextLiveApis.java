package com.lesports.qmt.tlive.client;

import com.lesports.qmt.tlive.api.common.TextLiveMessageType;
import com.lesports.qmt.tlive.api.common.UpDownAct;
import com.lesports.qmt.tlive.api.dto.TAnchor;
import com.lesports.qmt.tlive.api.dto.TTextLive;
import com.lesports.qmt.tlive.api.dto.TTextLiveMessage;
import com.lesports.qmt.tlive.api.dto.TVote;
import com.lesports.qmt.tlive.api.service.TTextLiveService;
import me.ellios.hedwig.rpc.client.ClientBuilder;
import me.ellios.hedwig.rpc.core.ServiceType;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * User: ellios
 * Time: 15-11-3 : 下午9:31
 */
public class TextLiveApis {

    private static final Logger LOG = LoggerFactory.getLogger(TextLiveApis.class);


    private static final TTextLiveService.Iface textLiveService = new ClientBuilder<TTextLiveService.Iface>()
            .serviceType(ServiceType.THRIFT).serviceFace(TTextLiveService.Iface.class).build();

    public static List<String> getLiveMessageIdsByTextLiveId(long textLiveId) {
        try {
            return textLiveService.getLiveMessageIdsByTextLiveId(textLiveId);
        } catch (TException e) {
            LOG.error("fail to getLiveMessageIdsByTextLiveId. textLiveId : {} ", textLiveId, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static TTextLive getTextLiveById(long textLiveId) {
        try {
            return textLiveService.getTextLiveById(textLiveId);
        } catch (TException e) {
            LOG.error("fail to getTextLiveById. textLiveId : {} ", textLiveId, e);
        }
        return null;
    }

    public static int getLiveMessageLatestIndex(long textLiveId, long section) {
        try {
            return textLiveService.getLiveMessageLatestIndex(textLiveId, section);
        } catch (TException e) {
            LOG.error("fail to getLiveMessageLatestIndex. textLiveId : {} ,section:{}", textLiveId, section, e);
        }
        return 0;
    }

    public static List<TTextLiveMessage> getLiveMessageByPage(long textLiveId, long section, int page) {
        try {
            return textLiveService.getLiveMessageByPage(textLiveId, section, page);
        } catch (TException e) {
            LOG.error("fail to getLiveMessageByPage. textLiveId : {} ,section:{} ,page:{}", textLiveId, section, page, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static TTextLive getMainTTextLiveByEid(long eid) {
        try {
            return textLiveService.getMainTTextLiveByEid(eid);
        } catch (TException e) {
            LOG.error("fail to getMainTTextLiveByEid. eid : {} ", eid, e);
        }
        return null;
    }

    public static List<TTextLiveMessage> getLiveMessageByIds(List<Long> ids) {
        try {
            return textLiveService.getLiveMessageByIds(ids);
        } catch (TException e) {
            LOG.error("fail to getLiveMessageByIds. ids : {} ", ids, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static List<String> getLiveMessagesIds(long textLiveId, long section, TextLiveMessageType liveMessageType) {
        try {
            return textLiveService.getLiveMessagesIds(textLiveId, section, liveMessageType);
        } catch (TException e) {
            LOG.error("fail to getLiveMessagesIds. textLiveId : {},section : {},liveMessageType : {} ", textLiveId, section, liveMessageType, e);
        }
        return Collections.EMPTY_LIST;
    }

    public static TVote addVote(long voteId, long optionId) {
        try {
            return textLiveService.addVote(voteId, optionId);
        } catch (TException e) {
            LOG.error("fail to addVote. voteId : {},optionId : {}", voteId, optionId, e);
        }
        return null;
    }

    public static TVote getVote(long voteId) {
        try {
            return textLiveService.getVote(voteId);
        } catch (TException e) {
            LOG.error("fail to getVote. voteId : {}", voteId, e);
        }
        return null;
    }

    public static long reportOnlineCount(long eid) {
        try {
            return textLiveService.reportOnlineCount(eid);
        } catch (TException e) {
            LOG.error("fail to reportOnlineCount. eid : {}", eid, e);
        }
        return 0L;
    }

    public static long getOnlineCount(long eid) {
        try {
            return textLiveService.getOnlineCount(eid);
        } catch (TException e) {
            LOG.error("fail to getOnlineCount. eid : {}", eid, e);
        }
        return 0L;
    }

    public static TAnchor upDownAnchor(long textLiveId, long anchorId, UpDownAct act) {
        try {
            return textLiveService.upDownAnchor(textLiveId, anchorId, act);
        } catch (TException e) {
            LOG.error("fail to upDownAnchor. textLiveId : {},anchorId : {},act : {}", textLiveId, anchorId, act, e);
        }
        return null;
    }

    public static TAnchor getAnchorUpDown(long textLiveId, long anchorId) {
        try {
            return textLiveService.getAnchorUpDown(textLiveId, anchorId);
        } catch (TException e) {
            LOG.error("fail to getAnchorUpDown. textLiveId : {},anchorId : {}", textLiveId, anchorId, e);
        }
        return null;
    }
}
