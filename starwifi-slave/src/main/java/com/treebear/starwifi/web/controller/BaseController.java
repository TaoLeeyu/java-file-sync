/**
 */
package com.treebear.starwifi.web.controller;

import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author think4ever
 * @version $Id: BaseController.java, v 0.1 2016-1-27 下午2:43:49 taoran Exp $
 */
public abstract class BaseController {

    /**
     * 后台分页默认页大小：20
     */
    protected static final int DEFAULT_BACK_PAGE_SIZE = 20;

    /**
     * 前台分页默认页大小：10
     */
    protected static int DEFAULT_PAGE_SIZE = 10;

    /**
     * 返回错误信息key
     */
    protected static final String ERROR_MESSAGE_KEY = "errorMessage";

    /**
     * 返回错误码key
     */
    protected static final String ERROR_CODE_KEY = "errorCode";

    /**
     * 返回内容key
     */
    protected static final String INFO_KEY = "info";

    /**
     * 返回分页当前页的key.
     */
    protected static final String PAGE_NUM_KEY = "pageNum";

    /**
     * 返回内容key
     */
    protected static final String PAGINATOR_KEY = "paginator";

    private static final Log logger = LogFactory.getLog(BaseController.class);

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;
    protected void setDefaultPageSize(int size) {
        DEFAULT_PAGE_SIZE = size;
    }

    /**
     * 获取INT参数
     *
     * @param param
     * @return
     */
    protected int getIntParam(String param) {
        try {
            return Integer.parseInt(param);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取String参数
     *
     * @param name
     * @return
     */
    protected String getStr(String name) {
        String param = request.getParameter(name);
        if (StringUtils.isBlank(param)) {
            return null;
        } else
            return param.trim();
    }

    /**
     * forward方法
     *
     * @param url
     * @return
     */
    protected String forward(String url) {
        return url;
    }
    /**
     * forward方法
     *
     * @param url
     * @return
     */
    protected String forwardWithParam(String url) {
        return "forward:"+url;
    }
    /**
     * Redirect方法
     *
     * @param url
     * @return
     */
    protected String redirect(String url) {

        return "redirect:" + url;
    }

    /**
     * 设置信息到Session中
     *
     * @param sessionName
     * @param object
     */
    protected void setSession(String sessionName, Object object) {
        HttpSession session = request.getSession();
        if (null == object) {
            return;
        }
        session.setAttribute(sessionName, object);
    }

    /**
     * 获取session中的信息
     *
     * @param sessionName
     */
    protected Object getSession(String sessionName) {
        HttpSession session = request.getSession();
        return session.getAttribute(sessionName);
    }

    /**
     * 获取session对象
     */
    protected HttpSession getSession() {
        HttpSession session = request.getSession();
        return session;
    }

    /**
     * 设置参数到spring MODEL中
     *
     * @param model
     * @param name
     * @param value
     */
    protected void set(Model model, String name, Object value) {
        model.addAttribute(name, value);
    }

    protected void set(HttpServletRequest request, String name, Object value) {
        request.setAttribute(name, value);
    }

    /**
     * 从request对象中获取参数
     *
     * @param name
     * @return
     */
    protected Object get(String name) {
        return StringUtils.isBlank(name) ? null : request.getParameter(name);
    }

    protected Integer getCurrentPageNum() {
        int pageNum = 1;
        try {
            pageNum = Integer.valueOf(request.getParameter(PAGE_NUM_KEY));

        } catch (Exception e) {
            // TODO: handle exception
        }
        return pageNum;
    }

    protected void setPage(boolean need) {
        if (need) {
            PageHelper.startPage(getCurrentPageNum(), DEFAULT_PAGE_SIZE);
        }

    }

    protected Integer getInt(String name) {
        try {
            return Integer.valueOf(request.getParameter(name));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取request里边所有的参数。
     * 在需要中间跳转页面时，直接通过此方法将参数传递。
     * eg:
     * forward("/admin/member/list" + getUrlParams())
     *
     * @return
     */
    protected String getUrlParams() {
        Map<String, String> map = getUrlParamsMap();
        StringBuffer urlParameters = new StringBuffer();
        urlParameters.append("?");
        for (Entry<String, String> entry : map.entrySet()) {
            urlParameters.append("&");
            urlParameters.append(entry.getKey());
        }
        return urlParameters.toString().replace("?&", "?");
    }

    private Map<String, String> getUrlParamsMap() {
        Map<String, String> tempMap = new HashMap<String, String>();
        Map<String, String[]> map = request.getParameterMap();
        for (Entry<String, String[]> entry : map.entrySet()) {
            // success url参数不需要.
            if ("url".equals(entry.getKey())) {
                continue;
            }
            //多次参数提交，去除相同参数 map key="id=xxx"
            for (String value : entry.getValue()) {
                String s = null;
                try {
                    s = entry.getKey() + "=" + URLEncoder.encode(value, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                ;
                tempMap.put(s, s);
            }
        }
        return tempMap;
    }

    /**
     * 获取validationEngine的结果List
     *
     * @param fieldId
     * @param isPass
     * @return
     */
    protected List getValidationResult(String fieldId, boolean isPass) {
        List validate = new ArrayList();
        validate.add(fieldId);
        validate.add(isPass);
        return validate;
    }

    public String alertMsg(String msg) {
        return "alert('" + msg + "')";
    }

    /**
     * 获取请求的IP地址。
     * 首先查看有没有代理，代理用真是的IP
     * 没有就用remoteAddr
     *
     * @return
     */
    protected String getRequestIp() {
        String ip = request.getHeader("x-forwarded-for");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            ip = ip.split(",")[0];
        } else {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public void setTokeyError(String url) {
        this.getSession().setAttribute("gotoURL", url);
    }



    public String doPost(String url) {
        try {
            URI uri = new URI(url);
            SimpleClientHttpRequestFactory schr = new SimpleClientHttpRequestFactory();
            ClientHttpRequest chr = schr.createRequest(uri, HttpMethod.POST);
            ClientHttpResponse res = chr.execute();
            InputStream is = res.getBody(); //获得返回数据,注意这里是个流
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String str = "";
            StringBuffer sb = new StringBuffer();
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public String doGet(String url) {
        try {
            URI uri = new URI(url);
            SimpleClientHttpRequestFactory schr = new SimpleClientHttpRequestFactory();
            ClientHttpRequest chr = schr.createRequest(uri, HttpMethod.GET);
            ClientHttpResponse res = chr.execute();
            InputStream is = res.getBody(); //获得返回数据,注意这里是个流
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String str = "";
            StringBuffer sb = new StringBuffer();
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
