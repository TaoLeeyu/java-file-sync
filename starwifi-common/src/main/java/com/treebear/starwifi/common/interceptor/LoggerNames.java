
package com.treebear.starwifi.common.interceptor;

/**
 * @author think4ever
 * @version v 0.1 2015/1/23 6:48 Exp $$
 */
public interface LoggerNames {

    /**
     * 系统DAO层操作日志
     * <p/>
     * Level : INFO
     * <p/>
     * 输出 : dal-digest.log
     */
    String TREEBEAR_DAL_DIGEST = "TREEBEAR_DAL_DIGEST";

    /**
     * 系统SERVICE层操作日志
     * <p/>
     * Level : INFO
     * <p/>
     * 输出 : biz-digest.log
     */
    String TREEBEAR_BIZ_DIGEST = "TREEBEAR_BIZ_DIGEST";

    /**
     * 系统CONTROLLER层操作日志
     * <p/>
     * Level : INFO
     * <p/>
     * 输出 : controller-digest.log
     */
    String TREEBEAR_CONTROLLER_DIGEST = "TREEBEAR_CONTROLLER_DIGEST";

}
