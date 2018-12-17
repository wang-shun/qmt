package com.lesports.qmt.base.web.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * Created by zhangdeqiang on 2017/2/13.
 */
@Component
public class MailUtil {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MailUtil.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;
    @Value("${hedwig.env}")
    private String env;

    /**
     * 发送纯文本的简单邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    public void sendSimpleMail(String to, String cc, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        this.setTo(to, message);
        this.setCc(cc, message);
        message.setSubject("[" + env + "]" + subject);
        message.setText(content);
        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            logger.error("send simple email error！", e);
        }
    }

    /**
     * 发送html格式的邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    public void sendHtmlMail(String to, String cc, String subject, String content) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            this.setTo(to, helper);
            this.setCc(cc, helper);
            helper.setSubject("[" + env + "]" + subject);
            helper.setText(content, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            logger.error("发送html邮件时发生异常！", e);
        }
    }

    //邮箱格式转换
    private void setCc(String cc, SimpleMailMessage message) {
        if (StringUtils.isNotBlank(cc)) {
            if (cc.contains(",")) {
                message.setCc(cc.split(","));
            } else {
                message.setCc(cc);
            }
        }
    }

    private void setCc(String cc, MimeMessageHelper helper) throws MessagingException {
        if (StringUtils.isNotBlank(cc)) {
            if (cc.contains(",")) {
                helper.setCc(cc.split(","));
            } else {
                helper.setCc(cc);
            }
        }
    }

    private void setTo(String to, SimpleMailMessage message) {
        if (StringUtils.isNotBlank(to)) {
            if (to.contains(",")) {
                message.setTo(to.split(","));
            } else {
                message.setTo(to);
            }
        }
    }

    private void setTo(String to, MimeMessageHelper helper) throws MessagingException {
        if (StringUtils.isNotBlank(to)) {
            if (to.contains(",")) {
                helper.setTo(to.split(","));
            } else {
                helper.setTo(to);
            }
        }
    }

    /**
     * 发送带附件的邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            this.setTo(to, helper);
            helper.setSubject("[" + env + "]" + subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            logger.error("发送带附件的邮件时发生异常！", e);
        }
    }

    /**
     * 发送嵌入静态资源（一般是图片）的邮件
     *
     * @param to
     * @param subject
     * @param content 邮件内容，需要包括一个静态资源的id，比如：<img src=\"cid:rscId01\" >
     * @param rscPath 静态资源路径和文件名
     * @param rscId   静态资源id
     */
    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            this.setTo(to, helper);
            helper.setSubject("[" + env + "]" + subject);
            helper.setText(content, true);

            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            logger.error("发送嵌入静态资源的邮件时发生异常！", e);
        }
    }
}
