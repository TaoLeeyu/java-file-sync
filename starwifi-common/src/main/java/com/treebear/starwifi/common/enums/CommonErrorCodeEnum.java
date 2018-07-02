package com.treebear.starwifi.common.enums;


import com.treebear.starwifi.common.ErrorCode;

/**
 * 通用异常枚举类.<br>
 * <p/>
 *
 * @author think Created by thinker on 2014/11/19.
 */
public enum CommonErrorCodeEnum implements ErrorCode {
    /**
     * 未知异常
     */
    UNKNOWN_EXCEPTION("unknown_exception", "未知异常"),

    /**
     * 必要参数为空
     */
    PARAM_NULL("PARAM_NULL", "必要参数为空"),

    /**
     * 非法参数
     */
    ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT", "参数非法"),

    /**
     * 编码异常
     */
    ENCODING_EXCEPTION("ENCODING_EXCEPTION", "字符编码异常"),

    /**
     * JSON转换异常
     */
    JSON_CONVERT_EXCEPTION("JSON_CONVERT_EXCEPTION", "JSON转换异常"),

    /**
     * 数据库操作异常
     */
    DB_OPERATE_ERROR("DB_OPERATE_ERROR", "数据库操作失败"),

    /**
     * 服务调用失败
     */
    SERVICE_CALL_ERROR("SERVICE_CALL_ERROR", "服务调用失败"),

    /**
     * 系统异常
     */
    SYSTEM_EXCEPTION("SYSTEM_EXCEPTION", "系统异常"),

    /**
     * 业务异常
     */
    BIZ_EXCEPTION("BIZ_EXCEPTION", "业务异常"),

    /**
     * 网络读写过程中IO异常
     */
    IO_EXCEPTION("IO_EXCEPTION", "IO异常"),

    /**
     * 数据不存在
     */
    NOT_EXIST("NOT_EXIST", "业务数据不存在"),

    /**
     * 乐观锁标识过期
     */
    LOCK_VERSION_ERROR("LOCK_VERSION_ERROR","乐观锁标识错误"),
    /**
     * 资金账户为空
     */
    ACCOUNT_IS_NULL("ACCOUNT_IS_NULL","资金账户为空"),
    /**
     * 增加财务记录错误
     */
    SAVE_ACCOUNT_BOOK_ERROR("SAVE_ACCOUNT_BOOK_ERROR","增加财务记录错误"),
    /**
     * 更新资金金额失败
     */
    UPDATE_ACCOUNT_INFO_ERROR("UPDATE_ACCOUNT_INFO_ERROR","更新资金金额失败"),
    /**
     * 账户余额不足
     */
    AMOUNT_IS_NOT_ENOUGH("AMOUNT_IS_NOT_ENOUGH","账户余额不足");



    /**
     * 代码
     */
    private String code = null;

    /**
     * 描述
     */
    private String description = null;

    /**
     * 双参构造函数
     *
     * @param code        代码
     * @param description 描述
     */
    private CommonErrorCodeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
