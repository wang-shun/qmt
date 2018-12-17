package com.lesports.qmt.sbd.repository;

import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.sbd.model.Competition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by lufei on 2016/10/24.
 */
public interface CompetitionRepository extends MongoCrudRepository<Competition, Long> {

    /**
     * 根据名字模糊查找赛事
     * @param name
     * @param pageable
     * @return
     */
    Page<Competition> fuzzyFindByName(String name, Pageable pageable);

    /**
     * 分页查找所有的字典目录
     * @param pageable
     * @return
     */
    Page<Competition> findAll(Pageable pageable);

    /**
     * 根据赛事大小项查找
     * @param gameFType
     * @param gameSType
     * @return
     */
    public List<Competition> findCompetitionByFTypeAndSType(long gameFType, long gameSType);

	List<Competition> findCompetitionByCompetitionCodeingId(String codeingId);
}
