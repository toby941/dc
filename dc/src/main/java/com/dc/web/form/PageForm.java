package com.dc.web.form;

import java.util.List;

import com.dc.model.Course;

public class PageForm extends AbstractForm {

    private List<Course> courses;

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public void form2domain() {
        // TODO Auto-generated method stub

    }

}
