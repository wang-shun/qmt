package com.lesports.qmt.base.web.entry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangdeqiang on 2017/2/16.
 */
public class ResponseJson<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean state = true;
    private Integer status = 200;
    private List<T> data;
    private T object;
    private String message;
    private String errorCode; // 错误码

    public ResponseJson() {
    }

    public ResponseJson(boolean state, Integer status, List<T> data, String message) {
        super();
        this.state = state;
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public ResponseJson(boolean state, List<T> data, String message) {
        super();
        this.state = state;
        this.data = data;
        this.message = message;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<T> getData() {
        if (data == null) {
            data = new ArrayList<T>();
        }
        return data;
    }

    public void setData(List<T> data) {

        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public static ResponseJson<?> fail(String message) {
        ResponseJson<?> json = new ResponseJson();
        json.setState(false);
        json.setMessage(message);

        return json;
    }

    public static <T> ResponseJson<T> fail(String errorCode, String message) {
        ResponseJson<T> json = new ResponseJson<>();
        json.setState(false);
        json.setErrorCode(errorCode);
        json.setMessage(message);

        return json;
    }

}