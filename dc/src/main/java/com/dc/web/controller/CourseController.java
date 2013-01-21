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

import com.dc.constants.Constants;
import com.dc.model.Course;
import com.dc.model.CoursePackage;
import com.dc.service.PageService;
import com.dc.web.form.PageForm;

@Controller
public class CourseController extends AbstractController {

    @Autowired
    private PageService pageService;

    @RequestMapping(value = "/packagelist")
    public ModelAndView packagelist(HttpServletRequest request, HttpServletResponse response, PageForm form)
            throws IOException {

        List<CoursePackage> packages = pageService.getAllCoursePackage();
        form.setPackages(packages);
        return new ModelAndView("page/package_list", DEFAULT_COMMAND, form);
    }

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
        form.setDesc(pageService.getDesc(courseNo, Constants.page_type_course));
        return new ModelAndView("page/detail", DEFAULT_COMMAND, form);
    }

    @RequestMapping(value = "/packageview/{id}", method = RequestMethod.GET)
    public ModelAndView packageView(@PathVariable String id, HttpServletRequest request, HttpServletResponse response,
            PageForm form) throws IOException {
        CoursePackage c = pageService.getPackage(id);
        form.setCoursePackage(c);
        form.setDesc(pageService.getDesc(id, Constants.page_type_package));
        return new ModelAndView("page/package_detail", DEFAULT_COMMAND, form);
    }

    @RequestMapping(value = "/packageedit/{id}", method = RequestMethod.POST)
    public ModelAndView packageEdit(@PathVariable String id, HttpServletRequest request, HttpServletResponse response,
            PageForm form) throws IOException {
        String desc = form.getDesc();
        if (StringUtils.isNotEmpty(desc)) {
            pageService.saveDesc(id, desc, Constants.page_type_package);
        }
        MultipartFile file = form.getPhoto1();
        if (file != null && file.getBytes() != null && file.getBytes().length > 0) {
            pageService.savePhoto(file, id, "1", Constants.page_type_package);
        }
        else {
            String tmpid = form.getTmpid();
            if (StringUtils.isNotBlank(tmpid)) {
                pageService.savePhotoByTmpPhoto(tmpid, id, "1", Constants.page_type_package);
            }
        }

        return redirect("/packagelist");
    }

    @RequestMapping(value = "/edit/{courseNo}", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable String courseNo, HttpServletRequest request, HttpServletResponse response,
            PageForm form) throws IOException {
        String desc = form.getDesc();
        if (StringUtils.isNotEmpty(desc)) {
            pageService.saveDesc(courseNo, desc, Constants.page_type_course);
        }
        MultipartFile file1 = form.getPhoto1();
        MultipartFile file2 = form.getPhoto2();
        MultipartFile file3 = form.getPhoto3();
        MultipartFile[] files = new MultipartFile[]{file1, file2, file3};
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (file != null) {
                pageService.savePhoto(file, courseNo, String.valueOf(i + 1), Constants.page_type_course);
            }
        }
        return redirect("/list");
    }
}
