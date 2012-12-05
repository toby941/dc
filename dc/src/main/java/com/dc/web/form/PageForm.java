package com.dc.web.form;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dc.model.Course;

public class PageForm extends AbstractForm {

    private List<Course> courses;

    private Course course;

    private String desc;
    private MultipartFile photo;

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

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
