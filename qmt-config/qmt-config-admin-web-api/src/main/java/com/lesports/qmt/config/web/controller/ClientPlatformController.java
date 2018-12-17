package com.lesports.qmt.config.web.controller;

import com.lesports.id.api.IdType;
import com.lesports.qmt.config.service.ClientPlatformWebService;
import com.lesports.qmt.config.vo.ClientPlatformVo;
import com.lesports.qmt.log.WebLogAnnotation;
import com.lesports.qmt.mvc.QmtPage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by zhangxudong@le.com on 11/10/16.
 */
@RestController
@RequestMapping(value = "/clientPlatforms")
@WebLogAnnotation(ID_TYPE = IdType.CLIENT_PLATFORM)
public class ClientPlatformController {
    @Resource
    private ClientPlatformWebService clientPlatformWebService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public QmtPage<ClientPlatformVo> getClients() {
        return clientPlatformWebService.getAll();
    }
}
