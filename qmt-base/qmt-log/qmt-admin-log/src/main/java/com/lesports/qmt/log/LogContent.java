package com.lesports.qmt.log;

import com.lesports.id.api.IdType;

import java.io.Serializable;

/**
 * Created by zhangdeqiang on 2016/12/9.
 */
public class LogContent<T> implements Serializable {
    private static final long serialVersionUID = 6815243291961873935L;

    //日志类型 0:业务日志；1：报警邮件
    private int logType;
    //操作人
    private String operator;
    //操作人ID
    private String operatorId;
    //当前方法路径
    private String methodPath;
    //实体类型
    private IdType idType;
    //实体ID
    private String entryId;
    //内容（参数）
    private T content;
    // 备注 (邮件内容)
    private String memo;
    // 操作时间
    private String createAt;
    //业务类型
    //private FeType feType;
    //邮件主题
    private String subject;
    //发送人
    private String sendTo;
    //索引类型
    private String type;

    public LogContent() {
    }

    public LogContent(String type, String operator, String methodPath, IdType idType, T content, String memo) {
        this.type = type;
        this.operator = operator;
        this.methodPath = methodPath;
        this.idType = idType;
        this.content = content;
        this.memo = memo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLogType() {
        return logType;
    }

    public void setLogType(int logType) {
        this.logType = logType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getMethodPath() {
        return methodPath;
    }

    public void setMethodPath(String methodPath) {
        this.methodPath = methodPath;
    }

    public IdType getIdType() {
        return idType;
    }

    public void setIdType(IdType idType) {
        this.idType = idType;
    }

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
