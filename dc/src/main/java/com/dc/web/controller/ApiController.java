/*
 * Copyright 2012 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.dc.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dc.service.ApiService;
import com.dc.web.form.PageForm;

/**
 * 预约功能
 */
@Controller
@RequestMapping("/api")
public class ApiController extends AbstractController {
    private final Logger log = Logger.getLogger(ApiController.class);

    @Autowired
    private ApiService apiService;

    @RequestMapping(value = "/test")
    public ModelAndView test(PageForm form, HttpServletRequest request) throws Exception {

        ModelAndView mv = new ModelAndView("api/list", "COMMAND", form);
        return mv;
    }

    @RequestMapping(value = "/ipad")
    public void ipadRequest(HttpServletRequest request, HttpServletResponse response) throws JDOMException, IOException {
        String command = request.getParameter("psentity");
        log.error("psentity:" + command);
        String str = apiService.handleRequest(command);
        response.setContentType("text/html; charset=UTF-8");
        javax.servlet.ServletOutputStream sout = response.getOutputStream();
        sout.write(str.getBytes("UTF-8"));
        sout.flush();
        sout.close();
    }

    @RequestMapping(value = "/txrx")
    public void txrxRequest(HttpServletRequest request, HttpServletResponse response) throws JDOMException, IOException {
        String str = apiService.getCurrentTxAndRx();
        response.setContentType("text/html; charset=UTF-8");
        javax.servlet.ServletOutputStream sout = response.getOutputStream();
        sout.write(str.getBytes("UTF-8"));
        sout.flush();
        sout.close();
    }

}
