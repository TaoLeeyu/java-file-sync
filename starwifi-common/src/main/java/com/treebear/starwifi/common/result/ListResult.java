package com.treebear.starwifi.common.result;

import java.util.List;

/**
 * 服务接口结果列表类
 *
 * @author think  Created by think on 2016/1/22.
 */
public class ListResult<T> extends BaseResult {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2011238699099137431L;

    /**
     * 结果数据列表
     */
    private List<T> resultDatas;

    public ListResult() {
    }

    public List<T> getResultDatas() {
        return this.resultDatas;
    }

    public void setResultDatas(List<T> resultDatas) {
        this.resultDatas = resultDatas;
    }
}
