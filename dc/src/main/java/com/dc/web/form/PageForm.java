package com.dc.web.form;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dc.model.Course;

public class PageForm extends AbstractForm {

    private List<Course> courses;

    private Course course;

    private String desc;
    private MultipartFile photo1;

    public MultipartFile getPhoto1() {
        return photo1;
    }

    public void setPhoto1(MultipartFile photo1) {
        this.photo1 = photo1;
    }

    public MultipartFile getPhoto2() {
        return photo2;
    }

    public void setPhoto2(MultipartFile photo2) {
        this.photo2 = photo2;
    }

    private MultipartFile photo2;
    private MultipartFile photo3;

    public MultipartFile getPhoto3() {
        return photo3;
    }

    public void setPhoto3(MultipartFile photo3) {
        this.photo3 = photo3;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public void setDesc(String desc) {
        this.desc = desc;
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
