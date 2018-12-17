package com.lesports.qmt.sbc.model;

import com.lesports.qmt.model.support.QmtModel;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * trunk.
 *
 * @author pangchuanxiao
 * @since 2016/10/18
 */
@Document(collection = "live_chats")
public class LiveChat extends QmtModel<Long> {
    private static final long serialVersionUID = -2000729691802075518L;

    @Field("live_chat_id")
    private Long liveChatId; // required
    @Field("chat_start_time")
    private String chatStartTime; // required
    @Field("chat_end_time")
    private String chatEndTime; // required
    @Field("allow_question")
    private Boolean allowQuestion; // required
    @Field("question_start_time")
    private String questionStartTime; // required
    @Field("question_end_time")
    private String questionEndTime; // required
    @Field("quick_reply")
    private String quickReply; // required
    @Field("allow_chat")
    private Boolean allowChat; // required
    @Field("is_danmaku")
    private Boolean isDanmaku; // required
    @Field("is_audit")
    private Boolean isAudit; // required
    @Field("chat_placard")
    private String chatPlacard; // required

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLiveChatId() {
        return liveChatId;
    }

    public void setLiveChatId(Long liveChatId) {
        this.liveChatId = liveChatId;
    }

    public String getChatStartTime() {
        return chatStartTime;
    }

    public void setChatStartTime(String chatStartTime) {
        this.chatStartTime = chatStartTime;
    }

    public String getChatEndTime() {
        return chatEndTime;
    }

    public void setChatEndTime(String chatEndTime) {
        this.chatEndTime = chatEndTime;
    }

    public Boolean getAllowQuestion() {
        return allowQuestion;
    }

    public void setAllowQuestion(Boolean allowQuestion) {
        this.allowQuestion = allowQuestion;
    }

    public String getQuestionStartTime() {
        return questionStartTime;
    }

    public void setQuestionStartTime(String questionStartTime) {
        this.questionStartTime = questionStartTime;
    }

    public String getQuestionEndTime() {
        return questionEndTime;
    }

    public void setQuestionEndTime(String questionEndTime) {
        this.questionEndTime = questionEndTime;
    }

    public String getQuickReply() {
        return quickReply;
    }

    public void setQuickReply(String quickReply) {
        this.quickReply = quickReply;
    }

    public Boolean getAllowChat() {
        return allowChat;
    }

    public void setAllowChat(Boolean allowChat) {
        this.allowChat = allowChat;
    }

    public Boolean getDanmaku() {
        return isDanmaku;
    }

    public void setDanmaku(Boolean danmaku) {
        isDanmaku = danmaku;
    }

    public Boolean getAudit() {
        return isAudit;
    }

    public void setAudit(Boolean audit) {
        isAudit = audit;
    }

    public String getChatPlacard() {
        return chatPlacard;
    }

    public void setChatPlacard(String chatPlacard) {
        this.chatPlacard = chatPlacard;
    }
}
