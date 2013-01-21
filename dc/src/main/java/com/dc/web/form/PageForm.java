package com.dc.web.form;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dc.model.Course;
import com.dc.model.CoursePackage;

public class PageForm extends AbstractForm {

    private Course course;

    private CoursePackage coursePackage;

    public CoursePackage getCoursePackage() {
        return coursePackage;
    }

    public void setCoursePackage(CoursePackage coursePackage) {
        this.coursePackage = coursePackage;
    }

    private List<Course> courses;

    private List<CoursePackage> packages;

    public List<CoursePackage> getPackages() {
        return packages;
    }

    public void setPackages(List<CoursePackage> packages) {
        this.packages = packages;
    }

    public String getTmpid() {
        return tmpid;
    }

    public void setTmpid(String tmpid) {
        this.tmpid = tmpid;
    }

    private String desc;

    private String password;

    private MultipartFile photo1;

    private MultipartFile photo2;

    private MultipartFile photo3;

    /**
     * flash控件上传图片后返回临时图片id
     */
    private String tmpid;

    private String userName;

    @Override
    public void form2domain() {
        // TODO Auto-generated method stub

    }

    public Course getCourse() {
        return course;
    }

    public List<Course> getCourses() {
        return courses;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public String getPassword() {
        return password;
    }

    public MultipartFile getPhoto1() {
        return photo1;
    }

    public MultipartFile getPhoto2() {
        return photo2;
    }

    public MultipartFile getPhoto3() {
        return photo3;
    }

    public String getUserName() {
        return userName;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoto1(MultipartFile photo1) {
        this.photo1 = photo1;
    }

    public void setPhoto2(MultipartFile photo2) {
        this.photo2 = photo2;
    }

    public void setPhoto3(MultipartFile photo3) {
        this.photo3 = photo3;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
