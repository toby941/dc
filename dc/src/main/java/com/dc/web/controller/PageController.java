package com.dc.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dc.web.form.PageForm;

@Controller
public class PageController extends AbstractController {

    @RequestMapping(value = "/logon", method = RequestMethod.GET)
    public ModelAndView logon(HttpServletRequest request, HttpServletResponse response, PageForm form)
            throws IOException {
        Boolean isLogon = (Boolean) request.getSession().getAttribute("flag");
        if (isLogon != null && isLogon) {
            return redirect("/list");
        }
        else {
            return new ModelAndView("page/logon", DEFAULT_COMMAND, form);
        }
    }

    @RequestMapping(value = "/logon", method = RequestMethod.POST)
    public ModelAndView doLogon(HttpServletRequest request, HttpServletResponse response, PageForm form)
            throws IOException {
        String userName = form.getUserName();
        String password = form.getPassword();
        if ("admin".equals(userName) && "2012".equals(password)) {
            request.getSession().setAttribute("flag", new Boolean(true));
            return redirect("/user/index");
        }
        else {
            return new ModelAndView("page/logon", DEFAULT_COMMAND, form);
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView doLogout(HttpServletRequest request, HttpServletResponse response, PageForm form)
            throws IOException {
        request.getSession().removeAttribute("flag");
        return new ModelAndView("page/logon", DEFAULT_COMMAND, form);
    }

    @RequestMapping(value = "/user/index", method = RequestMethod.GET)
    public ModelAndView userIndex(HttpServletRequest request, HttpServletResponse response, PageForm form)
            throws IOException {
        return new ModelAndView("user/index", DEFAULT_COMMAND, form);
    }

    @RequestMapping(value = "/leftPage", method = RequestMethod.GET)
    public ModelAndView leftPage(HttpServletRequest request, HttpServletResponse response, PageForm form)
            throws IOException {
        return new ModelAndView("user/menus", DEFAULT_COMMAND, form);
    }

}
