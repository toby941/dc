/*
 * Copyright 2012 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.dc.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dc.web.form.BookingForm;

/**
 * 预约功能
 */
@Controller
@RequestMapping("/api")
public class ApiController extends AbstractController {

    @RequestMapping(value = "/test")
    public ModelAndView test(BookingForm form, HttpServletRequest request) throws Exception {

        ModelAndView mv = new ModelAndView("api/list", "COMMAND", form);
        return mv;
    }
}// end class
