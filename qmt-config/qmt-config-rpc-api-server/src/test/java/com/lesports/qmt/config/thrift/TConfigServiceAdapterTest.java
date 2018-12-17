package com.lesports.qmt.config.thrift;

import com.lesports.AbstractIntegrationTest;
import com.lesports.qmt.config.api.dto.TMenu;
import com.lesports.qmt.config.api.service.TConfigService;
import org.apache.thrift.TException;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by denghui on 2016/12/21.
 */
public class TConfigServiceAdapterTest extends AbstractIntegrationTest {

    @Resource
    private TConfigService.Iface configService;

    @Test
    public void menu() throws TException {
        TMenu tMenu = configService.getTMenuById(33501018L);
        System.out.println(tMenu);
    }
}
