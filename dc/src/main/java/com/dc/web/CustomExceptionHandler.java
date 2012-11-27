/*
 * Copyright 2011 Mitian Technology, Co., Ltd. All rights reserved.
 */
package com.dc.web;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * CustomExceptionHandler.java<br/>
 * 自定义spring MVC异常处理类，记录异常日志
 */
public class CustomExceptionHandler extends SimpleMappingExceptionResolver {
    private static final Logger LOG = Logger.getLogger(CustomExceptionHandler.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        LOG.error("handle error" + getRequestInfo(request, handler), ex);
        ex.printStackTrace();
        request.setAttribute("e", ex);
        ModelAndView modelAndView = null;
        modelAndView = new ModelAndView("forward:/errorDocs/404.jsp");
        return modelAndView;
    }
    private static final String LOG_TEMPLETE = " request info ip:{0}, url:{1}";

    /**
     * 记录请求信息
     * 
     * @param request
     * @param handler
     * @return
     */
    private String getRequestInfo(HttpServletRequest request, Object handler) {
        return MessageFormat.format(LOG_TEMPLETE, request.getRemoteAddr(),
                request.getRequestURI() + request.getQueryString());
    }
}
