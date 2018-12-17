package com.lesports.qmt.config.converter;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.lesports.api.common.Platform;
import com.lesports.qmt.config.model.Menu;
import com.lesports.qmt.config.param.SaveMenuParam;
import com.lesports.qmt.config.vo.MenuVo;
import com.lesports.qmt.mvc.QmtVoConverter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by denghui on 2016/12/11.
 */
@Component
public class MenuVoConverter implements QmtVoConverter<Menu, MenuVo> {

    @Override
    public MenuVo toVo(Object object) {
        SaveMenuParam param = (SaveMenuParam) object;
        MenuVo vo = new MenuVo();
        vo.setId(param.getId());
        vo.setName(param.getName());
        List<Platform> platforms = JSON.parseArray(param.getPlatforms(), Platform.class);
        if (CollectionUtils.isEmpty(platforms)) {
            throw new IllegalArgumentException("platform is required");
        }
        vo.setPlatforms(Sets.newHashSet(platforms));
        return vo;
    }

    @Override
    public Menu copyEditableProperties(Menu existsEntity, MenuVo menuVo) {
        existsEntity.setName(menuVo.getName());
        existsEntity.setPlatforms(menuVo.getPlatforms());
        return existsEntity;
    }
}
