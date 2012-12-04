package com.dc.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dc.model.Course;
import com.dc.service.PageService;
import com.dc.web.form.PageForm;

@Controller
public class PageController extends AbstractController {
    public static final String DEFAULT_COMMAND = "command";
    @Autowired
    private PageService pageService;

    @RequestMapping(value = "/list")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response, PageForm form)
            throws IOException {
        List<Course> courses = pageService.getAllCourse();
        form.setCourses(courses);
        return new ModelAndView("page/list", DEFAULT_COMMAND, form);
    }
}
