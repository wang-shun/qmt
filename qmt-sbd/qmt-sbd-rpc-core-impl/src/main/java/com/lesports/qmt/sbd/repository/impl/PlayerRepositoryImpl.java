package com.lesports.qmt.sbd.repository.impl;

import com.lesports.mongo.repository.support.AbstractMongoRepository;
import com.lesports.qmt.sbd.model.Player;
import com.lesports.qmt.sbd.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Created by lufei on 2016/10/28.
 */
@Repository("playerRepository")
public class PlayerRepositoryImpl extends AbstractMongoRepository<Player, Long> implements PlayerRepository {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerRepositoryImpl.class);


    @Override
    protected Class<Player> getEntityType() {
        return Player.class;
    }

    @Override
    protected Long getId(Player entity) {
        return entity.getId();
    }


}
