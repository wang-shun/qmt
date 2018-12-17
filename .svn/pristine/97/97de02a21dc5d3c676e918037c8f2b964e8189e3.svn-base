package com.lesports.qmt.tlive.thrift;

import com.lesports.qmt.tlive.api.common.TextLiveMessageType;
import com.lesports.qmt.tlive.api.common.UpDownAct;
import com.lesports.qmt.tlive.api.dto.TAnchor;
import com.lesports.qmt.tlive.api.dto.TTextLive;
import com.lesports.qmt.tlive.api.dto.TTextLiveMessage;
import com.lesports.qmt.tlive.api.dto.TVote;
import com.lesports.qmt.tlive.api.service.TTextLiveService;
import com.lesports.qmt.tlive.service.TextLiveMessageService;
import com.lesports.qmt.tlive.service.TextLiveService;
import com.lesports.qmt.tlive.service.VoteService;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

import static me.ellios.hedwig.http.mediatype.ExtendedMediaType.APPLICATION_X_THRIFT;

/**
 * Created by lufei1 on 2015/9/15.
 */
@Service("thriftTextLiveService")
@Path("/r/qmt/textLive")
@Produces({APPLICATION_X_THRIFT})
public class TTextLiveServiceAdapter implements TTextLiveService.Iface {

    private static final Logger LOG = LoggerFactory.getLogger(TTextLiveServiceAdapter.class);

    @Inject
    private TextLiveService textLiveService;
    @Inject
    private TextLiveMessageService textLiveMessageService;
    @Inject
    private VoteService voteService;


    @Override
    public TTextLive getTextLiveById(long id) throws TException {
        try {
            return textLiveService.getTTextLiveById(id);
        } catch (Exception e) {
            LOG.error("fail to getTextLiveById. id : {}", id, e);
        }
        return null;
    }

    @Override
    public TTextLive getMainTTextLiveByEid(long eid) throws TException {
        try {
            return textLiveService.getMainTTextLiveByEid(eid);
        } catch (Exception e) {
            LOG.error("fail to getMainTTextLiveByEid. eid : {}", eid, e);
        }
        return null;
    }

    @Override
    public List<String> getLiveMessageIdsByTextLiveId(long textLiveId) throws TException {
        try {
            return textLiveMessageService.getLiveMessageIdsByTextLiveId(textLiveId);
        } catch (Exception e) {
            LOG.error("fail to getLiveMessageIdsByTextLiveId. textLiveId : {}", textLiveId, e);
        }
        return null;
    }

    @Override
    public List<TTextLiveMessage> getLiveMessageByIds(List<Long> ids) throws TException {
        try {
            return textLiveMessageService.getTLiveMessageByIds(ids);
        } catch (Exception e) {
            LOG.error("fail to getLiveMessageByIds. ids : {}", ids, e);
        }
        return null;
    }

    @Override
    public List<String> getLiveMessagesIds(long textLiveId, long section, TextLiveMessageType type) throws TException {
        try {
            return textLiveMessageService.getLiveMessagesIds(textLiveId, section, type);
        } catch (Exception e) {
            LOG.error("fail to getLiveMessages. textLiveId : {},section : {},type : {}", textLiveId, section, type, e);
        }
        return null;
    }

    @Override
    public int getLiveMessageLatestIndex(long textLiveId, long section) throws TException {
        try {
            return textLiveMessageService.getLatestIndex(textLiveId, section);
        } catch (Exception e) {
            LOG.error("fail to getLiveMessageLatestIndex. textLiveId : {},section : {}", textLiveId, section, e);
        }
        return 0;
    }

    @Override
    public List<TTextLiveMessage> getLiveMessageByPage(long textLiveId, long section, int page) throws TException {
        try {
            return textLiveMessageService.getTLiveMessageByPage(textLiveId, section, page);
        } catch (Exception e) {
            LOG.error("fail to getLiveMessageByPage. textLiveId : {},section : {},page : {}", textLiveId, section, page, e);
        }
        return null;
    }

    @Override
    public TVote addVote(long voteId, long optionId) throws TException {
        try {
            return voteService.addVote(voteId, optionId);
        } catch (Exception e) {
            LOG.error("fail to addVote. voteId : {},optionId : {}", voteId, optionId, e);
        }
        return null;
    }

    @Override
    public TVote getVote(long voteId) throws TException {
        try {
            return voteService.getVote(voteId);
        } catch (Exception e) {
            LOG.error("fail to getVote. voteId : {}", voteId, voteId, e);
        }
        return null;
    }

    @Override
    public long reportOnlineCount(long eid) throws TException {
        try {
            return textLiveService.reportOnlineCount(eid);
        } catch (Exception e) {
            LOG.error("fail to reportOnlineCount. eid : {}", eid, e);
        }
        return 0L;
    }

    @Override
    public long getOnlineCount(long eid) throws TException {
        try {
            return textLiveService.getOnlineCount(eid);
        } catch (Exception e) {
            LOG.error("fail to getOnlineCount. eid : {}", eid, e);
        }
        return 0L;
    }

    @Override
    public TAnchor upDownAnchor(long textLiveId, long anchorId, UpDownAct act) throws TException {
        try {
            return textLiveService.upDownAnchor(textLiveId, anchorId, act);
        } catch (Exception e) {
            LOG.error("fail to upDownAnchor. textLiveId : {},anchorId : {},act : {}", textLiveId, anchorId, act, e);
        }
        return null;
    }

    @Override
    public TAnchor getAnchorUpDown(long textLiveId, long anchorId) throws TException {
        try {
            return textLiveService.getAnchorUpDown(textLiveId, anchorId);
        } catch (Exception e) {
            LOG.error("fail to getAnchorUpDown. textLiveId : {},anchorId : {}", textLiveId, anchorId, e);
        }
        return null;
    }
}
