package com.lesports.qmt.sbd.helper;

import com.lesports.qmt.config.api.dto.DictEntryTopType;
import com.lesports.qmt.config.client.QmtConfigDictInternalApis;
import com.lesports.qmt.config.model.DictEntry;
import org.springframework.stereotype.Component;

/**
 * User: ellios
 * Time: 15-6-8 : 上午11:40y
 */
@Component("dictHelper")
public class DictHelper {

    /**
     * 获取字典所属的顶级分类
     *
     * @param entryId
     * @return
     */
    public DictEntryTopType getDictEntryTopType(long entryId) {
        DictEntry entry = QmtConfigDictInternalApis.getDictById(entryId);
        if (entry == null) {
            return null;
        }
        long topParentId = entry.getTopParentId() != null ? entry.getTopParentId() : 0;
        if (topParentId == 0) {
            //已经是顶级字典项了
            topParentId = entry.getId();
        }
        return getTopType(topParentId);
    }

    private DictEntryTopType getTopType(long topEntryId) {
        int topId = (int)topEntryId;
        switch (topId) {
			case 100001000:
                return DictEntryTopType.GAME_FIRST_TYPE;
            case 100003000:
                return DictEntryTopType.GROUP;
            case 100004000:
                return DictEntryTopType.ROUND;
            case 100005000:
                return DictEntryTopType.STAGE;
            case 100006000:
                return DictEntryTopType.STATION;
            case 100007000:
                return DictEntryTopType.ROLE;
            case 100008000:
                return DictEntryTopType.POSITION;
        }
        return null;
    }

}
