package com.treebear.starwifi.common.result;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Web Ajax响应对象
 *
 * @author thinker Created by think on 2016/3/16.
 */
public class AjaxResponse<T> implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4116431166496255045L;

    /**
     * 是否成功
     */
    private boolean isSuccess = false;
    /**
     * 结果码
     */
    private String resultCode;
    /**
     * 结果信息
     */
    private String message;

    /**
     * 结果数据
     */
    private T resultData;

    public AjaxResponse(boolean isSuccess, String message) {
        this(isSuccess, "", message, null);
    }

    public AjaxResponse(boolean isSuccess, String message, T resultData) {
        this(isSuccess, "", message, resultData);
    }

    public AjaxResponse(boolean isSuccess, String resultCode, String message, T resultData) {
        this.isSuccess = isSuccess;
        this.resultCode = resultCode;
        this.message = message;
        this.resultData = resultData;
    }

    //////////////GETTER|SETTER/////////////////

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResultData() {
        return this.resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }

    /**
     * 获取对应JSON
     *
     * @return
     */
    public String toJsonString() {
        return new Gson().toJson(this);
    }
}
