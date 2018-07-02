package com.treebear.starwifi.common.exception;


import com.treebear.starwifi.common.ErrorCode;
import com.treebear.starwifi.common.enums.CommonErrorCodeEnum;

/**
        * UNC业务异常类
        * <p>因业务数据|业务原因导致异常</p>
        *
        * @author thinker
        * @version BizException.java, v 0.1 2015/05/28 10:53 Exp $$
        */
public class BizException extends RuntimeException {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -293165993387413773L;

    /**
     * 错误码
     */
    protected ErrorCode errorCode;

    /**
     * 默认构造方法
     */
    public BizException() {
    }

    /**
     * 构造方法(errorCode=BIZ_EXCEPTION)
     *
     * @param message 异常信息
     */
    public BizException(String message) {
        super(message);
        this.errorCode = CommonErrorCodeEnum.BIZ_EXCEPTION;
    }
    /**
     * 构造方法(errorCode=CommonErrorCodeEnum)
     *
     * @param commonErrorCodeEnum 异常信息
     */
    public BizException(CommonErrorCodeEnum commonErrorCodeEnum) {
        super(commonErrorCodeEnum.getDescription());
        this.errorCode = commonErrorCodeEnum;
    }

    /**
     * @param errorCode
     * @param message
     */
    public BizException(ErrorCode errorCode, String message) {
        super(errorCode.getDescription() + ":" + message);
        this.errorCode = errorCode;
    }

    ///////GETTER/SETTER///////
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
