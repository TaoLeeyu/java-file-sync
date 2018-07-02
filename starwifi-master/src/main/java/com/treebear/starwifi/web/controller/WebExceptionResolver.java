package com.treebear.starwifi.web.controller;

import com.treebear.starwifi.common.result.AjaxResponse;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * WEB异常统一处理类
 *
 * @author think Created by think on 2016/2/25.
 */
public class WebExceptionResolver implements HandlerExceptionResolver {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(WebExceptionResolver.class);

    /**
     * 异常页面
     */
    private static final String EXCEPTION_PAGE = "/error/exception";
    private static final String ADMIN_EXCEPTION_PAGE = "/admin/error/500";

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelMap modelMap = new ModelMap();
        String httpCode = request.getParameter("httpCode");
        if (StringUtils.equals(httpCode, "403") || StringUtils.equals(httpCode, "404")) {
            modelMap.addAttribute("exceptionMessage", httpCode);
        }
        // 如果不是异步请求
        if (!(request.getHeader("accept").indexOf("application/json") > -1 ||
                (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
            String errorCode = "WEB_EXCEPTION_" + RandomStringUtils.randomNumeric(9);
            String url = request.getRequestURL().toString();
            logger.error("系统未捕获异常, ERROR_CODE:" + errorCode + ", RequestURL:" + url, ex);
            modelMap.addAttribute("exceptionMessage", ex.getMessage() + ", ERROR_CODE:" + errorCode);
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw, true));
            modelMap.addAttribute("exception", sw);
            if (url.contains("admin")) {
                return new ModelAndView(ADMIN_EXCEPTION_PAGE, modelMap);
            } else {
                return new ModelAndView(EXCEPTION_PAGE, modelMap);
            }
        } else {
            // JSON格式返回
            try {
                String errorCode = "AJAX_EXCEPTION_" + RandomStringUtils.randomNumeric(9);
                logger.error("系统未捕获异常, ERROR_CODE:" + errorCode + ", AJAX RequestURL:" + request.getRequestURL(), ex);
                PrintWriter writer = response.getWriter();
                writer.write(new AjaxResponse(false, ex.toString() + ", ERROR_CODE:" + errorCode).toJsonString());
                writer.flush();
            } catch (IOException e) {
                logger.error("返回Exception信息JSON信息异常", e);
            }
            return null;
        }
    }
}
