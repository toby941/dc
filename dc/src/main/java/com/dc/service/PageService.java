package com.dc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dc.model.Course;
import com.dc.model.CourseTab;

@Service
public class PageService {

    @Autowired
    private RxResponseResolve resolve;

    public List<Course> getAllCourse() throws IOException {
        List<CourseTab> courseTabs = resolve.resolveGetMenuList();
        List<Course> courses = new ArrayList<Course>();
        for (CourseTab tab : courseTabs) {
            List<Course> subCourses = tab.getCourses();
            for (Course subCourse : subCourses) {
                subCourse.setCourseTypeStr(tab.getName());
                courses.add(subCourse);
            }
        }
        return courses;
    }
}
