package com.dc.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * AbstractController.java 所有Controller的抽象类，新建Controller时必须集成该类,提供了一些基本方法
 */
public abstract class AbstractController extends MultiActionController {

    public static final String DEFAULT_COMMAND = "command";
    private final Logger log = Logger.getLogger(getClass());

    public String getUserAgent(HttpServletRequest request) {
        return request.getHeader("user-agent");
    }

    /**
     * @return the log
     */
    public Logger getLog() {
        return log;
    }

    public Logger getLogger() {
        return log;
    }

    public ModelAndView redirect(String url) {
        return new ModelAndView("redirect:" + url);
    }

    public ModelAndView forward(String url) {
        return new ModelAndView("forward:" + url);
    }

    public String getHostUrl(HttpServletRequest req) {
        String uri = req.getServerName();
        int port = req.getServerPort();
        return "http://" + uri + (port == 80 ? "" : ":" + port);
    }
}
