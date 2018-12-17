package com.lesports.qmt.config.service.impl;

import com.lesports.id.api.IdType;
import com.lesports.id.client.LeIdApis;
import com.lesports.mongo.repository.MongoCrudRepository;
import com.lesports.qmt.QmtOperationType;
import com.lesports.qmt.config.api.dto.TMenu;
import com.lesports.qmt.config.cache.TMenuCache;
import com.lesports.qmt.config.converter.TMenuConverter;
import com.lesports.qmt.config.model.Menu;
import com.lesports.qmt.config.repository.MenuRepository;
import com.lesports.qmt.config.service.MenuService;
import com.lesports.qmt.service.support.AbstractQmtService;
import com.lesports.utils.LeDateUtils;
import com.lesports.utils.math.LeNumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * Created by lufei1 on 2015/7/2.
 */
@Service("menuService")
public class MenuServiceImpl extends AbstractQmtService<Menu,Long> implements MenuService {

    private static final Logger LOG = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Resource
    private MenuRepository menuRepository;
    @Resource
    private TMenuCache menuCache;
    @Resource
    private TMenuConverter menuConverter;

    @Override
    public TMenu getTMenuById(long id) {
		TMenu tMenu = menuCache.findOne(id);
		if (tMenu == null) {
			Menu menu = menuRepository.findOne(id);
			if (menu == null) {
				LOG.warn("fail to getTMenuById, menu no exists. id : {}", id);
				return null;
			}
			tMenu = menuConverter.toDto(menu);
			if (tMenu == null) {
				LOG.warn("fail to getTMenuById, toTVo fail. id : {}", id);
				return null;
			}
			menuCache.save(tMenu);
		}
		return tMenu;
    }

    @Override
    protected QmtOperationType getOpreationType(Menu entity) {
        if (LeNumberUtils.toLong(entity.getId()) <= 0) {
            return QmtOperationType.CREATE;
        }
        return QmtOperationType.UPDATE;
    }

    @Override
    protected boolean doCreate(Menu entity) {
        entity.setId(LeIdApis.nextId(IdType.MENU));
        entity.setDeleted(false);
        return menuRepository.save(entity);
    }

    @Override
    protected boolean doAfterCreate(Menu entity) {
        return true;
    }

    @Override
    protected boolean doUpdate(Menu entity) {
        return menuRepository.save(entity);
    }

    @Override
    protected boolean doAfterUpdate(Menu entity) {
        return true;
    }

    @Override
    protected boolean doDelete(Long id) {
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        Update up = update("deleted", true).set("update_at", now);
        return menuRepository.update(id, up);
    }

    @Override
    protected boolean doAfterDelete(Menu entity) {
        return menuCache.delete(entity.getId());
    }

    @Override
    protected Menu doFindExistEntity(Menu entity) {
        return findOne(entity.getId());
    }

    @Override
    public boolean publishMenu(Long id){
        return menuCache.delete(id);
    }

	@Override
	protected MongoCrudRepository getInnerRepository() {
		return menuRepository;
	}

}
