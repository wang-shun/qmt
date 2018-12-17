package com.lesports.qmt.sbc.service;

import com.lesports.qmt.mvc.QmtPage;
import com.lesports.qmt.mvc.QmtPageParam;
import com.lesports.qmt.mvc.QmtWebService;
import com.lesports.qmt.sbc.vo.ProgramAlbumPeriodVo;
import com.lesports.qmt.sbc.vo.ProgramAlbumVo;

import java.util.List;

/**
 * Created by denghui on 2016/11/15.
 */
public interface ProgramAlbumWebService extends QmtWebService<ProgramAlbumVo, Long> {

    /**
     * 获取自制专辑下的选集列表
     * @param aid
     * @param pageParam
     * @return
     */
    QmtPage<ProgramAlbumPeriodVo> listPeriods(long aid, QmtPageParam pageParam);

    /**
     * 获取某个选集
     * @param eid
     * @return
     */
    ProgramAlbumPeriodVo getPeriodById(long eid);

    /**
     * 保存选集
     * @param aid
     * @param vo
     * @return
     */
    Long savePeriod(long aid, ProgramAlbumPeriodVo vo);

    /**
     * 获取自制专辑下的视频列表
     * @return
     */
    QmtPage<ProgramAlbumPeriodVo.SimpleVideoVo> listVideos(long aid, QmtPageParam pageParam);

    /**
     * 推荐自制专辑排序
     * @param ids
     * @return
     */
    boolean resetRecommendOrder(List<Long> ids);


}
