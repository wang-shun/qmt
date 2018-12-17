package com.lesports.qmt.base.web.test;

import com.lesports.qmt.base.web.BaseWebApplication;
import com.lesports.qmt.base.web.util.MailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zhangdeqiang on 2017/2/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BaseWebApplication.class)
public class MailTest {
    @Autowired
    private MailUtil mailUtil;

    private static final String to = "zhangdeqiang@le.com,lufei1@le.com";

    @Test
    public void sendSimpleMail() {
        mailUtil.sendSimpleMail(to, "zhangdeqiang@le.com", "主题：简单邮件", "测试邮件内容");
    }

    @Test
    public void sendAttachmentsMail() {
        mailUtil.sendAttachmentsMail(to, "主题：带附件的邮件", "有附件，请查收！", "E:\\data\\appdatas\\cat\\client.xml");
    }

    @Test
    public void sendInlineResourceMail() {
        String rscId = "rscId001";
        mailUtil.sendInlineResourceMail(to,
                "主题：嵌入静态资源的邮件",
                "<html><body>这是有嵌入静态资源：<img src=\'cid:" + rscId + "\' ></body></html>",
                "C:\\Users\\zhangdeqiang\\Documents\\Tencent Files\\6453910\\FileRecv\\MobileFile\\Image\\D{{ER4960%ZJB(SCD011)]9.png",
                rscId);
    }

    public static void main(String[] args) {
        System.out.println(new String[]{to});
    }
}
