package com.treebear.starwifi.common.result;

import java.io.Serializable;

/**
 * 服务接口返回结果基类
 */
public abstract class BaseResult implements Serializable {
    /**
     * 是否成功
     */
    private boolean isSuccess = false;
    /**
     * 错误码,若执行成功errorCode为空
     */
    private String errorCode;
    /**
     * 错误信息,若执行成功errorMessage为空
     */
    private String errorMessage;

    public BaseResult() {
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}