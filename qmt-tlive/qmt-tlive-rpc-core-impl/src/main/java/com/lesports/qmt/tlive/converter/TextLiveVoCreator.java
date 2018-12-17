package com.lesports.qmt.tlive.converter;

import com.lesports.qmt.tlive.api.common.AnchorRole;
import com.lesports.qmt.tlive.api.dto.TAnchor;
import com.lesports.qmt.tlive.api.dto.TTextLive;
import com.lesports.qmt.tlive.cache.TTextLiveCache;
import com.lesports.qmt.tlive.model.TextLive;
import com.lesports.qmt.tlive.repository.TextLiveRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created by lufei1 on 2015/9/16.
 */
@Component("textLiveVoCreator")
public class TextLiveVoCreator {

    @Resource
    private TextLiveRepository textLiveRepository;
    @Resource
    private TTextLiveCache textLiveCache;

    public TTextLive createTTextLive(long id) {
        TextLive textLive = textLiveRepository.findOne(id);
        if (textLive == null) {
            return null;
        }
        TTextLive tTextLive = new TTextLive();
        fillTTextLive(tTextLive, textLive);
        return tTextLive;
    }

    private void fillTTextLive(TTextLive tTextLive, TextLive textLive) {
        tTextLive.setId(textLive.getId());
        if (textLive.getAnchors() != null) {
            tTextLive.setAnchor(createTSimpleAnchor(textLive.getAnchors()));
        }
        tTextLive.setEid(textLive.getEid());
        tTextLive.setMid(textLive.getMid());
        tTextLive.setOnlineCount(textLiveCache.getOnlineCount(textLive.getEid()));
        tTextLive.setStatus(textLive.getStatus());
    }

    private TAnchor createTSimpleAnchor(Set<TextLive.Anchor> anchors) {
        TAnchor tAnchor = new TAnchor();
        TextLive.Anchor anchor = null;
        for (TextLive.Anchor an : anchors) {
            if (AnchorRole.LEAD_ROLE.equals(an.getRole())) {
                anchor = an;
                break;
            }
        }
        if (anchor != null) {
            tAnchor.setAnchorId(anchor.getAnchorId());
            tAnchor.setName(anchor.getName());
            tAnchor.setLogo(anchor.getLogo());
            tAnchor.setRole(anchor.getRole());
        }
        return tAnchor;
    }
}
