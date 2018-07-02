package com.treebear.starwifi.common.result;


/**
 * 服务接口结果类
 *
 * @author think Created by think on 2016/1/22.
 */
public class SingleResult<T> extends BaseResult {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6586842920733624537L;
    /**
     * 结果数据
     */
    private T resultData;

    public SingleResult() {
    }

    public T getResultData() {
        return this.resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }

    /**
     * 组装验证结果(add by taoran 2016-03-05).
     * @param state     验证状态 true-通过 false-不通过.
     * @param errorCode 系统错误码@see com.longyan.market.common.enums.CommonErrorCodeEnum.
     * @param errorMsg  错误消息.
     */
    public void pick(final boolean state, final String errorCode, final String errorMsg) {
        this.setSuccess(state);
        this.setErrorCode(errorCode);
        this.setErrorMessage(errorMsg);
    }

}
