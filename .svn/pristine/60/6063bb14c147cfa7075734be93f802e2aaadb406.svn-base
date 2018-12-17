package com.lesports.qmt.sbd.adapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.qmt.sbd.api.common.TeamType;
import com.lesports.qmt.sbd.model.Player;
import com.lesports.qmt.sbd.param.PlayerParam;
import com.lesports.qmt.sbd.vo.PlayerVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by lufei1 on 2016/11/16.
 */
@Component
public class PlayerAdapter {

    public PlayerVo toVo(PlayerParam playerParam) {
        PlayerVo playerVo = new PlayerVo();
        playerVo.setId(playerParam.getId());
        playerVo.setName(playerParam.getName());
        playerVo.setGameFType(playerParam.getGameFType());
        playerVo.setAbbreviation(playerParam.getAbbreviation());
        playerVo.setGender(playerParam.getGender());
        playerVo.setBirthday(playerParam.getBirthday());
        playerVo.setCountryId(playerParam.getCountryId());
        playerVo.setArea(playerParam.getArea());
        playerVo.setHeight(playerParam.getHeight());
        playerVo.setWeight(playerParam.getWeight());
        playerVo.setPosition(playerParam.getPosition());
        playerVo.setDesc(playerParam.getDesc());

        //俱乐部球队号码
        Map<TeamType, Player.PlayingTeam> teamNumber = JSON.parseObject(playerParam.getTeamNumber(), new TypeReference<Map<TeamType, Player.PlayingTeam>>() {
        });
        if (MapUtils.isNotEmpty(teamNumber)) {
            playerVo.setTeamNumber(teamNumber);
        }
        //职业生涯
        List<Player.CareerItem> career = JSON.parseArray(playerParam.getCareer(), Player.CareerItem.class);
        if (CollectionUtils.isNotEmpty(career)) {
            playerVo.setCareer(career);
        }
        //球员头像
        ImageUrlExt image = JSON.parseObject(playerParam.getImage(), ImageUrlExt.class);
        if (image != null) {
            playerVo.setImage(image);
        }

        //球员头像
        ImageUrlExt bgImage = JSON.parseObject(playerParam.getBgImage(), ImageUrlExt.class);
        if (bgImage != null) {
            playerVo.setBgImage(bgImage);
        }
        return playerVo;
    }

    public Player copyEditableProperties(Player existsEntity, PlayerVo vo) {
        existsEntity.setName(vo.getName());
        existsEntity.setGameFType(vo.getGameFType());
        existsEntity.setAbbreviation(vo.getAbbreviation());
        existsEntity.setGender(vo.getGender());
        existsEntity.setBirthday(vo.getBirthday());
        existsEntity.setCountryId(vo.getCountryId());
        existsEntity.setArea(vo.getArea());
        existsEntity.setHeight(vo.getHeight());
        existsEntity.setWeight(vo.getWeight());
        existsEntity.setPosition(vo.getPosition());
        existsEntity.setDesc(vo.getDesc());
        existsEntity.setTeamNumber(vo.getTeamNumber());
        existsEntity.setCareer(vo.getCareer());
        existsEntity.setImage(vo.getImage());
        return existsEntity;
    }
}
