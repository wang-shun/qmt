package com.lesports.qmt.msg.web;

import com.lesports.id.api.IdType;
import com.lesports.qmt.log.LogContent;
import com.lesports.qmt.log.LoggerHandler;
import com.lesports.qmt.log.WebLogAnnotation;
import com.lesports.qmt.msg.core.LeMessage;
import com.lesports.qmt.msg.service.EpisodeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.ws.rs.QueryParam;

/**
 * Created by zhangdeqiang on 2016/11/18.
 */
@WebLogAnnotation(ID_TYPE = IdType.ALBUM)
@RestController
@RequestMapping(value = "/log")
public class LogstashTestResource {
    private static final Logger LOG = LoggerFactory.getLogger(LogstashTestResource.class);
    private static final Logger monitor = LoggerFactory.getLogger("monitor");
    @Resource
    private EpisodeService episodeService;

    @Value("${hedwig.zk.group}")
    private String group;

    @RequestMapping(value = "/test")
    public String test(String value) {
        LOG.info("hello man:" + value);
        LOG.error("error man");
        return "hello world!";
    }

    @RequestMapping(value = "/getValue")
    public String getValue(String value,String id) {
        LOG.info("id:" + id);
        LOG.error("value:" + value);
        if (id.equals("2")) {
            throw new RuntimeException("就是个错误");
        }
        return value;
    }

    @RequestMapping(value = "/email")
    public String email(String value,String id) {
        LOG.info("id:{},value:{}", id, value);
        StackTraceElement element = Thread.currentThread().getStackTrace()[1];
        String method = element.getClassName() + "." + element.getMethodName();
        LogContent logContent = new LogContent();
        logContent.setEntryId(id);
        logContent.setContent(value);
        logContent.setMethodPath(method);
        logContent.setOperator("operator");
        logContent.setOperatorId("1001");

        logContent.setIdType(IdType.ACTION_LOG); //索引类型
        logContent.setSendTo("zhangdeqiang@le.com"); //多个邮件用逗号分隔
        logContent.setSubject("报警邮件测试"); //邮件标题
        logContent.setMemo(value); //邮件内容
        LoggerHandler.sendEMail(logContent);
        return value;
    }

    @RequestMapping(value = "/monitor")
    public boolean monitor(@QueryParam("value") String value) {
        if (StringUtils.isBlank(value)) {
            LOG.error("{}", "传值为空错误");
            return false;
        }

        monitor.info(value);
        return true;
    }

    @RequestMapping(value = "/handler")
    public boolean handler(@QueryParam("value") String value) {
        LeMessage message = new LeMessage();
        message.setData(value);
        message.setEntityId(11111l);
        //message.setIdType(IdType.ACTION);
        StackTraceElement element = Thread.currentThread().getStackTrace()[1];
        String method = element.getClassName() + "." + element.getMethodName();
        LoggerHandler.out(new LogContent(IdType.ALBUM.name(),"id|name", method, IdType.ALBUM, message, "测试json数据"));
        return true;
    }

    @RequestMapping(value = "/rpc")
    public String rpc(long matchId) {
        boolean result = episodeService.syncEpisode(matchId);
        return "rpc调用结果：" + result;
    }
}
