package com.quasar.backend.common.config;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * https://shiro.apache.org/web.html
 * https://blog.csdn.net/u014042146/article/details/72834582
 */
public class AuthcFilter extends FormAuthenticationFilter {

    private static boolean isAjax(ServletRequest request) {
        String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
        return "XMLHttpRequest".equalsIgnoreCase(header);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        boolean allowed = super.isAccessAllowed(request, response, mappedValue);
//        log.info("allowed:" + allowed + " | " + ((HttpServletRequest) request).getRequestURI());
//        log.info("isAccessAllowed ContentType:" +((HttpServletRequest) request).getHeader("Content-Type"));

        return allowed;
//        String uri = ((HttpServletRequest) request).getRequestURI();
//        if(uri.endsWith(".html") || uri.endsWith(".js") || uri.endsWith(".css")){
//            log.info("html pass");
//            return true;
//        }
//
//        return allowed;
    }

    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
//        log.info("redirectToLogin");
//        log.info("uri:" + ((HttpServletRequest) request).getRequestURI());
//        log.info("isAjax:" + isAjax(request));
//        log.info("ContentType:" + ((HttpServletRequest) request).getHeader("Content-Type"));
        String contentType = ((HttpServletRequest) request).getHeader("Content-Type");
        if (isAjax(request) || "application/json".equals(contentType)) {
            WebUtils.toHttp(response).sendError(
                    401,
                    "not_logged_in");
        } else {
            super.redirectToLogin(request, response);
        }
    }
}
