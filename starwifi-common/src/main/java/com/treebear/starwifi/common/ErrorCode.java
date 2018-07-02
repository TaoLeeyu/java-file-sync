package com.treebear.starwifi.common;

/**
 * 错误码接口.<br>
 *
 *
 */
public interface ErrorCode {
    /**
     * 获取错误码
     *
     * @return
     */
    public String getCode();

    /**
     * 获取错误描述
     *
     * @return
     */
    public String getDescription();
}