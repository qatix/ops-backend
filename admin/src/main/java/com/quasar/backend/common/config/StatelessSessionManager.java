package com.quasar.backend.common.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @Author: Logan
 * @Date: 2018/8/16 2:36 PM
 */
@Slf4j
public class StatelessSessionManager extends DefaultWebSessionManager {
    /**
     * 这个是客户端请求给服务端带的header
     */
    private final static String HEADER_TOKEN_NAME = "X-token";
    private final static String GET_TOKEN_NAME = "token";
    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    /**
     * 重写getSessionId,分析请求头中的指定参数，做用户凭证sessionId
     */
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        //先从header里面去
        String id = WebUtils.toHttp(request).getHeader(HEADER_TOKEN_NAME);
        if (StringUtils.isEmpty(id)) {
            //如果header结果为空，再从Parameter里面获取，包含GET或者POST
            id = WebUtils.toHttp(request).getParameter(GET_TOKEN_NAME);
        }

        log.info("session-id:" + id);

        if (StringUtils.isEmpty(id)) {
//            throw new RRException("未登录，请先登录");
            //如果没有携带id参数则按照父类的方式在cookie进行获取
//              System.out.println("super："+super.getSessionId(request, response));
            return super.getSessionId(request, response);
        } else {
            //如果请求头中有 authToken 则其值为sessionId
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        }
    }

}

