package com.dc.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
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

    @RequestMapping(value = "/view/{courseNo}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable String courseNo, HttpServletRequest request, HttpServletResponse response,
            PageForm form) throws IOException {
        Course course = pageService.getCourse(courseNo);
        form.setCourse(course);
        form.setDesc(pageService.getDesc(courseNo));
        return new ModelAndView("page/detail", DEFAULT_COMMAND, form);
    }

    @RequestMapping(value = "/edit/{courseNo}", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable String courseNo, HttpServletRequest request, HttpServletResponse response,
            PageForm form) throws IOException {
        String desc = form.getDesc();
        if (StringUtils.isNotEmpty(desc)) {
            pageService.saveDesc(courseNo, desc);
        }
        MultipartFile file1 = form.getPhoto1();
        MultipartFile file2 = form.getPhoto2();
        MultipartFile file3 = form.getPhoto3();
        MultipartFile[] files = new MultipartFile[]{file1, file2, file3};
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (file != null) {
                pageService.savePhoto(file, courseNo, String.valueOf((i + 1)));
            }
        }

        return redirect("/list");
    }
}
